package com.example.kwms.location.feature;

import com.example.kwms.location.domain.Location;
import com.example.kwms.location.domain.LocationRepository;
import com.example.kwms.location.domain.StorageType;
import com.example.kwms.location.domain.UsagePurpose;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CreateLocation {
    private final LocationRepository locationRepository;

    @PostMapping("/locations")
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public void request(@RequestBody @Valid final Request request) {
        validate(request.locationBarcode);

        final Location location = request.toDomain();

        locationRepository.save(location);
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
            @NotNull(message = "로케이션 유형은 필수입니다.")
            StorageType storageType,
            @NotNull(message = "로케이션 용도는 필수입니다.")
            UsagePurpose usagePurpose) {
        public Location toDomain() {
            return new Location(
                    locationBarcode,
                    storageType,
                    usagePurpose
            );
        }
    }
}
