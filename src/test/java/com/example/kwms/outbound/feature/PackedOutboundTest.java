package com.example.kwms.outbound.feature;

import com.example.kwms.common.ApiTest;
import com.example.kwms.common.Scenario;
import com.example.kwms.outbound.domain.OutboundRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class PackedOutboundTest extends ApiTest {

    @Autowired
    private OutboundRepository outboundRepository;

    @BeforeEach
    void packedOutboundSetUp() {
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
        Scenario.
                createPackagingMaterial().request();
        Scenario.createOutbound().request();
        Scenario.allocatePicking().request();
        final String toteBarcode = "TOTE-002";
        Scenario.createLocation().locationBarcode(toteBarcode).request();
        Scenario.allocatePickingTote()
                .toteBarcode(toteBarcode).request();

        Scenario.scanToPick()
                .pickingToteBarcode(toteBarcode)
                .lpnBarcode(lpnBarcode)
                .barcodeForPickingLocation(locationBarcode)
                .request();
        Scenario.inspectedOutbound().request();
    }

    @Test
    @DisplayName("출고를 포장완료 처리한다.")
    void packedOutbound() {
        final Long outboundNo = 1L;
        final String packagingMaterialCode = "code";
        final Long weightInGrams = 100L;
        final Long boxWidthInMillimeters = 10L;
        final Long boxLengthInMillimeters = 10L;
        final Long boxHeightInMillimeters = 10L;

        Scenario.packedOutbound()
                .outboundNo(outboundNo)
                .packagingMaterialCode(packagingMaterialCode)
                .weightInGrams(weightInGrams)
                .boxWidthInMillimeters(boxWidthInMillimeters)
                .boxLengthInMillimeters(boxLengthInMillimeters)
                .boxHeightInMillimeters(boxHeightInMillimeters)
                .request();

        assertThat(outboundRepository.getBy(outboundNo).isPicked()).isEqualTo(true);
    }

}
