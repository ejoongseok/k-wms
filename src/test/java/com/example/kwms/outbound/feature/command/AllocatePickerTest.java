package com.example.kwms.outbound.feature.command;

import com.example.kwms.common.ApiTest;
import com.example.kwms.common.Scenario;
import com.example.kwms.outbound.domain.OutboundRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class AllocatePickerTest extends ApiTest {

    @Autowired
    private OutboundRepository outboundRepository;

    @BeforeEach
    void allocatePickerSetUp() {
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
    @DisplayName("집품을 작업자에게 할당한다.")
    void allocatePicker() {

        Scenario.allocatePicker().request();

        assertThat(outboundRepository.getBy(1L).getPickerNo()).isNotNull();
    }

}
