package com.example.kwms.location.feature.command;

import com.example.kwms.common.ApiTest;
import com.example.kwms.common.Scenario;
import com.example.kwms.location.domain.LocationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class CreateLocationTest extends ApiTest {

    @Autowired
    private LocationRepository locationRepository;


    @Test
    @DisplayName("로케이션을 생성한다.")
    void createLocation() {
        Scenario.createLocation().request();

        assertThat(locationRepository.findAll()).hasSize(1);
    }

}
