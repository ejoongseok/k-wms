package com.example.kwms.location.feature;

import com.example.kwms.common.ApiTest;
import com.example.kwms.common.Scenario;
import com.example.kwms.location.domain.LocationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UpdateLocationUsagePurposeTest extends ApiTest {

    @Autowired
    private UpdateLocationUsagePurpose updateLocationUsagePurpose;
    @Autowired
    private LocationRepository locationRepository;

    @BeforeEach
    void updateLocationUsagePurposeSetUp() {
        Scenario.createLocation().request();
    }

    @Test
    @DisplayName("로케이션의 용도를 변경한다.")
    void updateLocationUsagePurpose() {
        final String locationBarcode = "TOTE-001";
        final String usagePurpose = "FILL";

        Scenario
                .updateLocationUsagePurpose()
                .locationBarcode(locationBarcode)
                .usagePurpose(usagePurpose)
                .request();

        locationRepository.getBy(locationBarcode).getUsagePurpose().equals(usagePurpose);
    }

}
