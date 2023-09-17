package com.example.kwms.inbound.feature.command;

import com.example.kwms.inbound.domain.PurchaseOrder;
import com.example.kwms.inbound.domain.PurchaseOrderProduct;
import com.example.kwms.inbound.domain.PurchaseOrderRepository;
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
public class UpdatePurchaseOrder {
    private final PurchaseOrderRepository purchaseOrderRepository;

    @Transactional
    @PutMapping("/purchase-orders/{purchaseOrderNo}")
    public void request(
            @PathVariable final Long purchaseOrderNo,
            @RequestBody @Valid final Request request) {
        final PurchaseOrder purchaseOrder = purchaseOrderRepository.getBy(purchaseOrderNo);

        purchaseOrder.updatePurchaseOrder(
                request.warehouseNo,
                request.title,
                request.description,
                request.toProducts());

    }

    public record Request(
            @NotNull(message = "창고 번호는 필수입니다.")
            Long warehouseNo,
            @NotBlank(message = "입고 제목은 필수입니다.")
            String title,
            String description,
            @NotEmpty(message = "입고 상품은 필수입니다.")
            List<Product> products) {
        List<PurchaseOrderProduct> toProducts() {
            return products.stream()
                    .map(p -> new PurchaseOrderProduct(
                            p.productNo(),
                            p.requestQuantity(),
                            p.unitPrice(),
                            p.description()
                    ))
                    .toList();
        }

        public record Product(
                @NotNull(message = "상품 번호는 필수입니다.")
                Long productNo,
                @NotNull(message = "상품 입고 요청 수량은 필수입니다.")
                @Min(value = 1, message = "상품 입고 요청 수량은 1개 이상이어야 합니다.")
                Long requestQuantity,
                @NotNull(message = "상품 입고 요청 단가는 필수입니다.")
                @Min(value = 0, message = "상품 입고 요청 단가는 0원 이상이어야 합니다.")
                Long unitPrice,
                String description) {
        }
    }
}
