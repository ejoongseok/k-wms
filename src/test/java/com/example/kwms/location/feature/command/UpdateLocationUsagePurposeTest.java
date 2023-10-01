package com.example.kwms.location.feature.command;

import com.example.kwms.common.ApiTest;
import com.example.kwms.common.Scenario;
import com.example.kwms.location.domain.LocationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UpdateLocationUsagePurposeTest extends ApiTest {

    @Autowired
    private LocationRepository locationRepository;

    @BeforeEach
    void updateLocationUsagePurposeSetUp() {
        Scenario.createLocation().request();
    }

    @Test
    @DisplayName("로케이션의 용도를 변경한다.")
    void updateLocationUsagePurpose() {
        final String usagePurpose = "FILL";

        Scenario
                .updateLocationUsagePurpose()
                .usagePurpose(usagePurpose)
                .request();

        locationRepository.getBy(1L).getUsagePurpose().equals(usagePurpose);
    }

}
