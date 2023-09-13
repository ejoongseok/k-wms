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

public class AllocatePickingToteTest extends ApiTest {

    @Autowired
    OutboundRepository outboundRepository;

    @Autowired
    private AllocatePickingTote allocatePickingTote;

    @BeforeEach
    void allocatePickingToteSetUp() {
        final String locationBarcode = "TOTE-001";
        final String pickingTote = "TOTE-002";
        final String lpnBarcode = "LPN-001";
        final Long quantity = 10L;
        Scenario.createLocation().locationBarcode(pickingTote).request();
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
    @DisplayName("피킹 토트를 할당한다.")
    @Transactional
    void allocatePickingTote() {
        final Long outboundNo = 1L;
        final String toteBarcode = "TOTE-002";

        Scenario.allocatePickingTote()
                .outboundNo(outboundNo)
                .toteBarcode(toteBarcode).request();

        assertThat(outboundRepository.getBy(outboundNo).getPickingTote()).isNotNull();
        assertThat(outboundRepository.getBy(outboundNo).getPickingTote().getLocationBarcode()).isEqualTo(toteBarcode);
    }

}
