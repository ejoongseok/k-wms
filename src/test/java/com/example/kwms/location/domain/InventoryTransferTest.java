package com.example.kwms.location.domain;

import com.example.kwms.inbound.domain.LPN;
import com.example.kwms.inbound.domain.LPNFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.example.kwms.location.domain.LocationFixture.aLocation;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InventoryTransferTest {

    InventoryTransfer inventoryTransfer = new InventoryTransfer();

    @Test
    @DisplayName("로케이션에서 다른 로케이션으로 재고를 이동시킨다.")
    void execute() {
        final LPN lpn = LPNFixture.aLPN()
                .lpnBarcode("LPN-001")
                .build();
        final Location tote = aLocation()
                .locationBarcode("TOTE-001")
                .storageType(StorageType.TOTE)
                .usagePurpose(UsagePurpose.MOVE)
                .build();
        final Location tote2 = aLocation()
                .locationBarcode("TOTE-002")
                .storageType(StorageType.TOTE)
                .usagePurpose(UsagePurpose.MOVE)
                .build();
        final Long quantity = 10L;
        tote.addManualInventory(lpn, quantity);

        final long transferQuantity = 1L;
        inventoryTransfer.execute(tote, tote2, lpn, transferQuantity);

        final List<Inventory> inventories = tote.getInventories();
        assertThat(inventories).hasSize(1);
        assertThat(inventories.get(0).equalsLPN(lpn)).isTrue();
        assertThat(inventories.get(0).getQuantity()).isEqualTo(9L);

        final List<Inventory> inventories2 = tote2.getInventories();
        assertThat(inventories2).hasSize(1);
        assertThat(inventories2.get(0).equalsLPN(lpn)).isTrue();
        assertThat(inventories2.get(0).getQuantity()).isEqualTo(1L);
    }

    @Test
    @DisplayName("로케이션에서 다른 로케이션으로 재고를 이동시킨다.")
    void fail_execute() {
        final LPN lpn = LPNFixture.aLPN()
                .lpnBarcode("LPN-001")
                .build();
        final Location tote = aLocation()
                .locationBarcode("TOTE-001")
                .storageType(StorageType.TOTE)
                .usagePurpose(UsagePurpose.MOVE)
                .build();
        final Location tote2 = aLocation()
                .locationBarcode("TOTE-002")
                .storageType(StorageType.TOTE)
                .usagePurpose(UsagePurpose.MOVE)
                .build();
        final Long quantity = 10L;
        tote.addManualInventory(lpn, quantity);

        final long transferQuantity = 11L;
        assertThatThrownBy(() -> {
            inventoryTransfer.execute(tote, tote2, lpn, transferQuantity);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("감소시킬 수량은 재고 수량보다 작아야 합니다. " +
                        "현재 재고 수량: 10, 감소시킬 수량: 11");
    }
}