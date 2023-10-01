package com.example.kwms.location.feature.command;

import com.example.kwms.inbound.domain.LPN;
import com.example.kwms.inbound.domain.LPNRepository;
import com.example.kwms.location.domain.Location;
import com.example.kwms.location.domain.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class AddInventory {
    private final LocationRepository locationRepository;
    private final LPNRepository lpnRepository;

    @PostMapping("/locations/{locationBarcode}/inventories/{lpnBarcode}")
    @Transactional
    public void request(
            @PathVariable final String locationBarcode,
            @PathVariable final String lpnBarcode) {
        final Location location = locationRepository.getBy(locationBarcode);
        final LPN lpn = lpnRepository.getBy(lpnBarcode);

        location.addInventory(lpn);
    }

}
