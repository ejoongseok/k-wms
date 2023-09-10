package com.example.kwms.location.feature;

import com.example.kwms.inbound.domain.LPN;
import com.example.kwms.inbound.domain.LPNRepository;
import com.example.kwms.location.domain.Location;
import com.example.kwms.location.domain.LocationRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AddManualInventory {
    private final LocationRepository locationRepository;
    private final LPNRepository lpnRepository;

    @PostMapping("/locations/{locationBarcode}/manual-inventories/{lpnBarcode}")
    @Transactional
    public void request(
            @PathVariable final String locationBarcode,
            @PathVariable final String lpnBarcode,
            @RequestBody @Valid final Request request) {
        final Location location = locationRepository.getBy(locationBarcode);
        final LPN lpn = lpnRepository.getBy(lpnBarcode);

        location.addManualInventory(lpn, request.quantity());
    }

    public record Request(
            @NotNull(message = "수량은 필수입니다.")
            @Min(value = 1, message = "수량은 1개 이상이어야 합니다.")
            Long quantity) {
    }
}
