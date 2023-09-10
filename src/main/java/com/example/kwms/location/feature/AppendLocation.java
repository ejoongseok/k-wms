package com.example.kwms.location.feature;

import com.example.kwms.location.domain.Location;
import com.example.kwms.location.domain.LocationRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AppendLocation {

    private final LocationRepository locationRepository;

    @PostMapping("/locations/append")
    @Transactional
    public void request(@RequestBody @Valid final Request request) {
        final Location current = locationRepository.getBy(request.currentLocationBarcode);
        final Location target = locationRepository.getBy(request.targetLocationBarcode);

        target.appendLocation(current);
    }

    public record Request(
            @NotBlank(message = "현재 로케이션 바코드는 필수입니다.")
            String currentLocationBarcode,
            @NotBlank(message = "대상 로케이션 바코드는 필수입니다.")
            String targetLocationBarcode) {
    }
}
