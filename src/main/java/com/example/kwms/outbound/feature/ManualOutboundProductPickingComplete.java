package com.example.kwms.outbound.feature;

import com.example.kwms.outbound.domain.Outbound;
import com.example.kwms.outbound.domain.OutboundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class ManualOutboundProductPickingComplete {
    private final OutboundRepository outboundRepository;

    @PostMapping("/outbounds/{outboundNo}/outbound-products/{outboundProductNo}/complete-picking")
    @Transactional
    public void request(
            @PathVariable final Long outboundNo,
            @PathVariable final Long outboundProductNo) {
        final Outbound outbound = outboundRepository.getBy(outboundNo);

        outbound.manualCompletePicking(outboundProductNo);
    }

}
