package com.example.kwms.outbound.domain;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.example.kwms.inbound.domain.LPNFixture.aLPN;
import static com.example.kwms.location.domain.InventoryFixture.anInventory;
import static com.example.kwms.outbound.domain.OrderFixture.anOrder;
import static com.example.kwms.outbound.domain.OrderProductFixture.anOrderProduct;
import static com.example.kwms.outbound.domain.PackagingMaterialDimensionFixture.aPackagingMaterialDimension;
import static com.example.kwms.outbound.domain.PackagingMaterialFixture.aPackagingMaterial;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ConstructOutboundTest {

    ConstructOutbound sut = new ConstructOutbound();
    private final Long warehouseNo = 1L;

    @Test
    @DisplayName("출고를 생성한다.")
    void createOutbound() {
        final Outbound outbound = sut.create(
                warehouseNo,
                List.of(anInventory().build()),
                List.of(aPackagingMaterial().build()),
                anOrder().build(),
                false,
                LocalDate.now(),
                100L,
                100L);

        assertThat(outbound).isNotNull();
    }

    @Test
    @DisplayName("출고를 생성한다. - 출고 수량이 재고 수량보다 많을 경우 예외가 발생한다.")
    @Disabled("재고 수량을 확인 하는 로직 제거")
    void fail_over_quantity_createOutbound() {

        assertThatThrownBy(() -> {
            sut.create(
                    warehouseNo, List.of(anInventory().build()),
                    List.of(aPackagingMaterial().build()),
                    anOrder().orderProduct(
                            anOrderProduct().orderQuantity(2L)
                    ).build(),
                    false,
                    LocalDate.now(),
                    100L,
                    100L);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("재고가 부족합니다.");
    }

    @Test
    @DisplayName("출고를 생성한다. - (유통기한이 지나서 재고가 부족)출고 수량이 재고 수량보다 많을 경우 예외가 발생한다.")
    @Disabled("재고 수량을 확인 하는 로직 제거")
    void expire_createOutbound() {

        assertThatThrownBy(() -> {
            sut.create(
                    warehouseNo, List.of(anInventory()
                            .lpn(aLPN().expiringAt(LocalDateTime.now().minusDays(1))).build())
                    ,
                    List.of(aPackagingMaterial().build()),
                    anOrder().orderProduct(anOrderProduct().orderQuantity(10L)).build(),
                    false,
                    LocalDate.now(),
                    100L,
                    100L);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("재고가 부족합니다.");
    }

    @Test
    @DisplayName("출고를 생선한다. - 주문을 포장할 포장재를 찾을 수 없다. - 제한 무게를 초과")
    void over_max_weight_createOutbound() {
        final Outbound outbound = sut.create(
                warehouseNo, List.of(anInventory().build()),
                List.of(aPackagingMaterial().maxWeightInGrams(1L).build()),
                anOrder().build(),
                false,
                LocalDate.now(),
                100L,
                100L);

        assertThat(outbound.getRecommendedPackagingMaterial()).isNull();
    }

    @Test
    @DisplayName("출고를 생선한다. - 주문을 포장할 포장재를 찾을 수 없다. - 허용 가능한 부피 초과")
    void over_volume_createOutbound() {
        final Outbound outbound = sut.create(
                warehouseNo, List.of(anInventory().build()),
                List.of(aPackagingMaterial().dimension(
                        aPackagingMaterialDimension()
                                .innerHeightInMillimeters(1L)
                                .innerWidthInMillimeters(1L)
                ).build()),
                anOrder().build(),
                false,
                LocalDate.now(),
                1000L, 10000L);

        assertThat(outbound.getRecommendedPackagingMaterial()).isNull();
    }
}