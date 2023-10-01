package com.example.kwms.outbound.feature.command;

import com.example.kwms.common.ApiTest;
import com.example.kwms.common.Scenario;
import com.example.kwms.outbound.domain.PackagingMaterialRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class CreatePackagingMaterialTest extends ApiTest {

    @Autowired
    private PackagingMaterialRepository packagingMaterialRepository;

    @Test
    @DisplayName("포장재를 생성한다.")
    void createPackagingMaterial() {
        Scenario.
                createPackagingMaterial().request();

        assertThat(packagingMaterialRepository.findAll()).hasSize(1);
    }
}
