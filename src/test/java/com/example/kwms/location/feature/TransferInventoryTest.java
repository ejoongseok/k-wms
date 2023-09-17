package com.example.kwms.location.feature;

import com.example.kwms.common.ApiTest;
import com.example.kwms.common.Scenario;
import com.example.kwms.location.domain.Location;
import com.example.kwms.location.domain.LocationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

public class TransferInventoryTest extends ApiTest {

    @Autowired
    private LocationRepository locationRepository;

    @BeforeEach
    void transferInventorySetUp() {
        final String locationBarcode = "TOTE-001";
        final String lpnBarcode = "LPN-001";
        final Long quantity = 10L;
        Scenario
                .createLocation().locationBarcode(locationBarcode).request()
                .createLocation().locationBarcode("TOTE-002").request()
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
    @DisplayName("재고 이동")
    @Transactional
    void transferInventory() {
        final String currentLocationBarcode = "TOTE-001";
        final String targetLocationBarcode = "TOTE-002";
        final String lpnBarcode = "LPN-001";
        final Long quantity = 1L;

        Scenario.transferInventory()
                .currentLocationBarcode(currentLocationBarcode)
                .targetLocationBarcode(targetLocationBarcode)
                .lpnBarcode(lpnBarcode)
                .quantity(quantity)
                .request();


        final Location location = locationRepository.getBy(currentLocationBarcode);
        final Location target = locationRepository.getBy(targetLocationBarcode);
        assertThat(location.getInventories()).hasSize(1);
        assertThat(location.getInventories().get(0).getQuantity()).isEqualTo(9L);
        assertThat(target.getInventories()).hasSize(1);
        assertThat(target.getInventories().get(0).getQuantity()).isEqualTo(1L);
    }

}
