package com.example.kwms.location.feature;

import com.example.kwms.common.ApiTest;
import com.example.kwms.common.Scenario;
import com.example.kwms.location.domain.Inventory;
import com.example.kwms.location.domain.LocationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AdjustInventoryTest extends ApiTest {

    @Autowired
    private LocationRepository locationRepository;

    @BeforeEach
    void AdjustInventorySetUp() {
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
    }

    @Test
    @DisplayName("재고 조정")
    @Transactional
    void adjustInventory() {
        final String locationBarcode = "TOTE-001";
        final String lpnBarcode = "LPN-001";
        final Long quantity = 1L;
        final String reason = "재고가 맞지 않음.";

        Scenario
                .adjustInventory()
                .locationBarcode(locationBarcode)
                .lpnBarcode(lpnBarcode)
                .quantity(quantity)
                .reason(reason)
                .request();

        final List<Inventory> inventories = locationRepository.getBy(locationBarcode).getInventories();
        assertThat(inventories).hasSize(1);
        assertThat(inventories.get(0).getQuantity()).isEqualTo(1L);
    }

}
