package com.example.kwms.location.feature.command;

import com.example.kwms.common.ApiTest;
import com.example.kwms.common.Scenario;
import com.example.kwms.location.domain.WarehouseTransferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class ShipmentWarehouseTransferTest extends ApiTest {

    @Autowired
    private WarehouseTransferRepository warehouseTransferRepository;

    @BeforeEach
    void shipmentWarehouseTransferSetUp() {
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
        final Long warehouseTransferNo = 1L;

        Scenario.addWarehouseTransferLocation()
                .warehouseTransferNo(warehouseTransferNo)
                .locationBarcode(locationBarcode)
                .request();
    }

    @Test
    @DisplayName("창고간 재고 이동을 출고한다.")
    void shipmentWarehouseTransfer() {
        final String warehouseTransferBarcode = "WT-001";

        Scenario
                .shipmentWarehouseTransfer()
                .warehouseTransferNo(1L)
                .request();

        assertThat(warehouseTransferRepository.getBy(1L).getShippedAt()).isNotNull();
    }

}
