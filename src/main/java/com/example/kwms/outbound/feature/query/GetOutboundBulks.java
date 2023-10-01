package com.example.kwms.outbound.feature.query;

import com.example.kwms.outbound.domain.BulkOutbound;
import com.example.kwms.outbound.domain.BulkOutboundRepository;
import com.example.kwms.outbound.domain.Outbound;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class GetOutboundBulks {
    private final BulkOutboundRepository bulkOutboundRepository;

    @GetMapping("/outbounds/bulks")
    @Transactional(readOnly = true)
    public List<Response> findAll() {
        final List<BulkOutbound> bulkOutbounds = bulkOutboundRepository.findAll();
        final List<Response> responses = new ArrayList<>();
        for (final BulkOutbound bulkOutbound : bulkOutbounds) {
            final Long bulkOutboundNo = bulkOutbound.getBulkOutboundNo();
            final Long outboundSize = (long) bulkOutbound.getOutbounds().size();
            final Outbound outbound = bulkOutbound.getOutbounds().get(0);
            final Long skuQuantity = (long) outbound.getOutboundProducts().size();
            final Long orderQuantity = outbound.getOutboundProducts().stream().mapToLong(outboundProduct -> outboundProduct.getQuantity()).sum();
            final String status;
            final LocalDateTime pickedAt = bulkOutbound.getPickedAt();
            final LocalDateTime inspectedAt = bulkOutbound.getInspectedAt();
            final LocalDateTime packedAt = bulkOutbound.getPackedAt();
            if (null != pickedAt) {
                status = "피킹 완료";
            } else {
                if (null != inspectedAt) {
                    status = "검수 완료";
                } else {
                    if (null != packedAt) {
                        status = "포장 완료";
                    } else {
                        status = "피킹 전";
                    }
                }
            }
            final Response response = new Response(bulkOutboundNo, outboundSize, skuQuantity, orderQuantity, status, pickedAt, inspectedAt, packedAt);
            responses.add(response);
        }
        return responses;
    }

    private record Response(
            Long bulkOutboundNo,
            Long outboundSize,
            Long skuQuantity,
            Long orderQuantity,
            String status,
            LocalDateTime pickedAt,
            LocalDateTime inspectedAt,
            LocalDateTime packedAt) {

    }
}
