package com.example.kwms.outbound.feature;

import com.example.kwms.common.ApiTest;
import com.example.kwms.common.Scenario;
import com.example.kwms.outbound.domain.OutboundRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class CancelOutboundTest extends ApiTest {

    @Autowired
    private CancelOutbound cancelOutbound;

    @Autowired
    private OutboundRepository outboundRepository;

    @BeforeEach
    void cancelOutboundSetup() {
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
    }

    @Test
    @DisplayName("출고를 취소한다.")
    void cancelOutbound() {
        final Long outboundNo = 1L;

        Scenario.cancelOutbound().outboundNo(outboundNo).request();

        assertThat(outboundRepository.getBy(outboundNo).isCanceled()).isTrue();
    }

}
