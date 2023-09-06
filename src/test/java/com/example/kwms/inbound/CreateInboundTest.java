package com.example.kwms.inbound;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class CreateInboundTest {

    private CreateInbound createInbound;

    @BeforeEach
    void setUp() {
        createInbound = new CreateInbound();
    }

    @Test
    @DisplayName("입고 생성")
    void createInbound() {
        final CreateInbound.Request.Product product = new CreateInbound.Request.Product(
                1L,
                2_000L,
                15_000L,
                "블랙핑크 3집 앨범[]"
        );

        final CreateInbound.Request request = new CreateInbound.Request(
                "블랙핑크 앨범 입고",
                LocalDateTime.now().plusDays(1L),
                LocalDateTime.now(),
                "23년도 블랙핑크 신규 앨범 주문",
                List.of(product));


        createInbound.request(request);

        // inboundRepository.findById(1L).asn();
    }

    private static class InboundProduct {
        private final Long productNo;
        private final Long requestQuantity;
        private final Long unitPrice;
        private final String description;
        private Inbound inbound;

        public InboundProduct(
                final Long productNo,
                final Long requestQuantity,
                final Long unitPrice,
                final String description) {
            Assert.notNull(productNo, "상품 번호는 필수입니다.");
            Assert.notNull(requestQuantity, "상품 입고 요청 수량은 필수입니다.");
            if (1 > requestQuantity)
                throw new IllegalArgumentException("상품 입고 요청 수량은 1개 이상이어야 합니다.");
            Assert.notNull(unitPrice, "상품 입고 요청 단가는 필수입니다.");
            if (0 > unitPrice)
                throw new IllegalArgumentException("상품 입고 요청 단가는 0원 이상이어야 합니다.");

            this.productNo = productNo;
            this.requestQuantity = requestQuantity;
            this.unitPrice = unitPrice;
            this.description = description;
        }

        public void assignInbound(final Inbound inbound) {
            Assert.notNull(inbound, "입고는 필수입니다.");
            this.inbound = inbound;
        }
    }

    private static class Inbound {
        private final String title;
        private final LocalDateTime estimatedArrivalAt;
        private final LocalDateTime orderRequestedAt;
        private final String description;
        private List<InboundProduct> inboundProducts = new ArrayList<>();

        public Inbound(
                final String title,
                final LocalDateTime estimatedArrivalAt,
                final LocalDateTime orderRequestedAt,
                final String description,
                final List<InboundProduct> inboundProducts) {
            Assert.hasText(title, "입고 제목은 필수입니다.");
            Assert.notNull(estimatedArrivalAt, "입고 예정일은 필수입니다.");
            Assert.notNull(orderRequestedAt, "주문 요청일은 필수입니다.");
            Assert.notNull(inboundProducts, "입고 상품은 필수입니다.");
            this.title = title;
            this.estimatedArrivalAt = estimatedArrivalAt;
            this.orderRequestedAt = orderRequestedAt;
            this.description = description;
            this.inboundProducts = inboundProducts;
            inboundProducts.forEach(product -> product.assignInbound(this));
        }

    }

    private class CreateInbound {
        public void request(final Request request) {
            final Inbound inbound = request.toDomain();
        }

        public record Request(
                @NotBlank(message = "입고 제목은 필수입니다.")
                String title,
                @NotNull(message = "입고 예정일은 필수입니다.")
                LocalDateTime estimatedArrivalAt,
                @NotNull(message = "주문 요청일은 필수입니다.")
                LocalDateTime orderRequestedAt,
                String description,
                List<Product> inboundProducts) {
            public Inbound toDomain() {
                return new Inbound(
                        title,
                        estimatedArrivalAt,
                        orderRequestedAt,
                        description,
                        inboundProducts.stream()
                                .map(Product::toDomain)
                                .collect(Collectors.toList()));
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
                public InboundProduct toDomain() {
                    return new InboundProduct(
                            productNo,
                            requestQuantity,
                            unitPrice,
                            description);
                }
            }
        }
    }
}
