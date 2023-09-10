package com.example.kwms.common;

import com.example.kwms.inbound.feature.api.AddInboundProductApi;
import com.example.kwms.inbound.feature.api.CreateInboundApi;
import com.example.kwms.inbound.feature.api.CreateLPNApi;
import com.example.kwms.inbound.feature.api.RegisterInboundProductInspectionResultApi;
import com.example.kwms.inbound.feature.api.UpdateInboundApi;
import com.example.kwms.inbound.feature.api.UpdateInboundProductApi;
import com.example.kwms.location.feature.api.AddInventoryApi;
import com.example.kwms.location.feature.api.AppendLocationApi;
import com.example.kwms.location.feature.api.CreateLocationApi;

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

    public static CreateLPNApi createLPN() {
        return new CreateLPNApi();
    }

    public static CreateLocationApi createLocation() {
        return new CreateLocationApi();
    }

    public static AppendLocationApi appendLocation() {
        return new AppendLocationApi();
    }

    public static AddInventoryApi addInventory() {
        return new AddInventoryApi();
    }
}
