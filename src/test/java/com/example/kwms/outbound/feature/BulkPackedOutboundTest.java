package com.example.kwms.outbound.feature;

import com.example.kwms.common.ApiTest;
import com.example.kwms.common.Scenario;
import com.example.kwms.outbound.domain.BulkOutboundRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class BulkPackedOutboundTest extends ApiTest {

    @Autowired
    BulkOutboundRepository bulkOutboundRepository;
    @Autowired
    private BulkPackedOutbound bulkPackedOutbound;

    @BeforeEach
    void bulkPackedOutboundSetUp() {
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
        Scenario.createBulkOutbound().request();
        Scenario.pickingBulkOutbound().request();
        Scenario.pickingBulkOutbound().request();
        Scenario.inspectedBulkOutbound().request();
    }

    @Test
    @DisplayName("대량출고 포장 처리")
    void bulkPackedOutbound() {
        final Long bulkOutboundNo = 1L;
        final String packagingMaterialCode = "code";
        final Long weightInGrams = 100L;
        final Long boxWidthInMillimeters = 10L;
        final Long boxLengthInMillimeters = 10L;
        final Long boxHeightInMillimeters = 10L;
        final BulkPackedOutbound.Request request = new BulkPackedOutbound.Request(
                packagingMaterialCode,
                weightInGrams,
                boxWidthInMillimeters,
                boxLengthInMillimeters,
                boxHeightInMillimeters
        );

        Scenario.packedBulkOutbound().request();

//        bulkPackedOutbound.request(bulkOutboundNo, request);

        assertThat(bulkOutboundRepository.getBy(bulkOutboundNo).getPackedAt()).isNotNull();
    }

}
