package com.example.kwms.outbound.feature.command;

import com.example.kwms.outbound.domain.BulkOutbound;
import com.example.kwms.outbound.domain.BulkOutboundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class InspectedBulkOutbound {
    private final BulkOutboundRepository bulkOutboundRepository;

    @PostMapping("/bulk-outbounds/{bulkOutboundNo}/inspected")
    @Transactional
    public void request(@PathVariable final Long bulkOutboundNo) {
        final BulkOutbound bulkOutbound = bulkOutboundRepository.getBy(bulkOutboundNo);

        bulkOutbound.inspected();
    }
}
