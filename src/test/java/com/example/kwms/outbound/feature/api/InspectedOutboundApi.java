package com.example.kwms.outbound.feature.api;

import com.example.kwms.common.Scenario;
import io.restassured.RestAssured;
import org.springframework.http.HttpStatus;

public class InspectedOutboundApi {
    private Long outboundNo = 1L;

    public InspectedOutboundApi outboundNo(final Long outboundNo) {
        this.outboundNo = outboundNo;
        return this;
    }

    public Scenario request() {
        RestAssured
                .given().log().all()
                .when()
                .post("/outbounds/{outboundNo}/inspected", outboundNo)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
        return new Scenario();
    }
}
