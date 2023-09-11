package com.example.kwms.location.feature;

import com.example.kwms.common.ApiTest;
import com.example.kwms.common.Scenario;
import com.example.kwms.location.domain.WarehouseTransferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class ReceiveWarehouseTransferTest extends ApiTest {

    @Autowired
    private WarehouseTransferRepository warehouseTransferRepository;

    @BeforeEach
    void receiveWarehouseTransferSetUp() {
        Scenario.createWarehouse().request();
        Scenario.createWarehouse().request();
        final String locationBarcode = "TOTE-001";
        final String lpnBarcode = "LPN-001";
        final Long quantity = 10L;
        Scenario.createLocation().locationBarcode(locationBarcode).request()
                .createInbound().request()
                .registerInboundProductInspectionResult().request()
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

        final String warehouseTransferBarcode = "WT-001";

        Scenario
                .shipmentWarehouseTransfer()
                .warehouseTransferBarcode(warehouseTransferBarcode)
                .request();
    }

    @Test
    @DisplayName("창고간 재고 이동을 입고한다.")
    void receiveWarehouseTransfer() {
        final String warehouseTransferBarcode = "WT-001";

        Scenario.receiveWarehouseTransfer().warehouseTransferBarcode(warehouseTransferBarcode).request();

        assertThat(warehouseTransferRepository.getBy(warehouseTransferBarcode).getReceivedAt()).isNotNull();
    }

}
