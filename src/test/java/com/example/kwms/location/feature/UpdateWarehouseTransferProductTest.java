package com.example.kwms.location.feature;

import com.example.kwms.common.ApiTest;
import com.example.kwms.common.Scenario;
import com.example.kwms.location.domain.WarehouseTransferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

public class UpdateWarehouseTransferProductTest extends ApiTest {

    @Autowired
    private WarehouseTransferRepository warehouseTransferRepository;

    @BeforeEach
    void updateWarehouseTransferProductSetUp() {
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
    }

    @Test
    @DisplayName("창고간 재고 이동 상품을 수정한다.")
    @Transactional
    void updateWarehouseTransferProduct() {

        Scenario.updateWarehouseTransferProduct().request();

        assertThat(warehouseTransferRepository.getBy(1L).getProducts().get(0).getQuantity()).isEqualTo(2L);
    }

}
