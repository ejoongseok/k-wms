package com.example.kwms.outbound.feature;

import lombok.Getter;

@Getter
public class WaybillResponse {
    private final String trackingNumber;
    private final String waybillImage;

    public WaybillResponse(final String trackingNumber, final String waybillImage) {
        this.trackingNumber = trackingNumber;
        this.waybillImage = waybillImage;
    }
}
