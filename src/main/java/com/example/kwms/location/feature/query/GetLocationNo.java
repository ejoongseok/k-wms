package com.example.kwms.location.feature.query;

import com.example.kwms.location.domain.Location;
import com.example.kwms.location.domain.LocationRepository;
import com.example.kwms.location.domain.WarehouseRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class GetLocationNo {
    private final LocationRepository locationRepository;
    private final WarehouseRepository warehouseRepository;

    @GetMapping("/locations/{locationNo}")
    @Transactional(readOnly = true)
    public LocationResponse getLocation(@PathVariable final Long locationNo) {
        final Location location = locationRepository.getBy(locationNo);
        final LocationResponse locationResponse = new LocationResponse(
                warehouseRepository.getBy(location.getWarehouseNo()).getName(),
                null == location.getParent() ? "" : location.getParent().getLocationBarcode(),
                location.getLocationNo(),
                location.getLocationBarcode(),
                location.getStorageType().name(),
                location.getUsagePurpose().name(),
                location.getStorageType().getDescription(),
                location.getUsagePurpose().getDescription(),
                new ArrayList<>(),
                null == location.getParent() ? null : location.getParent().getLocationNo()
        );
        if (!location.getChildren().isEmpty()) {
            locationResponse.children.addAll(recursive(location.getChildren()));
        }
        return locationResponse;
    }

    private Collection<LocationResponse> recursive(final List<Location> children) {
        final List<LocationResponse> locationResponses = new ArrayList<>();
        for (final Location child : children) {
            final LocationResponse locationResponse = new LocationResponse(
                    null,
                    null == child.getParent() ? "" : child.getParent().getLocationBarcode(),
                    child.getLocationNo(),
                    child.getLocationBarcode(),
                    child.getStorageType().name(),
                    child.getUsagePurpose().name(),
                    child.getStorageType().getDescription(),
                    child.getUsagePurpose().getDescription(),
                    new ArrayList<>(),
                    null == child.getParent() ? null : child.getParent().getLocationNo()
            );
            if (!child.getChildren().isEmpty()) {
                locationResponse.children.addAll(recursive(child.getChildren()));
            }
            locationResponses.add(locationResponse);
        }
        return locationResponses;
    }

    @Data
    public static class LocationResponse {
        private final String warehouseName;
        private final String parentBarcode;
        private final Long locationNo;
        private final String locationBarcode;
        private final String storageType;
        private final String usagePurpose;
        private final String storageTypeDescription;
        private final String usagePurposeDescription;
        private final List<LocationResponse> children = new ArrayList<>();
        private final Long parentLocationNo;

        LocationResponse(
                final String warehouseName,
                final String parentBarcode,
                final Long locationNo,
                final String locationBarcode,
                final String storageType,
                final String usagePurpose,
                final String storageTypeDescription,
                final String usagePurposeDescription,
                final List<LocationResponse> children,
                final Long parentLocationNo) {
            this.warehouseName = warehouseName;
            this.parentBarcode = parentBarcode;
            this.locationNo = locationNo;
            this.locationBarcode = locationBarcode;
            this.storageType = storageType;
            this.usagePurpose = usagePurpose;
            this.storageTypeDescription = storageTypeDescription;
            this.usagePurposeDescription = usagePurposeDescription;
            this.children.addAll(children);
            this.parentLocationNo = parentLocationNo;
        }


    }
}
