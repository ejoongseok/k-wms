package com.example.kwms.outbound.domain;

import com.example.kwms.location.domain.Inventory;
import com.example.kwms.location.domain.Location;
import com.example.kwms.location.domain.StorageType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.example.kwms.location.domain.InventoryFixture.anInventory;
import static com.example.kwms.location.domain.LocationFixture.aLocation;
import static com.example.kwms.outbound.domain.OutboundFixture.anOutbound;
import static com.example.kwms.outbound.domain.OutboundProductFixture.anOutboundProduct;
import static com.example.kwms.outbound.domain.PackagingMaterialFixture.aPackagingMaterial;
import static com.example.kwms.outbound.domain.PickingFixture.aPicking;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OutboundTest {

    @Test
    @DisplayName("출고에 집품할 때 사용할 토트를 할당한다.")
    void allocatePickingTote() {
        final Outbound outbound = anOutbound()
                .pickingTote(null)
                .packagingMaterial(aPackagingMaterial())
                .build();
        final Location tote = aLocation().build();

        outbound.allocatePickingTote(tote);

        assertThat(outbound.getPickingTote()).isNotNull();
    }

    @Test
    @DisplayName("출고에 집품할 때 사용할 토트를 null으로 할당하면 예외가 발생한다.")
    void fail_null_paramter_allocatePickingTote() {
        final Outbound outbound = anOutbound().build();
        final Location tote = null;

        assertThatThrownBy(() -> {
            outbound.allocatePickingTote(tote);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("출고에 할당할 토트는 필수 입니다.");
    }

    @Test
    @DisplayName("출고에 할당할 로케이션이 토트가 아니면 예외가 발생한다.")
    void fail_not_tote_allocatePickingTote() {
        final Outbound outbound = anOutbound().build();
        final Location pallet = aLocation()
                .storageType(StorageType.PALLET)
                .build();

        assertThatThrownBy(() -> {
            outbound.allocatePickingTote(pallet);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("할당하려는 로케이션이 토트가 아닙니다.");
    }

    @Test
    @DisplayName("집품에 사용하려는 토트에 상품이 존재하는 경우 예외가 발생한다.")
    void fail_already_exsists_inventory_allocatePickingTote() {
        final Outbound outbound = anOutbound().build();
        final Location tote = aLocation()
                .inventories(anInventory())
                .build();

        assertThatThrownBy(() -> {
            outbound.allocatePickingTote(tote);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("할당하려는 토트에 상품이 존재합니다.");
    }

    @Test
    @DisplayName("출고에 이미 토트바구니가 할당된 경우 재 할당하려고 하면 예외가 발생한다.")
    void fail_already_allocate_tote_allocatePickingTote() {
        final Outbound outbound = anOutbound().build();
        final Location tote = aLocation().build();

        assertThatThrownBy(() -> {
            outbound.allocatePickingTote(tote);
        }).isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("이미 출고에 토트가 할당되어 있습니다.");
    }

    @Test
    @DisplayName("출고에 사용할 포장재가 할당되어 있지 않은 상태로 토트 바구니를 할당하면 예외가 발생한다.")
    void fail_null_pacakagingMaterial_allocatePickingTote() {
        final Outbound outbound = anOutbound()
                .pickingTote(null)
                .packagingMaterial(null)
                .build();
        final Location tote = aLocation().build();

        assertThatThrownBy(() -> {
            outbound.allocatePickingTote(tote);
        }).isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("포장재가 할당되어 있지 않습니다.");
    }

    @Test
    @DisplayName("집품할 상품을 스캔한다.")
    void scanToPick() {
        final Outbound outbound = anOutbound()
                .outboundProducts(anOutboundProduct().pickings(aPicking()))
                .build();
        final Inventory inventory = anInventory().build();

        outbound.scanToPick(inventory);

        assertThat(outbound.isPicked()).isEqualTo(true);
    }

    @Test
    @DisplayName("집품할 상품을 스캔한다. 취소된 출고는 집품할 수 없다.")
    void fail_scanToPick() {
        final Outbound outbound = anOutbound()
                .outboundProducts(anOutboundProduct().pickings(aPicking()))
                .build();
        final Inventory inventory = anInventory().build();
        outbound.canceled("취소");

        assertThatThrownBy(() -> {
            outbound.scanToPick(inventory);
        }).isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("취소된 출고는 집품을 할 수 없습니다");
    }

    @Test
    @DisplayName("집품할 상품을 스캔한다. 집품 목록이 할당된 상태에서만 집품할 수 있다.")
    void fail_scanToPick2() {
        final Outbound outbound = anOutbound()
                .build();
        final Inventory inventory = anInventory().build();

        assertThatThrownBy(() -> {
            outbound.scanToPick(inventory);
        }).isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("집품 목록이 할당된 상태에서만 집품할 수 있습니다.");
    }

    @Test
    @DisplayName("집품할 상품을 스캔한다. 토트가 할당되지 않은 출고는 집품할 수 없다.")
    void fail_scanToPick3() {
        final Outbound outbound = anOutbound()
                .outboundProducts(anOutboundProduct().pickings(aPicking()))
                .pickingTote(null)
                .build();
        final Inventory inventory = anInventory().build();

        assertThatThrownBy(() -> {
            outbound.scanToPick(inventory);
        }).isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("토트가 할당되지 않은 출고는 집품할 수 없습니다.");
    }

    @Test
    @DisplayName("집품할 상품을 스캔한다. 집품에 할당되지 않은 재고를 집품시 예외가 발생한다.")
    void fail_scanToPick4() {
        final Outbound outbound = anOutbound()
                .outboundProducts(anOutboundProduct().pickings(aPicking().inventory(anInventory().inventoryNo(2L))))
                .build();
        final Inventory inventory = anInventory().inventoryNo(1L).build();

        assertThatThrownBy(() -> {
            outbound.scanToPick(inventory);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("스캔한 재고는 집품목록에 할당되어 있지 않습니다.");
    }

    @Test
    @DisplayName("집품 수동 완료처리")
    void manualCompletePicking() {
        final Outbound outbound = anOutbound()
                .outboundProducts(anOutboundProduct().pickings(aPicking().quantityRequiredForPick(2L)))
                .build();

        assertThat(outbound.getPickingTote().getInventories()).isEmpty();
        assertThat(outbound.isPicked()).isEqualTo(false);
        outbound.manualCompletePicking(1L);
        assertThat(outbound.getPickingTote().getInventories()).hasSize(1);
        assertThat(outbound.getPickingTote().getInventories().get(0).getQuantity()).isEqualTo(2L);
        assertThat(outbound.isPicked()).isEqualTo(true);
    }

    @Test
    @DisplayName("집품 수동 완료처리")
    void manualCompletePicking2() {
        final Outbound outbound = anOutbound()
                .outboundProducts(anOutboundProduct().pickings(aPicking().quantityRequiredForPick(2L)))
                .build();
        final Inventory inventory = anInventory().build();

        outbound.scanToPick(inventory);

        assertThat(outbound.getPickingTote().getInventories()).hasSize(1);
        assertThat(outbound.getPickingTote().getInventories().get(0).getQuantity()).isEqualTo(1L);
        assertThat(outbound.isPicked()).isEqualTo(false);
        outbound.manualCompletePicking(1L);
        assertThat(outbound.getPickingTote().getInventories()).hasSize(1);
        assertThat(outbound.getPickingTote().getInventories().get(0).getQuantity()).isEqualTo(2L);
        assertThat(outbound.isPicked()).isEqualTo(true);
    }

    @Test
    @DisplayName("집품 수동 완료처리 - 이미 완료처리된 피킹은 예외가 발생한다.")
    void manualCompletePicking3() {
        final Outbound outbound = anOutbound()
                .outboundProducts(anOutboundProduct().pickings(aPicking().quantityRequiredForPick(2L)))
                .build();

        outbound.manualCompletePicking(1L);
        assertThatThrownBy(() -> {
            outbound.manualCompletePicking(1L);
        }).isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("이미 집품이 완료된 출고상품입니다.");
    }

    @Test
    @DisplayName("집품 수동 완료처리 - 출고 중지된 출고는 집품할 수 없다.")
    void manualCompletePicking4() {
        final Outbound outbound = anOutbound()
                .outboundProducts(anOutboundProduct().pickings(aPicking().quantityRequiredForPick(2L)))
                .build();
        outbound.canceled("");

        assertThatThrownBy(() -> {
            outbound.manualCompletePicking(1L);
        }).isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("취소된 출고는 피킹할 수 없습니다.");
    }

    @Test
    @DisplayName("집품 수동 완료처리 - 집품이 할당되지 않은 출고상품은 집품 완료 할 수 없다.")
    void manualCompletePicking5() {
        final Outbound outbound = anOutbound()
                .build();

        assertThatThrownBy(() -> {
            outbound.manualCompletePicking(1L);
        }).isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("집품 목록이 할당된 상태에서만 집품할 수 있습니다.");
    }

    @Test
    @DisplayName("집품 수동 완료처리 - 집품할 토트가 할당되지 않은 출고상품은 집품 완료 할 수 없다.")
    void manualCompletePicking6() {
        final Outbound outbound = anOutbound()
                .outboundProducts(anOutboundProduct().pickings(aPicking().quantityRequiredForPick(2L)))
                .pickingTote(null)
                .build();

        assertThatThrownBy(() -> {
            outbound.manualCompletePicking(1L);
        }).isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("토트가 할당되지 않은 출고는 집품할 수 없습니다.");
    }

}