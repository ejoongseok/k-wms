package com.example.kwms.outbound.feature.api;

import com.example.kwms.common.Scenario;
import com.example.kwms.outbound.feature.command.AllocatePickingTote;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

public class AllocatePickingToteApi {
    private Long outboundNo = 1L;
    private String toteBarcode = "TOTE-002";

    public AllocatePickingToteApi outboundNo(final Long outboundNo) {
        this.outboundNo = outboundNo;
        return this;
    }

    public AllocatePickingToteApi toteBarcode(final String toteBarcode) {
        this.toteBarcode = toteBarcode;
        return this;
    }

    public Scenario request() {
        final AllocatePickingTote.Request request = new AllocatePickingTote.Request(
                toteBarcode
        );

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/outbounds/{outboundNo}/allocate-picking-tote", outboundNo)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());

        return new Scenario();
    }
}
