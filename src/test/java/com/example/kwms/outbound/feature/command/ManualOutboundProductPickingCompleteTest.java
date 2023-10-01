package com.example.kwms.outbound.feature.command;

import com.example.kwms.common.ApiTest;
import com.example.kwms.common.Scenario;
import com.example.kwms.location.domain.UsagePurpose;
import com.example.kwms.outbound.domain.OutboundRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class ManualOutboundProductPickingCompleteTest extends ApiTest {

    @Autowired
    private OutboundRepository outboundRepository;

    @BeforeEach
    void manualOutboundProductPickingCompleteSetup() {
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
    }


    @Test
    @DisplayName("수동 출고 상품 피킹 완료")
    void manualOutboundProductPickingComplete() {
        final Long outboundNo = 1L;

        Scenario.manualOutboundProductPickingComplete().request();

        assertThat(outboundRepository.getBy(outboundNo).isPicked()).isEqualTo(true);
    }

}
