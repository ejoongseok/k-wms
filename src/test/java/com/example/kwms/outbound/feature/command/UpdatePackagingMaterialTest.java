package com.example.kwms.outbound.feature.command;

import com.example.kwms.common.ApiTest;
import com.example.kwms.common.Scenario;
import com.example.kwms.outbound.domain.PackagingMaterialRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class UpdatePackagingMaterialTest extends ApiTest {

    @Autowired
    private PackagingMaterialRepository packagingMaterialRepository;

    @BeforeEach
    void updatePackagingMaterialSetUp() {
        Scenario.
                createPackagingMaterial().request();
    }

    @Test
    @DisplayName("포장재를 수정한다.")
    void updatePackagingMaterial() {
        Scenario.updatePackagingMaterial().request();

        assertThat(packagingMaterialRepository.findAll().get(0).getName()).isEqualTo("update name");
    }
}
