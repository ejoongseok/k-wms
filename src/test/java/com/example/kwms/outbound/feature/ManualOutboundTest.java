package com.example.kwms.outbound.feature;

import com.example.kwms.common.ApiTest;
import com.example.kwms.common.Scenario;
import com.example.kwms.outbound.domain.OutboundRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class ManualOutboundTest extends ApiTest {

    @Autowired
    private ManualOutbound manualOutbound;
    @Autowired
    private OutboundRepository outboundRepository;

    @BeforeEach
    void manualOutboundSetUp() {
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
    }

    @Test
    @DisplayName("수동 출고 처리")
    void manualOutbound() {
        final Long outboundNo = 1L;

        Scenario.manualOutbound().request();

        assertThat(outboundRepository.getBy(outboundNo).isManualOutbound()).isEqualTo(true);
    }

}
