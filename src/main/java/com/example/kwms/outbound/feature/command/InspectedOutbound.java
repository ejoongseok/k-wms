package com.example.kwms.outbound.feature.command;

import com.example.kwms.outbound.domain.OutboundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class InspectedOutbound {
    private final OutboundRepository outboundRepository;

    @PostMapping("/outbounds/{outboundNo}/inspected")
    @Transactional
    public void request(@PathVariable final Long outboundNo) {
        outboundRepository.getBy(outboundNo).inspected();
    }
}
