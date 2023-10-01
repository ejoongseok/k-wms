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
class ManualOutbound {
    private final OutboundRepository outboundRepository;

    @PostMapping("/outbounds/{outboundNo}/manual")
    @Transactional
    public void request(@PathVariable final Long outboundNo) {
        final Outbound outbound = outboundRepository.getBy(outboundNo);

        outbound.manualOutbound();
    }
}