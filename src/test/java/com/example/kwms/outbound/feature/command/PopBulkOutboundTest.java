package com.example.kwms.outbound.feature.command;

import com.example.kwms.common.ApiTest;
import com.example.kwms.common.Scenario;
import com.example.kwms.outbound.domain.BulkOutboundRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

public class PopBulkOutboundTest extends ApiTest {

    @Autowired
    private BulkOutboundRepository bulkOutboundRepository;

    @BeforeEach
    void popBulkOutboundSetUp() {
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
        Scenario.createOutbound().orderNo(99L).request();
        Scenario.createBulkOutbound().request();
    }

    @Test
    @DisplayName("대량 출고에서 입력한 수량만큼 출고건을 제외한다.")
    @Transactional
    void popBulkOutbound() {
        final Long bulkOutboundNo = 1L;
        final Long quantity = 1L;

        Scenario.popBulkOutbound().bulkOutboundNo(bulkOutboundNo).quantity(quantity).request();

        assertThat(bulkOutboundRepository.getBy(bulkOutboundNo).getOutbounds()).hasSize(1);
    }

}
