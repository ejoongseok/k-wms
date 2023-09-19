package com.example.kwms.location.feature.query;

import com.example.kwms.location.domain.Location;
import com.example.kwms.location.domain.LocationRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetLocationBarcode {
    private final LocationRepository locationRepository;

    @GetMapping("/locations/barcode/{locationBarcode}")
    @Transactional(readOnly = true)
    public LocationResponse getLocationBarcode(@PathVariable final String locationBarcode) {
        final Location location = locationRepository.getBy(locationBarcode);
        return new LocationResponse(
                location.getLocationNo(),
                location.getLocationBarcode(),
                location.getStorageType().getDescription(),
                location.getUsagePurpose().getDescription()
        );
    }

    @Data
    public static class LocationResponse {
        private final Long locationNo;
        private final String locationBarcode;
        private final String storageTypeDescription;
        private final String usagePurposeDescription;

        LocationResponse(
                final Long locationNo,
                final String locationBarcode,
                final String storageTypeDescription,
                final String usagePurposeDescription) {
            this.locationNo = locationNo;
            this.locationBarcode = locationBarcode;
            this.storageTypeDescription = storageTypeDescription;
            this.usagePurposeDescription = usagePurposeDescription;
        }


    }
}
