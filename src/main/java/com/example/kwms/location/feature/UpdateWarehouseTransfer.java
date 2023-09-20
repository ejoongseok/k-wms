package com.example.kwms.location.feature;

import com.example.kwms.location.domain.WarehouseTransfer;
import com.example.kwms.location.domain.WarehouseTransferProduct;
import com.example.kwms.location.domain.WarehouseTransferRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UpdateWarehouseTransfer {
    private final WarehouseTransferRepository warehouseTransferRepository;

    @Transactional
    @PutMapping("/warehouse-transfers/{warehouseTransferNo}")
    public void request(
            @PathVariable final Long warehouseTransferNo,
            @RequestBody @Valid final Request request) {
        final WarehouseTransfer warehouseTransfer = warehouseTransferRepository.getBy(warehouseTransferNo);
        if (!warehouseTransfer.getBarcode().equals(request.barcode)) {
            validate(request.barcode);
        }

        warehouseTransfer.update(
                request.fromWarehouseNo,
                request.toWarehouseNo,
                request.barcode,
                request.toProducts());
    }

    private void validate(final String barcode) {
        warehouseTransferRepository.findBy(barcode)
                .ifPresent(w -> {
                    throw new IllegalArgumentException("이미 존재하는 바코드입니다. 바코드: %s".formatted(barcode));
                });
    }

    public record Request(
            @NotNull(message = "출발 창고 번호는 필수입니다.")
            Long fromWarehouseNo,
            @NotNull(message = "도착 창고 번호는 필수입니다.")
            Long toWarehouseNo,
            @NotBlank(message = "바코드는 필수입니다.")
            String barcode,
            @NotEmpty(message = "상품은 필수입니다.")
            List<Product> products
    ) {
        public List<WarehouseTransferProduct> toProducts() {
            return products.stream()
                    .map(product -> new WarehouseTransferProduct(
                            product.productNo,
                            product.quantity
                    ))
                    .toList();
        }

        public record Product(
                @NotNull(message = "상품 번호는 필수입니다.")
                Long productNo,
                @NotNull(message = "수량은 필수입니다.")
                @Min(value = 1, message = "수량은 1개 이상이어야 합니다.")
                Long quantity
        ) {
        }
    }
}
