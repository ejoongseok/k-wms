package com.example.kwms.outbound.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.kwms.outbound.domain.OutboundFixture.anOutbound;
import static com.example.kwms.outbound.domain.OutboundProductFixture.anOutboundProduct;
import static com.example.kwms.outbound.domain.PackagingMaterialFixture.aPackagingMaterial;
import static com.example.kwms.outbound.domain.ProductFixture.aProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OutboundSplitterTest {
    private final OutboundSplitter outboundSplitter = new OutboundSplitter();

    @Test
    @DisplayName("분할할 출고 상품의 번호와 수량을 입력하면" +
            "분할된 출고 상품의 번호와 수량이 반환되고," +
            "분할된 출고 상품의 수량만큼 원본 출고 상품의 수량이 감소한다." +
            "분할된 출고 상품의 수량이 0이면 원본 출고 상품에서 제거한다.")
    void split1() {
        final Outbound target = createSuccessOutbound();
        assertThat(target.getOutboundProducts()).hasSize(2);

        final List<OutboundProduct> splitOutboundProducts = new ArrayList<>();
        splitOutboundProducts.add(anOutboundProduct().build());
        final Product product = aProduct().build();
        final Product product2 = aProduct().productNo(2L).build();
        final List<PackagingMaterial> packagingMaterials = Collections.singletonList(aPackagingMaterial().build());
        final Outbound splittedOutbound = outboundSplitter.split(target, splitOutboundProducts, packagingMaterials, List.of(product, product2));

        assertSplit(target, splittedOutbound);
    }

    private Outbound createSuccessOutbound() {
        return anOutbound()
                .pickingTote(null)
                .outboundProducts(
                        anOutboundProduct().productNo(1L),
                        anOutboundProduct().productNo(2L))
                .build();
    }

    private void assertSplit(final Outbound target, final Outbound splittedOutbound) {
        assertThat(target.getOutboundProducts()).hasSize(1);
        assertThat(target.getOutboundProducts().get(0).getProductNo()).isEqualTo(2L);
        assertThat(target.getRecommendedPackagingMaterial()).isNotNull();
        assertThat(splittedOutbound.getOutboundProducts()).hasSize(1);
        assertThat(splittedOutbound.getOutboundProducts().get(0).getProductNo()).isEqualTo(1L);
        assertThat(splittedOutbound.getRecommendedPackagingMaterial()).isNotNull();
    }

    @Test
    @DisplayName("분할할 출고 상품의 수량이 원본 출고 상품의 수량보다 많으면 예외가 발생한다.")
    void fail_split2() {
        final Outbound target = anOutbound().pickingTote(null).build();

        assertThatThrownBy(() -> {
            final List<OutboundProduct> splitOutboundProducts = List.of(anOutboundProduct().orderQuantity(2L).build());
            final List<PackagingMaterial> packagingMaterials = List.of(aPackagingMaterial().build());
            outboundSplitter.split(target, splitOutboundProducts, packagingMaterials, List.of(aProduct().build()));
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("분할할 수량이 출고 수량보다 같거나 많습니다.");
    }

    @Test
    @DisplayName("분할한 출고에 할당한 적합한 포장재가 없으면 예외가 발생한다.")
    void fail_split3() {
        final Outbound target = createSuccessOutbound();

        assertThatThrownBy(() -> {
            final List<OutboundProduct> splitOutboundProducts = List.of(anOutboundProduct().build());
            final List<PackagingMaterial> packagingMaterials = List.of(aPackagingMaterial().maxWeightInGrams(1L).build());
            outboundSplitter.split(target, splitOutboundProducts, packagingMaterials, List.of(aProduct().build(), aProduct().productNo(2L).build()));
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("추천 포장재는 필수입니다.");
    }
}