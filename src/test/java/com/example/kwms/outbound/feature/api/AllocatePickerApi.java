package com.example.kwms.outbound.feature.api;

import com.example.kwms.common.Scenario;
import io.restassured.RestAssured;
import org.springframework.http.HttpStatus;

public class AllocatePickerApi {
    public Scenario request() {
        RestAssured.given().log().all()
                .when()
                .post("/outbounds/allocate-picker")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
        return new Scenario();
    }
}
