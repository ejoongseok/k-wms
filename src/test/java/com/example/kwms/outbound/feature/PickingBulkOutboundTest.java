package com.example.kwms.outbound.feature;

import com.example.kwms.common.ApiTest;
import com.example.kwms.common.Scenario;
import com.example.kwms.outbound.domain.BulkOutbound;
import com.example.kwms.outbound.domain.BulkOutboundRepository;
import com.example.kwms.outbound.domain.Outbound;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PickingBulkOutboundTest extends ApiTest {

    @Autowired
    BulkOutboundRepository bulkOutboundRepository;

    @BeforeEach
    void pickingBulkOutboundSetup() {
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
    }

    @Test
    @DisplayName("대량출고건 중 하나를 집품 완료한다.")
    @Transactional
    void pickingBulkOutbound() {
        final Long bulkOutboundNo = 1L;
        final String locationBarcode = "TOTE-001";
        final String lpnBarcode = "LPN-001";
        final Long quantity = 1L;
        final PickingBulkOutbound.Request.Picked picked = new PickingBulkOutbound.Request.Picked(
                locationBarcode,
                lpnBarcode,
                quantity
        );
        final PickingBulkOutbound.Request request = new PickingBulkOutbound.Request(
                List.of(picked)
        );

        Scenario.pickingBulkOutbound().request();


        final BulkOutbound bulkOutbound = bulkOutboundRepository.getBy(bulkOutboundNo);
        assertThat(bulkOutbound.getOutbounds().stream().anyMatch(Outbound::isPicked)).isEqualTo(true);
        assertThat(bulkOutbound.getPickedAt()).isNull();
    }

}
