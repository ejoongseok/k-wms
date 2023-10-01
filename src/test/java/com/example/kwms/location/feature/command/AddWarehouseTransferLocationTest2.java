package com.example.kwms.location.feature.command;

import com.example.kwms.location.domain.Location;
import com.example.kwms.location.domain.StorageType;
import com.example.kwms.location.domain.UsagePurpose;
import com.example.kwms.location.domain.WarehouseTransfer;
import com.example.kwms.location.domain.WarehouseTransferProduct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.example.kwms.location.domain.InventoryFixture.anInventory;
import static com.example.kwms.location.domain.LocationFixture.aLocation;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AddWarehouseTransferLocationTest2 {


    private AddWarehouseTransferLocation sut;

    @BeforeEach
    void setUp() {
        sut = new AddWarehouseTransferLocation(null, null, null);
    }

    @Test
    @DisplayName("재고이동에 사용할 로케이션 할당")
    void addLocation() {
        final WarehouseTransferProduct warehouseTransferProduct = new WarehouseTransferProduct(1L, 1L);
        final WarehouseTransfer warehouseTransfer = new WarehouseTransfer(1L, 2L, "WT-001", List.of(warehouseTransferProduct));
        final Location location = aLocation().inventories(anInventory()).build();

        sut.addLocation(warehouseTransfer, location);

        assertThat(location.getUsagePurpose()).isEqualTo(UsagePurpose.WAREHOUSE_TRANSFER);
        assertThat(warehouseTransfer.getWarehouseTransferLocations()).hasSize(1);
    }

    @Test
    @DisplayName("재고이동에 사용할 로케이션 할당")
    void addLocation2() {
        final WarehouseTransferProduct warehouseTransferProduct = new WarehouseTransferProduct(2L, 1L);
        final WarehouseTransfer warehouseTransfer = new WarehouseTransfer(1L, 2L, "WT-001", List.of(warehouseTransferProduct));
        final Location location = aLocation().inventories(anInventory()).build();

        assertThatThrownBy(() -> {
            sut.addLocation(warehouseTransfer, location);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("재고이동 대상이 아닌 상품이 로케이션에 존재합니다.");
    }

    @Test
    @DisplayName("재고이동에 사용할 로케이션 할당")
    void addLocation3() {
        final WarehouseTransferProduct warehouseTransferProduct = new WarehouseTransferProduct(2L, 1L);
        final WarehouseTransfer warehouseTransfer = new WarehouseTransfer(1L, 2L, "WT-001", List.of(warehouseTransferProduct));
        final Location location = aLocation().inventories(anInventory()).storageType(StorageType.RACK).build();

        assertThatThrownBy(() -> {
            sut.addLocation(warehouseTransfer, location);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("재고이동가능한 로케이션은 로케이션의 용도는 토트 또는 팔렛이어야 합니다.");
    }

    @Test
    @DisplayName("재고이동에 사용할 로케이션 할당")
    void addLocation4() {
        final WarehouseTransferProduct warehouseTransferProduct = new WarehouseTransferProduct(2L, 1L);
        final WarehouseTransfer warehouseTransfer = new WarehouseTransfer(1L, 2L, "WT-001", List.of(warehouseTransferProduct));
        final Location location = aLocation().inventories(anInventory()).warehouseNo(2L).build();

        assertThatThrownBy(() -> {
            sut.addLocation(warehouseTransfer, location);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("출발 창고 번호와 로케이션의 창고 번호가 일치하지 않습니다.");
    }

}