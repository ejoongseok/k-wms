package com.example.kwms.outbound.feature;

import com.example.kwms.common.ApiTest;
import com.example.kwms.common.Scenario;
import com.example.kwms.outbound.domain.OutboundRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestWaybillTest extends ApiTest {

    @Autowired
    private RequestWaybill requestWaybill;
    @Autowired
    private OutboundRepository outboundRepository;

    @BeforeEach
    void requestWaybillSetUp() {
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

        Scenario.scanToPick()
                .pickingToteBarcode(toteBarcode)
                .lpnBarcode(lpnBarcode)
                .barcodeForPickingLocation(locationBarcode)
                .request();
        Scenario.inspectedOutbound().request();
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
    }

    @Test
    @DisplayName("운송장을 요청한다.")
    void requestWaybill() {
        final Long outboundNo = 1L;

        requestWaybill.request(outboundNo);

        assertThat(outboundRepository.getBy(outboundNo).getTrackingNumber()).isNotNull();
    }

}
