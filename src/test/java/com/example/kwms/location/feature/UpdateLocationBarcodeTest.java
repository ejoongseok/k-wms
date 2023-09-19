package com.example.kwms.location.feature;

import com.example.kwms.common.ApiTest;
import com.example.kwms.common.Scenario;
import com.example.kwms.location.domain.LocationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UpdateLocationBarcodeTest extends ApiTest {

    @Autowired
    private LocationRepository locationRepository;

    @BeforeEach
    void updateLocationBarcodeSetUp() {
        Scenario.createLocation().request();
    }

    @Test
    @DisplayName("로케이션의 바코드를 변경한다.")
    void updateLocationBarcode() {
        final String locationBarcode = "TOTE-002";

        Scenario
                .updateLocationBarcode()
                .locationBarcode(locationBarcode)
                .request();

        locationRepository.getBy(1L).getLocationBarcode().equals(locationBarcode);
    }

}
