package com.example.kwms.inbound.feature;

import com.example.kwms.inbound.domain.Inbound;
import com.example.kwms.inbound.domain.InboundProduct;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;


class CreateInbound {
    public void request(final Request request) {
        final List<InboundProduct> products = request.toProducts();
        final Inbound inbound = request.toDomain();

        inbound.assignProducts(products);
    }

    public record Request(
            @NotBlank(message = "입고 제목은 필수입니다.")
            String title,
            @NotNull(message = "입고 예정일은 필수입니다.")
            LocalDateTime estimatedArrivalAt,
            @NotNull(message = "주문 요청일은 필수입니다.")
            LocalDateTime orderRequestedAt,
            String description,
            @NotEmpty(message = "입고 상품은 필수입니다.")
            List<Request.Product> inboundProducts) {
        Inbound toDomain() {
            return new Inbound(
                    title,
                    estimatedArrivalAt,
                    orderRequestedAt,
                    description);
        }

        List<InboundProduct> toProducts() {
            return inboundProducts.stream()
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
            InboundProduct toDomain() {
                return new InboundProduct(
                        productNo,
                        requestQuantity,
                        unitPrice,
                        description);
            }
        }
    }
}
