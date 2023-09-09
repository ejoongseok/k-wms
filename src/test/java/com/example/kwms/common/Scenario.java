package com.example.kwms.common;

import com.example.kwms.inbound.feature.api.CreateInboundApi;

public class Scenario {
    public static CreateInboundApi createInbound() {
        return new CreateInboundApi();
    }
}
