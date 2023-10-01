package com.example.kwms.location.feature.command;

import com.example.kwms.location.domain.Inventory;
import com.example.kwms.location.domain.InventoryRepository;
import com.example.kwms.location.domain.WarehouseTransfer;
import com.example.kwms.location.domain.WarehouseTransferProduct;
import com.example.kwms.location.domain.WarehouseTransferRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CreateWarehouseTransfer {
    private final InventoryRepository inventoryRepository;
    private final WarehouseTransferRepository warehouseTransferRepository;

    //TODO 나중에 락 추가하기
    @PostMapping("/warehouse-transfers")
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public void request(@RequestBody @Valid final Request request) {
        validate(request.fromWarehouseNo, request.products);

        final WarehouseTransfer warehouseTransfer = request.toDomain();

        warehouseTransferRepository.save(warehouseTransfer);
    }

    private void validate(
            final Long fromWarehouseNo,
            final List<Request.Product> products) {
        for (final Request.Product product : products) {
            final List<Inventory> inventories = inventoryRepository.listBy(fromWarehouseNo, product.productNo);
            final long totalQuantity = inventories.stream()
                    .mapToLong(Inventory::getQuantity)
                    .sum();
            if (totalQuantity < product.quantity) {
                throw new IllegalArgumentException(
                        "재고가 부족합니다. 재고 수량: %d, 요청 수량: %d"
                                .formatted(totalQuantity, product.quantity));
            }
        }
    }

    public record Request(
            @NotNull(message = "출발 창고 번호는 필수입니다.")
            Long fromWarehouseNo,
            @NotNull(message = "도착 창고 번호는 필수입니다.")
            Long toWarehouseNo,
            @NotBlank(message = "바코드는 필수입니다.")
            String barcode,
            @NotEmpty(message = "상품은 필수입니다.")
            List<Request.Product> products) {
        public WarehouseTransfer toDomain() {
            return new WarehouseTransfer(
                    fromWarehouseNo,
                    toWarehouseNo,
                    barcode,
                    products.stream()
                            .map(product -> new WarehouseTransferProduct(
                                    product.productNo,
                                    product.quantity
                            ))
                            .toList());
        }

        public record Product(
                @NotNull(message = "상품 번호는 필수입니다.")
                Long productNo,
                @NotNull(message = "수량은 필수입니다.")
                @Min(value = 1, message = "수량은 1보다 커야 합니다.")
                Long quantity) {
        }
    }
}
