package com.example.kwms.outbound.feature.api;

import com.example.kwms.common.Scenario;
import com.example.kwms.outbound.feature.command.BulkPackedOutbound;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

public class PackedBulkOutboundApi {
    private Long bulkOutboundNo = 1L;
    private String packagingMaterialCode = "code";
    private Long weightInGrams = 100L;
    private Long boxWidthInMillimeters = 10L;
    private Long boxLengthInMillimeters = 10L;
    private Long boxHeightInMillimeters = 10L;

    public PackedBulkOutboundApi bulkOutboundNo(final Long bulkOutboundNo) {
        this.bulkOutboundNo = bulkOutboundNo;
        return this;
    }

    public PackedBulkOutboundApi packagingMaterialCode(final String packagingMaterialCode) {
        this.packagingMaterialCode = packagingMaterialCode;
        return this;
    }

    public PackedBulkOutboundApi weightInGrams(final Long weightInGrams) {
        this.weightInGrams = weightInGrams;
        return this;
    }

    public PackedBulkOutboundApi boxWidthInMillimeters(final Long boxWidthInMillimeters) {
        this.boxWidthInMillimeters = boxWidthInMillimeters;
        return this;
    }

    public PackedBulkOutboundApi boxLengthInMillimeters(final Long boxLengthInMillimeters) {
        this.boxLengthInMillimeters = boxLengthInMillimeters;
        return this;
    }

    public PackedBulkOutboundApi boxHeightInMillimeters(final Long boxHeightInMillimeters) {
        this.boxHeightInMillimeters = boxHeightInMillimeters;
        return this;
    }

    public Scenario request() {
        final BulkPackedOutbound.Request request = new BulkPackedOutbound.Request(
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
                .when()
                .post("/bulk-outbounds/{bulkOutboundNo}/packed", bulkOutboundNo)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());

        return new Scenario();
    }
}
