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
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class CreatePurchaseOrder {
    private final PurchaseOrderRepository purchaseOrderRepository;

    @Transactional
    @PostMapping("/purchase-orders")
    @ResponseStatus(HttpStatus.CREATED)
    public void request(@RequestBody @Valid final Request request) {
        final List<PurchaseOrderProduct> products = request.toProducts();
        final PurchaseOrder purchaseOrder = request.toDomain();

        purchaseOrder.assignProducts(products);

        purchaseOrderRepository.save(purchaseOrder);
    }

    public record Request(
            @NotNull(message = "창고 번호는 필수입니다.")
            Long warehouseNo,
            @NotBlank(message = "발주서명은 필수입니다.")
            String name,
            String description,
            @NotEmpty(message = "입고 상품은 필수입니다.")
            List<Request.Product> purchaseOrderProducts) {
        PurchaseOrder toDomain() {
            return new PurchaseOrder(
                    warehouseNo,
                    name,
                    description);
        }

        List<PurchaseOrderProduct> toProducts() {
            return purchaseOrderProducts.stream()
                    .map(Request.Product::toDomain)
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
            PurchaseOrderProduct toDomain() {
                return new PurchaseOrderProduct(
                        productNo,
                        requestQuantity,
                        unitPrice,
                        description);
            }
        }
    }
}
