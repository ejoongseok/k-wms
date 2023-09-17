package com.example.kwms.outbound.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static com.example.kwms.location.domain.InventoryFixture.anInventory;
import static com.example.kwms.outbound.domain.OrderFixture.anOrder;
import static com.example.kwms.outbound.domain.PackagingMaterialDimensionFixture.aPackagingMaterialDimension;
import static com.example.kwms.outbound.domain.PackagingMaterialFixture.aPackagingMaterial;
import static org.assertj.core.api.Assertions.assertThat;

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