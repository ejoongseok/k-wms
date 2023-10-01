package com.example.kwms.outbound.feature.query;

import com.example.kwms.outbound.domain.Outbound;
import com.example.kwms.outbound.domain.OutboundRepository;
import com.example.kwms.outbound.domain.SameOutbound;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

@RestController
@RequiredArgsConstructor
public class GetSameShipments {

    private final OutboundRepository outboundRepository;

    @GetMapping("/outbounds/same-shipments")
    @Transactional(readOnly = true)
    public List<Response> findAll() {
        final List<Outbound> outbounds = outboundRepository.findAll().stream()
                .filter(Outbound::isReady)
                .filter(o -> null == o.getPickerNo())
                .filter(o -> !o.isManualOutbound())
                .filter(o -> null == o.getBulkOutbound())
                .toList();

        final Map<Integer, List<SameOutbound>> sameOutboundMap = outbounds.stream()
                .map(o -> SameOutbound.from(o))
                .collect(groupingBy(SameOutbound::hashCode));
        Long index = 1L;
        final List<Response> responses = new ArrayList<>();
        for (final Map.Entry<Integer, List<SameOutbound>> integerListEntry : sameOutboundMap.entrySet()) {

            final List<SameOutbound> sameOutbounds = integerListEntry.getValue();
            final Long shippedQuantity = (long) sameOutbounds.size();
            if (1 == shippedQuantity) {
                continue;
            }
            final Long bulkOutboundNo = index;
            final List<Long> outboundNos = sameOutbounds.stream()
                    .map(SameOutbound::getOutboundNo)
                    .toList();
            final Long productQuantity = (long) sameOutbounds.get(0).getSameOutboundProducts().size();
            final Long orderQuantity = sameOutbounds.get(0).getSameOutboundProducts().stream()
                    .mapToLong(SameOutbound.SameOutboundProduct::getQuantity)
                    .sum();
            final Response response = new Response(bulkOutboundNo, outboundNos, shippedQuantity, productQuantity, orderQuantity);
            responses.add(response);
            index++;
        }

        return responses;
    }

    private record Response(
            Long bulkOutboundNo,
            List<Long> outboundNos,
            Long shippedQuantity,
            Long productQuantity,
            Long orderQuantity) {

    }
}
