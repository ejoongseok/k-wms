package com.example.kwms.common;

import com.example.kwms.inbound.feature.api.AddInboundProductApi;
import com.example.kwms.inbound.feature.api.CreateInboundApi;
import com.example.kwms.inbound.feature.api.RegisterInboundProductInspectionResultApi;
import com.example.kwms.inbound.feature.api.UpdateInboundApi;
import com.example.kwms.inbound.feature.api.UpdateInboundProductApi;

public class Scenario {
    public static CreateInboundApi createInbound() {
        return new CreateInboundApi();
    }

    public static AddInboundProductApi addInboundProduct() {
        return new AddInboundProductApi();
    }

    public static UpdateInboundApi updateInbound() {
        return new UpdateInboundApi();
    }

    public static UpdateInboundProductApi updateInboundProduct() {
        return new UpdateInboundProductApi();
    }

    public static RegisterInboundProductInspectionResultApi registerInboundProductInspectionResult() {
        return new RegisterInboundProductInspectionResultApi();
    }
}
