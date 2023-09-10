package com.example.kwms.inbound.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.kwms.inbound.domain.InboundFixture.aInbound;
import static com.example.kwms.inbound.domain.InboundProductFixture.aInboundProduct;
import static com.example.kwms.inbound.domain.InboundProductFixture.aNoInspectedInboundProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InboundTest {

    @Test
    @DisplayName("입고 상품 수정")
    void updateProduct() {
        final Inbound inbound = aInbound()
                .inboundProducts(aNoInspectedInboundProduct().description("상품 입고 설명"))
                .build();
        final List<InboundProduct> inboundProducts = inbound.getInboundProducts();
        final InboundProduct inboundProduct = inboundProducts.get(0);
        assertThat(inboundProduct.getDescription()).isEqualTo("상품 입고 설명");

        inbound.updateProduct(
                1L,
                1L,
                1L,
                1000L,
                "상품 입고 설명 수정"
        );

        assertThat(inboundProduct.getDescription()).isEqualTo("상품 입고 설명 수정");
    }

    @Test
    @DisplayName("검수결과가 등록된 입고상품은 수정이 불가능하다.")
    void fail_updateProduct() {
        final Inbound inbound = aInbound()
                .inboundProducts(aInboundProduct())
                .build();

        assertThatThrownBy(() -> {
            inbound.updateProduct(
                    1L,
                    1L,
                    1L,
                    1000L,
                    "상품 입고 설명 수정"
            );
        }).isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("이미 검수가 완료된 상품은 변경할 수 없습니다.");
    }

    @Test
    @DisplayName("입고 상품 검수 결과 등록")
    void registerInspectionResult() {
        final Inbound inbound = aInbound()
                .inboundProducts(aNoInspectedInboundProduct().description("상품 입고 설명"))
                .build();
        final List<InboundProduct> inboundProducts = inbound.getInboundProducts();
        final InboundProduct inboundProduct = inboundProducts.get(0);
        assertThat(inboundProduct.getInspectedAt()).isNull();

        final LocalDateTime inspectedAt = LocalDateTime.now();
        final LocalDateTime arrivedAt = LocalDateTime.now();
        final String inspectionComment = "검수 결과 코멘트";
        final long acceptableQuantity = 1L;
        final long rejectedQuantity = 0L;
        final long inboundProductNo = 1L;
        inbound.registerInspectionResult(
                inboundProductNo,
                inspectedAt,
                arrivedAt,
                inspectionComment,
                acceptableQuantity,
                rejectedQuantity
        );

        assertThat(inboundProduct.getInspectedAt()).isNotNull();
    }


    @Test
    @DisplayName("검수결과가 등록된 입고상품은 재 검수가 불가능하다.")
    void fail_registerInspectionResult() {
        final Inbound inbound = aInbound()
                .inboundProducts(aInboundProduct())
                .build();

        assertThatThrownBy(() -> {
            final LocalDateTime inspectedAt = LocalDateTime.now();
            final LocalDateTime arrivedAt = LocalDateTime.now();
            final String inspectionComment = "검수 결과 코멘트";
            final long acceptableQuantity = 1L;
            final long rejectedQuantity = 0L;
            final long inboundProductNo = 1L;
            inbound.registerInspectionResult(
                    inboundProductNo,
                    inspectedAt,
                    arrivedAt,
                    inspectionComment,
                    acceptableQuantity,
                    rejectedQuantity
            );
        }).isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("이미 검수가 완료된 상품입니다.");
    }
}