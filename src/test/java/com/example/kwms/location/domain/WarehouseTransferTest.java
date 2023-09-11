package com.example.kwms.location.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static com.example.kwms.location.domain.InventoryFixture.anInventory;
import static com.example.kwms.location.domain.LocationFixture.aLocation;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WarehouseTransferTest {

    @Test
    void shipment() {
        final WarehouseTransferProduct warehouseTransferProduct = new WarehouseTransferProduct(1L, 1L);
        final WarehouseTransfer warehouseTransfer = new WarehouseTransfer(1L, 2L, "WT-001", List.of(warehouseTransferProduct));
        warehouseTransfer.addLocation(aLocation().inventories(anInventory()).build());

        warehouseTransfer.shipment();
    }

    @Test
    void shipment2() {
        final WarehouseTransferProduct warehouseTransferProduct = new WarehouseTransferProduct(1L, 1L);
        final WarehouseTransferProduct warehouseTransferProduct2 = new WarehouseTransferProduct(2L, 1L);
        final WarehouseTransfer warehouseTransfer = new WarehouseTransfer(1L, 2L, "WT-001", List.of(warehouseTransferProduct, warehouseTransferProduct2));
        warehouseTransfer.addLocation(aLocation().inventories(anInventory()).build());

        assertThatThrownBy(() -> {
            warehouseTransfer.shipment();
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("출하할 상품이 로케이션에 존재하지 않습니다.");

    }

    @Test
    void shipment3() {
        final WarehouseTransferProduct warehouseTransferProduct = new WarehouseTransferProduct(1L, 1L);
        final WarehouseTransfer warehouseTransfer = new WarehouseTransfer(1L, 2L, "WT-001", List.of(warehouseTransferProduct));
        warehouseTransfer.addLocation(aLocation().inventories(anInventory()).build());
        warehouseTransfer.shipment();
        assertThatThrownBy(() -> {
            warehouseTransfer.shipment();
        }).isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("이미 출하된 재고이동입니다.");
    }

    @Test
    void shipment4() {
        final WarehouseTransferProduct warehouseTransferProduct = new WarehouseTransferProduct(1L, 1L);
        final WarehouseTransfer warehouseTransfer = new WarehouseTransfer(1L, 2L, "WT-001", List.of(warehouseTransferProduct));
        assertThatThrownBy(() -> {
            warehouseTransfer.shipment();
        }).isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("출하할 로케이션이 존재하지 않습니다.");
    }
}