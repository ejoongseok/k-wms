package com.example.kwms.outbound.feature.command;

import com.example.kwms.common.ApiTest;
import com.example.kwms.common.Scenario;
import com.example.kwms.location.domain.UsagePurpose;
import com.example.kwms.outbound.domain.PickingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class AllocatePickingTest extends ApiTest {

    @Autowired
    private PickingRepository pickingRepository;

    @BeforeEach
    void allocatePickingSetup() {
        Scenario.createWarehouse().request();
        final String locationBarcode = "TOTE-001";
        final String lpnBarcode = "LPN-001";
        final Long quantity = 10L;
        Scenario.createLocation().locationBarcode(locationBarcode).usagePurpose(UsagePurpose.DISPLAY).request()
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
        Scenario.manualAllocatePicker().request();
    }

    @Test
    @DisplayName("출고 피킹을 할당한다.")
    void allocatePicking() {
        Scenario.allocatePicking().request();

        assertThat(pickingRepository.findAll()).hasSize(1);
    }

}
