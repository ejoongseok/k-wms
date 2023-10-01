package com.example.kwms.location.feature.command;

import com.example.kwms.common.ApiTest;
import com.example.kwms.common.Scenario;
import com.example.kwms.location.domain.WarehouseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class UpdateWarehouseTest extends ApiTest {

    @Autowired
    private WarehouseRepository warehouseRepository;

    @BeforeEach
    void warehouseRepositorySetUp() {
        Scenario.createWarehouse().request();
    }

    @Test
    @DisplayName("창고를 수정한다.")
    void updateWarehouse() {
        Scenario.updateWarehouse().request();

        assertThat(warehouseRepository.findAll().get(0).getName()).isEqualTo("창고명 수정");
    }

}
