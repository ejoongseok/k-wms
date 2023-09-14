package com.example.kwms.outbound.feature;

import com.example.kwms.common.ApiTest;
import com.example.kwms.common.Scenario;
import com.example.kwms.outbound.domain.BulkOutboundRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateBulkOutboundTest extends ApiTest {

    @Autowired
    private BulkOutboundRepository bulkOutboundRepository;

    @BeforeEach
    void createBulkOutboundSetUp() {
        final String locationBarcode = "TOTE-001";
        final String lpnBarcode = "LPN-001";
        final Long quantity = 10L;
        Scenario.createWarehouse().request();
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
        Scenario.createOutbound().request();
    }

    @Test
    @DisplayName("대량의 출고를 생성한다.")
    void createBulkOutbound() {
        Scenario.createBulkOutbound().request();

        assertThat(bulkOutboundRepository.findAll()).hasSize(1);
    }

}
