package com.example.kwms.outbound.feature;

import com.example.kwms.location.domain.Location;
import com.example.kwms.location.domain.LocationRepository;
import com.example.kwms.outbound.domain.Outbound;
import com.example.kwms.outbound.domain.OutboundRepository;
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
public class AllocatePickingTote {
    private final OutboundRepository outboundRepository;
    private final LocationRepository locationRepository;

    @PostMapping("/outbounds/{outboundNo}/allocate-picking-tote")
    @Transactional
    public void request(
            @PathVariable final Long outboundNo,
            @RequestBody @Valid final Request request) {
        final Outbound outbound = outboundRepository.getBy(outboundNo);
        final Location tote = locationRepository.getBy(request.toteBarcode);

        outbound.allocatePickingTote(tote);
    }

    public record Request(
            @NotBlank(message = "토트 바코드는 필수입니다.")
            String toteBarcode) {
    }
}
