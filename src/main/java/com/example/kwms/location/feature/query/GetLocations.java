package com.example.kwms.location.feature.query;

import com.example.kwms.location.domain.Location;
import com.example.kwms.location.domain.LocationRepository;
import com.example.kwms.location.domain.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GetLocations {
    private final LocationRepository locationRepository;
    private final WarehouseRepository warehouseRepository;

    @GetMapping("/locations")
    public List<LocationResponse> findAll() {
        final List<Location> locations = locationRepository.findAll();
        return locations.stream()
                .map(l -> new LocationResponse(
                        warehouseRepository.getBy(l.getWarehouseNo()).getName(),
                        l.getLocationNo(),
                        l.getLocationBarcode(),
                        l.getStorageType().getDescription(),
                        l.getUsagePurpose().getDescription()))
                .toList();
    }

    record LocationResponse(
            String warehouseName,
            Long locationNo,
            String locationBarcode,
            String storageType,
            String usagePurpose) {

    }
}
