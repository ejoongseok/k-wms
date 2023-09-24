package com.example.kwms.outbound.feature.api;

import com.example.kwms.common.Scenario;
import io.restassured.RestAssured;
import org.springframework.http.HttpStatus;

public class ManualOutboundProductPickingCompleteApi {
    private Long outboundNo = 1L;
    private Long outboundProductNo = 1L;

    public ManualOutboundProductPickingCompleteApi outboundNo(final Long outboundNo) {
        this.outboundNo = outboundNo;
        return this;
    }

    public ManualOutboundProductPickingCompleteApi outboundProductNo(final Long outboundProductNo) {
        this.outboundProductNo = outboundProductNo;
        return this;
    }

    public Scenario request() {


        RestAssured
                .given().log().all()
                .when()
                .post("/outbounds/{outboundNo}/outbound-products/{outboundProductNo}/complete-picking", outboundNo, outboundProductNo)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());

        return new Scenario();
    }
}
