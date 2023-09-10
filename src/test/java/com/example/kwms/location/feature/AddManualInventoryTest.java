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

public class AddManualInventoryTest extends ApiTest {

    @Autowired
    private AddManualInventory addManualInventory;

    @Autowired
    private LocationRepository locationRepository;

    @BeforeEach
    void addManualInventorySetup() {
        Scenario.createLocation().locationBarcode("TOTE-001").request()
                .createInbound().request()
                .registerInboundProductInspectionResult().request()
                .createLPN().lpnBarcode("LPN-001").request();
    }

    @Test
    @DisplayName("수량 직접입력 재고 추가")
    @Transactional
    void addManualInventory() {
        final String locationBarcode = "TOTE-001";
        final String lpnBarcode = "LPN-001";
        final Long quantity = 10L;

        Scenario.addManualInventory()
                .locationBarcode(locationBarcode)
                .lpnBarcode(lpnBarcode)
                .quantity(quantity)
                .request();

        final Location location = locationRepository.getBy(locationBarcode);
        assertThat(location.getInventories()).hasSize(1);
        assertThat(location.getInventories().get(0).getQuantity()).isEqualTo(quantity);
    }

}
