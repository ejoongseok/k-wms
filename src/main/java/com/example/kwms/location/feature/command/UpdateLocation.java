package com.example.kwms.location.feature.command;

import com.example.kwms.location.domain.Location;
import com.example.kwms.location.domain.LocationRepository;
import com.example.kwms.location.domain.UsagePurpose;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UpdateLocation {
    private final LocationRepository locationRepository;

    @PatchMapping("/locations/{locationBarcode}")
    @Transactional
    public void request(
            @PathVariable final String locationBarcode,
            @RequestBody @Valid final Request request) {
        validate(request.locationBarcode);

        final Location location = locationRepository.getBy(locationBarcode);

        location.update(
                request.locationBarcode,
                request.usagePurpose
        );
    }

    private void validate(final String locationBarcode) {
        locationRepository.findByBarcode(locationBarcode)
                .ifPresent(location -> {
                    throw new IllegalArgumentException("이미 존재하는 로케이션 바코드입니다.");
                });
    }

    public record Request(
            @NotBlank(message = "로케이션 바코드는 필수입니다.")
            String locationBarcode,
            @NotNull(message = "로케이션 용도는 필수입니다.")
            UsagePurpose usagePurpose) {
    }
}
