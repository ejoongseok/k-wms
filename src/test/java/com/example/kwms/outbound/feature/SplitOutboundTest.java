package com.example.kwms.outbound.feature;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class SplitOutboundTest {

    private SplitOutbound splitOutbound;

    @BeforeEach
    void setUp() {
        splitOutbound = new SplitOutbound();
    }

    @Test
    @DisplayName("출고를 분할한다.")
    void splitOutbound() {
        final Long outboundNo = 1L;
        final List<SplitOutbound.Request.Product> targets = new ArrayList<>();
        final Long productNo = 1L;
        final Long quantity = 1L;
        targets.add(new SplitOutbound.Request.Product(productNo, quantity));
        final SplitOutbound.Request request = new SplitOutbound.Request(
                targets
        );
        splitOutbound.request(outboundNo, request);
    }

}
