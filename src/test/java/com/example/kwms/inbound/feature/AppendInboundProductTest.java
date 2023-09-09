package com.example.kwms.inbound.feature;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AppendInboundProductTest {


    private AppendInboundProduct appendInboundProduct;

    @BeforeEach
    void setUp() {
        appendInboundProduct = new AppendInboundProduct();
    }

    @Test
    @DisplayName("입고 상품을 추가한다.")
    void appendInboundProduct() {
        final Long inboundNo = 1L;
        final AppendInboundProduct.Request.Product product = new AppendInboundProduct.Request.Product(
                1L,
                1_000L,
                15_000L,
                "블랙핑크 3집 앨범[] - 미입고분 추가 입고"
        );
        final AppendInboundProduct.Request request = new AppendInboundProduct.Request(
                inboundNo,
                product
        );
        appendInboundProduct.request(request);
    }

    private class AppendInboundProduct {
        public void request(final Request request) {
            throw new UnsupportedOperationException("Unsupported request");
        }

        public record Request(
                @NotNull(message = "입고 번호는 필수입니다.")
                Long inboundNo,
                @NotNull(message = "입고 상품은 필수입니다.")
                Request.Product product) {
            public record Product(
                    @NotNull(message = "상품 번호는 필수입니다.")
                    Long productNo,
                    @NotNull(message = "상품 입고 요청 수량은 필수입니다.")
                    @Min(value = 1, message = "상품 입고 요청 수량은 1개 이상이어야 합니다.")
                    Long requestQuantity,
                    @NotNull(message = "상품 입고 요청 단가는 필수입니다.")
                    @Min(value = 0, message = "상품 입고 요청 단가는 0원 이상이어야 합니다.")
                    Long unitPrice,
                    String description
            ) {
            }
        }
    }
}
