package com.example.kwms.outbound.feature;

import com.example.kwms.common.ApiTest;
import com.example.kwms.common.Scenario;
import com.example.kwms.outbound.domain.OutboundRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateOutboundTest extends ApiTest {

    @Autowired
    private CreateOutbound createOutbound;

    @Autowired
    private OutboundRepository outboundRepository;

    @BeforeEach
    void createOutboundSetUp() {
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
    }

    @Test
    @DisplayName("출고를 생성한다.")
    void createOutbound() {
        final Long orderNo = 1L;
        final Long warehouseNo = 1L;
        final boolean isPriorityDelivery = false;
        final LocalDate desiredDeliveryAt = LocalDate.now();
        final CreateOutbound.Request request = new CreateOutbound.Request(
                warehouseNo,
                orderNo,
                isPriorityDelivery,
                desiredDeliveryAt
        );

        createOutbound.request(request);

        assertThat(outboundRepository.findAll()).hasSize(1);
    }

}
