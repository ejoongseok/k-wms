package com.example.kwms.location.feature;

import com.example.kwms.location.domain.Location;
import com.example.kwms.location.domain.LocationRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UpdateLocationUsagePurpose {
    private final LocationRepository locationRepository;

    @Transactional
    @PostMapping("/locations/{locationBarcode}/usage-purpose")
    public void request(
            @PathVariable final String locationBarcode,
            @RequestBody @Valid final Request request) {

        final Location location = locationRepository.getBy(locationBarcode);

        location.updateUsagePurpose(request.usagePurpose);
    }

    public record Request(
            @NotBlank(message = "사용 용도는 필수입니다.")
            String usagePurpose) {
    }
}
