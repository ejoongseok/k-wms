package com.example.kwms.outbound.feature.api;

import com.example.kwms.common.Scenario;
import com.example.kwms.outbound.feature.command.PackedOutbound;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

public class PackedOutboundApi {
    private Long outboundNo = 1L;
    private String packagingMaterialCode = "code";
    private Long weightInGrams = 100L;
    private Long boxWidthInMillimeters = 10L;
    private Long boxLengthInMillimeters = 10L;
    private Long boxHeightInMillimeters = 10L;

    public PackedOutboundApi outboundNo(final Long outboundNo) {
        this.outboundNo = outboundNo;
        return this;
    }

    public PackedOutboundApi packagingMaterialCode(final String packagingMaterialCode) {
        this.packagingMaterialCode = packagingMaterialCode;
        return this;
    }

    public PackedOutboundApi weightInGrams(final Long weightInGrams) {
        this.weightInGrams = weightInGrams;
        return this;
    }

    public PackedOutboundApi boxWidthInMillimeters(final Long boxWidthInMillimeters) {
        this.boxWidthInMillimeters = boxWidthInMillimeters;
        return this;
    }

    public PackedOutboundApi boxLengthInMillimeters(final Long boxLengthInMillimeters) {
        this.boxLengthInMillimeters = boxLengthInMillimeters;
        return this;
    }

    public PackedOutboundApi boxHeightInMillimeters(final Long boxHeightInMillimeters) {
        this.boxHeightInMillimeters = boxHeightInMillimeters;
        return this;
    }


    public Scenario request() {
        final PackedOutbound.Request request = new PackedOutbound.Request(
                packagingMaterialCode,
                weightInGrams,
                boxWidthInMillimeters,
                boxLengthInMillimeters,
                boxHeightInMillimeters
        );

        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/outbounds/{outboundNo}/packed", outboundNo)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());

        return new Scenario();
    }
}
