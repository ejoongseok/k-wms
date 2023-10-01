package com.example.kwms.location.feature.command;

import com.example.kwms.common.ApiTest;
import com.example.kwms.common.Scenario;
import com.example.kwms.location.domain.WarehouseTransfer;
import com.example.kwms.location.domain.WarehouseTransferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class UpdateWarehouseTransferTest extends ApiTest {

    @Autowired
    private WarehouseTransferRepository warehouseTransferRepository;

    @BeforeEach
    void updateWarehouseTransferSetUp() {
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
    @DisplayName("창고간 재고 이동을 수정한다.")
    void updateWarehouseTransfer() {
        Scenario.updateWarehouseTransfer().request();

        final WarehouseTransfer transfer = warehouseTransferRepository.getBy(1L);
        assertThat(transfer.getBarcode()).isEqualTo("WT-002");
    }

}
