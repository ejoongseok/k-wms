package com.example.kwms.location.feature.command;

import com.example.kwms.common.ApiTest;
import com.example.kwms.common.Scenario;
import com.example.kwms.location.domain.Location;
import com.example.kwms.location.domain.LocationRepository;
import com.example.kwms.location.domain.UsagePurpose;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UpdateLocationTest extends ApiTest {

    @Autowired
    private LocationRepository locationRepository;


    @BeforeEach
    void updateLocationSetup() {
        Scenario.createLocation().locationBarcode("TOTE-001").request();
    }

    @Test
    @DisplayName("로케이션을 수정한다.")
    void updateLocation() {

        Scenario.updateLocation()
                .locationBarcode("TOTE-002")
                .usagePurpose(UsagePurpose.DISPLAY)
                .request();

        final List<Location> all = locationRepository.findAll();
        assertThat(all).hasSize(1);
        assertThat(all.get(0).getLocationBarcode()).isEqualTo("TOTE-002");
        assertThat(all.get(0).getUsagePurpose()).isEqualTo(UsagePurpose.DISPLAY);
    }

}
