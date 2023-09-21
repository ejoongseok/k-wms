package com.example.kwms.outbound.feature.api;

import com.example.kwms.common.Scenario;
import io.restassured.RestAssured;
import org.springframework.http.HttpStatus;

public class ManualOutboundApi {
    private Long outboundNo = 1L;

    public ManualOutboundApi outboundNo(final Long outboundNo) {
        this.outboundNo = outboundNo;
        return this;
    }

    public Scenario request() {

        RestAssured
                .given().log().all()
                .when()
                .post("/outbounds/{outboundNo}/manual", outboundNo)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());

        return new Scenario();
    }
}
