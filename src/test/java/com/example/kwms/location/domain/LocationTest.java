package com.example.kwms.location.domain;

import com.example.kwms.inbound.domain.LPN;
import com.example.kwms.inbound.domain.LPNFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.example.kwms.location.domain.LocationFixture.aLocation;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LocationTest {

    private Location current;
    private Location target;

    @BeforeEach
    void setUp() {
        current = aLocation()
                .locationBarcode("TOTE-001")
                .storageType(StorageType.TOTE)
                .usagePurpose(UsagePurpose.MOVE)
                .build();
        target = aLocation()
                .locationBarcode("PALLET-001")
                .storageType(StorageType.PALLET)
                .usagePurpose(UsagePurpose.MOVE)
                .build();
    }

    @Test
    @DisplayName("로케이션을 대상 로케이션의 하위로 이동시킨다.")
    void appendLocation() {

        target.appendLocation(current);

        assertThat(target.getChildren()).contains(current);
    }

    @Test
    @DisplayName("하위 로케이션에 이미 추가된 로케이션은 추가할 수 없다.")
    void fail_already_appendLocation() {
        assertThatThrownBy(() -> {
            target.appendLocation(current);
            target.appendLocation(current);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이미 하위 로케이션으로 추가된 로케이션입니다. " +
                        "로케이션 바코드: PALLET-001, 하위 로케이션 바코드: TOTE-001");
    }

    @Test
    @DisplayName("하위 로케이션으로 자기 자신을 추가할 수 없다.")
    void fail_equals_appendLocation() {
        assertThatThrownBy(() -> {
            target.appendLocation(target);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("자기 자신을 추가할 수 없습니다.");
    }

    @Test
    @DisplayName("더 큰 로케이션은 하위 로케이션으로 추가할 수 없다. PALLET를 토트에 하위로 추가할 수 없다.")
    void fail_cannotAddSubLocationToBiggerLocation() {
        assertThatThrownBy(() -> {
            current.appendLocation(target);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("크기가 같거나 더 큰 로케이션이 하위 로케이션으로 추가될 수 없습니다. " +
                        "현재 로케이션 바코드: TOTE-001 유형/사이즈: TOTE/1, " +
                        "추가하려는 로케이션 바코드: PALLET-001 유형/사이즈: PALLET/2");
    }

    @Test
    @DisplayName("크기가 같은 로케이션은 하위 로케이션으로 추가할 수 없다. 토트를 토트에 하위로 추가할 수 없다.")
    void fail_cannotAddSubLocationToBiggerLocation2() {
        final Location tote = aLocation()
                .locationBarcode("TOTE-002")
                .storageType(StorageType.TOTE)
                .usagePurpose(UsagePurpose.MOVE)
                .build();
        assertThatThrownBy(() -> {
            tote.appendLocation(current);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("크기가 같거나 더 큰 로케이션이 하위 로케이션으로 추가될 수 없습니다. " +
                        "현재 로케이션 바코드: TOTE-002 유형/사이즈: TOTE/1, " +
                        "추가하려는 로케이션 바코드: TOTE-001 유형/사이즈: TOTE/1");
    }

    @Test
    @DisplayName("로케이션에 재고를 추가한다.")
    void addInventory() {
        final LPN lpn = LPNFixture.aLPN()
                .lpnBarcode("LPN-001")
                .build();
        final Location tote = aLocation()
                .locationBarcode("TOTE-001")
                .storageType(StorageType.TOTE)
                .usagePurpose(UsagePurpose.MOVE)
                .build();
        tote.addInventory(lpn);

        final List<Inventory> inventories = tote.getInventories();
        assertThat(inventories).hasSize(1);
        assertThat(inventories.get(0).equalsLPN(lpn)).isTrue();
        assertThat(inventories.get(0).getQuantity()).isEqualTo(1L);
    }

    @Test
    @DisplayName("로케이션에 재고를 추가한다.")
    void addInventory2() {
        final LPN lpn = LPNFixture.aLPN()
                .lpnBarcode("LPN-001")
                .build();
        final Location tote = aLocation()
                .locationBarcode("TOTE-001")
                .storageType(StorageType.TOTE)
                .usagePurpose(UsagePurpose.MOVE)
                .build();
        tote.addInventory(lpn);
        tote.addInventory(lpn);

        final List<Inventory> inventories = tote.getInventories();
        assertThat(inventories).hasSize(1);
        assertThat(inventories.get(0).equalsLPN(lpn)).isTrue();
        assertThat(inventories.get(0).getQuantity()).isEqualTo(2L);
    }


    @Test
    @DisplayName("로케이션에 재고 수량을 직접입력해서 추가한다.")
    void addManualInventory() {
        final LPN lpn = LPNFixture.aLPN()
                .lpnBarcode("LPN-001")
                .build();
        final Location tote = aLocation()
                .locationBarcode("TOTE-001")
                .storageType(StorageType.TOTE)
                .usagePurpose(UsagePurpose.MOVE)
                .build();
        final Long quantity = 10L;
        tote.addManualInventory(lpn, quantity);

        final List<Inventory> inventories = tote.getInventories();
        assertThat(inventories).hasSize(1);
        assertThat(inventories.get(0).equalsLPN(lpn)).isTrue();
        assertThat(inventories.get(0).getQuantity()).isEqualTo(10L);
    }

    @Test
    @DisplayName("로케이션에 재고 수량을 직접입력해서 추가한다.")
    void addManualInventory2() {
        final LPN lpn = LPNFixture.aLPN()
                .lpnBarcode("LPN-001")
                .build();
        final Location tote = aLocation()
                .locationBarcode("TOTE-001")
                .storageType(StorageType.TOTE)
                .usagePurpose(UsagePurpose.MOVE)
                .build();
        final Long quantity = 10L;
        tote.addManualInventory(lpn, quantity);
        tote.addManualInventory(lpn, quantity);

        final List<Inventory> inventories = tote.getInventories();
        assertThat(inventories).hasSize(1);
        assertThat(inventories.get(0).equalsLPN(lpn)).isTrue();
        assertThat(inventories.get(0).getQuantity()).isEqualTo(20L);
    }


    @Test
    @DisplayName("추가하려고 입력한 재고 수량이 0보다 작으면 예외가 발생한다.")
    void fail_addManualInventory() {
        final LPN lpn = LPNFixture.aLPN()
                .lpnBarcode("LPN-001")
                .build();
        final Location tote = aLocation()
                .locationBarcode("TOTE-001")
                .storageType(StorageType.TOTE)
                .usagePurpose(UsagePurpose.MOVE)
                .build();
        final Long quantity = 0L;
        assertThatThrownBy(() -> {
            tote.addManualInventory(lpn, quantity);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("수량은 0개 이상이어야 합니다.");
    }


    @Test
    @DisplayName("로케이션에 재고 수량을 조정한다.")
    void adjustInventory() {
        final LPN lpn = LPNFixture.aLPN()
                .lpnBarcode("LPN-001")
                .build();
        final Location tote = aLocation()
                .locationBarcode("TOTE-001")
                .storageType(StorageType.TOTE)
                .usagePurpose(UsagePurpose.MOVE)
                .build();
        final Long quantity = 10L;
        tote.addManualInventory(lpn, quantity);

        tote.adjustInventory(lpn, 1L);

        final List<Inventory> inventories = tote.getInventories();
        assertThat(inventories).hasSize(1);
        assertThat(inventories.get(0).equalsLPN(lpn)).isTrue();
        assertThat(inventories.get(0).getQuantity()).isEqualTo(1L);
    }

    @Test
    @DisplayName("로케이션에 재고 수량을 조정한다. - 조정하려는 수량이 0보다 작으면 예외가 발생한다.")
    void fail_adjustInventory() {
        final LPN lpn = LPNFixture.aLPN()
                .lpnBarcode("LPN-001")
                .build();
        final Location tote = aLocation()
                .locationBarcode("TOTE-001")
                .storageType(StorageType.TOTE)
                .usagePurpose(UsagePurpose.MOVE)
                .build();
        final Long quantity = 10L;
        tote.addManualInventory(lpn, quantity);

        assertThatThrownBy(() -> {
            tote.adjustInventory(lpn, -1L);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("수량은 0개 이상이어야 합니다.");

    }

}