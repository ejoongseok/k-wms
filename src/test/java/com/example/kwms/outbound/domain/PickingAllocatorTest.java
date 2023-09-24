package com.example.kwms.outbound.domain;

import com.example.kwms.location.domain.Inventory;
import com.example.kwms.location.domain.UsagePurpose;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static com.example.kwms.inbound.domain.LPNFixture.aLPN;
import static com.example.kwms.location.domain.InventoryFixture.anInventory;
import static com.example.kwms.location.domain.LocationFixture.aLocation;
import static com.example.kwms.outbound.domain.OutboundFixture.anOutbound;
import static com.example.kwms.outbound.domain.OutboundProductFixture.anOutboundProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PickingAllocatorTest {

    private PickingAllocator pickingAllocator;

    @BeforeEach
    void setUp() {
        pickingAllocator = new PickingAllocator();
    }

    @Test
    @DisplayName(
            "출고 상품에 대한 집품 목록을 할당한다." +
                    "1. 재고는 유통기한이 가장 빠른 순서대로 할당한다." +
                    "2. 유통기한이 같으면 재고 수량이 가장 많은 순서대로 할당한다. " +
                    "3. 유통기한과 재고 수량이 같으면 로케이션 순서대로 할당한다." +
                    "재고가 충분하지 않을 경우 예외가 발생한다."
    )
    void allocatePicking() {
        final List<Inventory> inventories = createInventories();
        final Outbound outbound = anOutbound()
                .outboundProducts(anOutboundProduct().orderQuantity(10L))
                .build();

        pickingAllocator.allocatePicking(outbound, inventories);

        assertAllocatePickings(outbound, inventories);
    }

    private List<Inventory> createInventories() {
        final LocalDateTime now = LocalDateTime.now();
        return Arrays.asList(
                anInventory()
                        .inventoryNo(1L)
                        .quantity(4L)
                        .lpn(aLPN().expiringAt(now.plusDays(3L)))
                        .location(aLocation().locationBarcode("A1").usagePurpose(UsagePurpose.DISPLAY))
                        .build()
                ,
                anInventory()
                        .inventoryNo(2L)
                        .quantity(3L)
                        .lpn(aLPN().expiringAt(now.plusDays(1L)))
                        .location(aLocation().locationBarcode("A2").usagePurpose(UsagePurpose.DISPLAY))
                        .build()
                ,
                anInventory()
                        .inventoryNo(3L)
                        .quantity(4L)
                        .lpn(aLPN().expiringAt(now.plusDays(1L)))
                        .location(aLocation().locationBarcode("A3").usagePurpose(UsagePurpose.DISPLAY))
                        .build()
                ,
                anInventory()
                        .inventoryNo(4L)
                        .quantity(4L)
                        .lpn(aLPN().expiringAt(now.plusDays(1L)))
                        .location(aLocation().locationBarcode("A1-1").usagePurpose(UsagePurpose.DISPLAY))
                        .build()
                ,
                anInventory().inventoryNo(5L).lpn(aLPN().expiringAt(now.minusDays(1L))).location(aLocation().locationBarcode("A1-1").usagePurpose(UsagePurpose.DISPLAY)).build(),
                anInventory().inventoryNo(6L).quantity(0L).location(aLocation().locationBarcode("A1-1").usagePurpose(UsagePurpose.DISPLAY)).build()
        );
    }

    private void assertAllocatePickings(final Outbound outbound, final List<Inventory> inventories) {
        final List<Picking> pickings = outbound.getPickings();
        final Picking picking1 = pickings.get(0);
        final Picking picking2 = pickings.get(1);
        final Picking picking3 = pickings.get(2);
        final Inventory inventory1 = getBy(inventories, picking1.getInventory());
        final Inventory inventory2 = getBy(inventories, picking2.getInventory());
        final Inventory inventory3 = getBy(inventories, picking3.getInventory());
        assertThat(pickings).hasSize(3);
        assertThat(picking1.getQuantityRequiredForPick()).isEqualTo(4L);
        assertThat(picking2.getQuantityRequiredForPick()).isEqualTo(4L);
        assertThat(picking3.getQuantityRequiredForPick()).isEqualTo(2L);
        assertThat(inventory1.getQuantity()).isEqualTo(0L);
        assertThat(inventory1.getLocationBarcode()).isEqualTo("A1-1");
        assertThat(inventory2.getQuantity()).isEqualTo(0L);
        assertThat(inventory2.getLocationBarcode()).isEqualTo("A3");
        assertThat(inventory3.getQuantity()).isEqualTo(1L);
        assertThat(inventory3.getLocationBarcode()).isEqualTo("A2");
    }

    private Inventory getBy(final List<Inventory> inventories, final Inventory inventory) {
        return inventories.stream()
                .filter(i -> i.equals(inventory))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 재고가 없습니다."));
    }


    @Test
    @DisplayName("재고가 충분하지 않을 경우 예외가 발생한다.")
    void fail_over_order_quantity_allocatePicking() {
        final List<Inventory> inventories = createInventories();
        final long overQuantity = 16L;
        final Outbound outbound = anOutbound()
                .outboundProducts(anOutboundProduct().orderQuantity(overQuantity))
                .build();

        assertThatThrownBy(() -> {
            pickingAllocator.allocatePicking(outbound, inventories);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("재고가 부족합니다.");
    }
}