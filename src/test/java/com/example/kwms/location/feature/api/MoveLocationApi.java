package com.example.kwms.location.feature.api;

import com.example.kwms.common.Scenario;
import com.example.kwms.location.feature.MoveLocation;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

public class MoveLocationApi {
    private String currentLocationBarcode = "TOTE-001";
    private String targetLocationBarcode = "PALLET-001";

    public MoveLocationApi currentLocationBarcode(final String currentLocationBarcode) {
        this.currentLocationBarcode = currentLocationBarcode;
        return this;
    }

    public MoveLocationApi targetLocationBarcode(final String targetLocationBarcode) {
        this.targetLocationBarcode = targetLocationBarcode;
        return this;
    }

    public Scenario request() {
        final MoveLocation.Request request = new MoveLocation.Request(
                currentLocationBarcode,
                targetLocationBarcode
        );

        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/locations/move")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());

        return new Scenario();

    }
}
