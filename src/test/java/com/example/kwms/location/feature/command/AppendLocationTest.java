package com.example.kwms.location.feature.command;

import com.example.kwms.common.ApiTest;
import com.example.kwms.common.Scenario;
import com.example.kwms.location.domain.Location;
import com.example.kwms.location.domain.LocationRepository;
import com.example.kwms.location.domain.StorageType;
import com.example.kwms.location.domain.UsagePurpose;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

public class AppendLocationTest extends ApiTest {

    @Autowired
    private LocationRepository locationRepository;


    @BeforeEach
    void appendLocationSetUp() {
        Scenario.createLocation()
                .locationBarcode("TOTE-001")
                .storageType(StorageType.TOTE)
                .usagePurpose(UsagePurpose.DISPLAY)
                .request();
        Scenario.createLocation()
                .locationBarcode("PALLET-001")
                .storageType(StorageType.PALLET)
                .usagePurpose(UsagePurpose.MOVE)
                .request();
    }

    @Test
    @DisplayName("로케이션을 대상 로케이션의 하위로 이동시킨다.")
    @Transactional
    void appendLocation() {
        final String currentLocationBarcode = "TOTE-001";
        final String targetLocationBarcode = "PALLET-001";

        Scenario.appendLocation()
                .currentLocationBarcode(currentLocationBarcode)
                .targetLocationBarcode(targetLocationBarcode)
                .request();

        final Location pallet = locationRepository.getBy(targetLocationBarcode);
        assertThat(pallet.getChildren()).hasSize(1);
        assertThat(pallet.getChildren().get(0).getUsagePurpose()).isEqualTo(UsagePurpose.MOVE);
    }

}
