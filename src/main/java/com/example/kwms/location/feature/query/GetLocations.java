package com.example.kwms.location.feature.query;

import com.example.kwms.location.domain.Location;
import com.example.kwms.location.domain.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GetLocations {
    private final LocationRepository locationRepository;

    @GetMapping("/locations")
    public List<LocationResponse> findAll() {
        final List<Location> locations = locationRepository.findAll();
        return locations.stream()
                .map(l -> new LocationResponse(
                        l.getLocationNo(),
                        l.getLocationBarcode(),
                        l.getStorageType().getDescription(),
                        l.getUsagePurpose().getDescription()))
                .toList();
    }

    record LocationResponse(
            Long locationNo,
            String locationBarcode,
            String storageType,
            String usagePurpose) {

    }
}
