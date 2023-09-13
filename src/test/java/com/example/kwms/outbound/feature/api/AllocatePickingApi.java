package com.example.kwms.outbound.feature.api;

import com.example.kwms.common.Scenario;
import io.restassured.RestAssured;
import org.springframework.http.HttpStatus;

public class AllocatePickingApi {
    private Long outboundNo = 1L;

    public AllocatePickingApi outboundNo(final Long outboundNo) {
        this.outboundNo = outboundNo;
        return this;
    }

    public Scenario request() {
        RestAssured
                .given().log().all()
                .when()
                .post("/outbounds/{outboundNo}/pickings", outboundNo)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());

        return new Scenario();
    }
}
