package com.example.kwms.location.feature;

import com.example.kwms.common.NotFoundException;
import com.example.kwms.location.domain.Location;
import com.example.kwms.location.domain.LocationRepository;
import com.example.kwms.location.domain.MovingWarehouseLocation;
import com.example.kwms.location.domain.WarehouseRepository;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MoveWarehouseLocationTest {

    private MoveWarehouseLocation moveWarehouseLocation;

    @BeforeEach
    void setUp() {
        moveWarehouseLocation = new MoveWarehouseLocation();
    }

    @Test
    @DisplayName("창고간 로케이션 이동. A재고를 B로 이동")
    void moveWarehouseLocation() {
        final Long warehouseNo = 1L;
        final List<String> locationBarcodes = Arrays.asList("PALLET-1", "PALLET-2", "PALLET-3");
        final MoveWarehouseLocation.Request request = new MoveWarehouseLocation.Request(
                warehouseNo,
                locationBarcodes
        );

        moveWarehouseLocation.request(request);
    }

    private class MoveWarehouseLocation {
        private WarehouseRepository warehouseRepository;
        private LocationRepository locationRepository;

        public void request(final Request request) {
            validate(request.warehouseNo);
            final List<Location> locations = request.locationBarcodes.stream()
                    .map(locationRepository::getBy)
                    .collect(Collectors.toList());

            new MovingWarehouseLocation(request.warehouseNo, locations);

            // 로케이션의 상태를 전부 이동중으로 변경한다.
            locations.forEach(Location::warehouseMove);


        }

        private void validate(final Long warehouseNo) {
            warehouseRepository.findById(warehouseNo)
                    .orElseThrow(() -> new NotFoundException("창고가 존재하지 않습니다."));
        }

        public record Request(
                @NotNull(message = "창고 번호는 필수입니다.")
                Long warehouseNo,
                @NotNull(message = "로케이션 바코드는 필수입니다.")
                @NotEmpty(message = "로케이션 바코드는 필수입니다.")
                List<String> locationBarcodes) {
        }
    }
}
