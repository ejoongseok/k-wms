package com.example.kwms.location.feature;

import com.example.kwms.common.ApiTest;
import com.example.kwms.common.Scenario;
import com.example.kwms.location.domain.WarehouseRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateWarehouseTest extends ApiTest {

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Test
    @DisplayName("창고를 생성한다.")
    void createWarehouse() {
        Scenario.createWarehouse().request();

        assertThat(warehouseRepository.findAll()).hasSize(1);
    }

}
