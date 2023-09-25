package com.example.kwms.outbound.feature;

import com.example.kwms.common.ApiTest;
import com.example.kwms.common.Scenario;
import com.example.kwms.location.domain.UsagePurpose;
import com.example.kwms.outbound.domain.Outbound;
import com.example.kwms.outbound.domain.OutboundRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class ResetOutboundTest extends ApiTest {

    @Autowired
    OutboundRepository outboundRepository;

    @BeforeEach
    void resetOutboundSetUp() {
        Scenario.createWarehouse().request();
        final String locationBarcode = "TOTE-001";
        final String lpnBarcode = "LPN-001";
        final Long quantity = 10L;
        Scenario.createLocation().locationBarcode(locationBarcode).usagePurpose(UsagePurpose.DISPLAY).request()
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
        Scenario.manualAllocatePicker().request();
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
        Scenario.cancelOutbound().request();
    }

    @Test
    @DisplayName("출고를 초기화한다.")
    void resetOutbound() {
        final Long outboundNo = 1L;

        Scenario.resetOutbound().request();

        final Outbound outbound = outboundRepository.getBy(outboundNo);
        assertThat(outbound.getPickingTote()).isNull();
        assertThat(outbound.getRealPackagingMaterial()).isNull();
        assertThat(outbound.getTrackingNumber()).isNull();
        assertThat(outbound.getPickedAt()).isNull();
        assertThat(outbound.isInspected()).isFalse();
        assertThat(outbound.isPacked()).isFalse();
        assertThat(outbound.isCanceled()).isFalse();
    }

}
