package com.example.kwms.outbound.feature;

import com.example.kwms.common.ApiTest;
import com.example.kwms.common.Scenario;
import com.example.kwms.outbound.domain.BulkOutboundRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class InspectedBulkOutboundTest extends ApiTest {

    @Autowired
    private InspectedBulkOutbound inspectedBulkOutbound;

    @Autowired
    private BulkOutboundRepository bulkOutboundRepository;

    @BeforeEach
    void inspectedBulkOutboundSetUp() {
        final String locationBarcode = "TOTE-001";
        final String lpnBarcode = "LPN-001";
        final Long quantity = 10L;
        Scenario.createWarehouse().request();
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
        Scenario.createOutbound().request();
        Scenario.createBulkOutbound().request();
        Scenario.pickingBulkOutbound().request();
        Scenario.pickingBulkOutbound().request();
    }

    @Test
    @DisplayName("대량 출고 검수 완료")
    void inspectedBulkOutbound() {
        final Long bulkOutboundNo = 1L;

        Scenario.inspectedBulkOutbound().request();

        assertThat(bulkOutboundRepository.getBy(bulkOutboundNo).getInspectedAt()).isNotNull();
    }

}
