package com.example.kwms.outbound.feature.query;

import com.example.kwms.outbound.domain.Outbound;
import com.example.kwms.outbound.domain.OutboundFixture;
import com.example.kwms.outbound.domain.OutboundProductFixture;
import com.example.kwms.outbound.domain.SameOutbound;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

public class GetSameShipmentsTest {

    @Test
    void name() {
        final List<Outbound> list = Arrays.asList(
                OutboundFixture.anOutbound().build(),
                OutboundFixture.anOutbound().build(),
                OutboundFixture.anOutbound().build(),
                OutboundFixture.anOutbound().build(),
                OutboundFixture.anOutbound().build(),
                OutboundFixture.anOutbound().build(),
                OutboundFixture.anOutbound().build(),
                OutboundFixture.anOutbound().build(),
                OutboundFixture.anOutbound().build(),
                OutboundFixture.anOutbound().build(),
                OutboundFixture.anOutbound().build(),
                OutboundFixture.anOutbound().build(),
                OutboundFixture.anOutbound().outboundProducts(OutboundProductFixture.anOutboundProduct().productNo(2L)).build(),
                OutboundFixture.anOutbound().outboundProducts(OutboundProductFixture.anOutboundProduct().orderQuantity(2L)).build()
        );

        final Map<Integer, List<SameOutbound>> sameOutboundMap = list.stream()
                .map(o -> SameOutbound.from(o))
                .collect(groupingBy(SameOutbound::hashCode));

        System.out.println(sameOutboundMap);
    }
}
