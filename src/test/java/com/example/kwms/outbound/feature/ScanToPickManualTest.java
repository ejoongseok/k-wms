package com.example.kwms.outbound.feature;

import com.example.kwms.common.ApiTest;
import com.example.kwms.common.Scenario;
import com.example.kwms.outbound.domain.OutboundRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

public class ScanToPickManualTest extends ApiTest {

    @Autowired
    private OutboundRepository outboundRepository;

    @BeforeEach
    void scanToPickManualSetup() {
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
        Scenario.
                createPackagingMaterial().request();
        Scenario.createOutbound().request();
        Scenario.allocatePicking().request();
        final String toteBarcode = "TOTE-002";
        Scenario.createLocation().locationBarcode(toteBarcode).request();
        Scenario.allocatePickingTote()
                .toteBarcode(toteBarcode).request();
    }

    @Test
    @DisplayName("직접 수량을 입력해서 집품.")
    @Transactional
    void scanToPickManual() {
        final String pickingToteBarcode = "TOTE-002";
        final String lpnBarcode = "LPN-001";
        final String barcodeForPickingLocation = "TOTE-001";

        Scenario.scanToPickManual()
                .pickingToteBarcode(pickingToteBarcode)
                .lpnBarcode(lpnBarcode)
                .barcodeForPickingLocation(barcodeForPickingLocation)
                .quantity(1L)
                .request();

        assertThat(outboundRepository.getBy(pickingToteBarcode).isPicked()).isEqualTo(true);
    }

}
