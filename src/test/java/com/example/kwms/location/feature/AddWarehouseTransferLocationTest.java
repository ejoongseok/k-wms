package com.example.kwms.location.feature;

import com.example.kwms.common.ApiTest;
import com.example.kwms.common.Scenario;
import com.example.kwms.location.domain.LocationRepository;
import com.example.kwms.location.domain.UsagePurpose;
import com.example.kwms.location.domain.WarehouseTransferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

public class AddWarehouseTransferLocationTest extends ApiTest {

    @Autowired
    private AddWarehouseTransferLocation addWarehouseTransferLocation;
    @Autowired
    private WarehouseTransferRepository warehouseTransferRepository;
    @Autowired
    private LocationRepository locationRepository;

    @BeforeEach
    void addWarehouseTransferLocationSetUp() {
        Scenario.createWarehouse().request();
        Scenario.createWarehouse().request();
        final String locationBarcode = "TOTE-001";
        final String lpnBarcode = "LPN-001";
        final Long quantity = 10L;
        Scenario.createLocation().locationBarcode(locationBarcode).request()
                .createPurchaseOrder().request()
                .addReceive().request()
                .createLPN().lpnBarcode(lpnBarcode).request();
        Scenario.addManualInventory()
                .locationBarcode(locationBarcode)
                .lpnBarcode(lpnBarcode)
                .quantity(quantity)
                .request();
        Scenario.createWarehouseTransfer().request();
    }

    @Test
    @DisplayName("재고이동에 사용할 로케이션 할당")
    @Transactional
    void allocateWarehouseTransferLocation() {
        final Long warehouseTransferNo = 1L;
        final String locationBarcode = "TOTE-001";

        Scenario.addWarehouseTransferLocation()
                .warehouseTransferNo(warehouseTransferNo)
                .locationBarcode(locationBarcode)
                .request();

        assertThat(warehouseTransferRepository.getBy(warehouseTransferNo).getWarehouseTransferLocations()).hasSize(1);
        assertThat(locationRepository.getBy(locationBarcode).getUsagePurpose()).isEqualTo(UsagePurpose.WAREHOUSE_TRANSFER);
    }

}
