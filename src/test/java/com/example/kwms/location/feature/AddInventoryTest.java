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

public class AddInventoryTest extends ApiTest {

    @Autowired
    private AddInventory addInventory;
    @Autowired
    private LocationRepository locationRepository;

    @BeforeEach
    void addInventorySetUp() {
        Scenario.createLocation().locationBarcode("TOTE-001").request()
                .createInbound().request()
                .registerInboundProductInspectionResult().request()
                .createLPN().lpnBarcode("LPN-001").request();
    }

    @Test
    @DisplayName("재고 추가")
    @Transactional
    void addInventory() {
        final String locationBarcode = "TOTE-001";
        final String lpnBarcode = "LPN-001";

        Scenario.
                addInventory().locationBarcode(locationBarcode).lpnBarcode(lpnBarcode).request();

        final Location location = locationRepository.getBy(locationBarcode);
        assertThat(location.getInventories()).hasSize(1);
    }

}
