package com.example.kwms.location.feature.api;

import com.example.kwms.common.Scenario;
import com.example.kwms.location.feature.AppendLocation;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

public class AppendLocationApi {
    private String currentLocationBarcode = "TOTE-001";
    private String targetLocationBarcode = "PALLET-001";

    public AppendLocationApi currentLocationBarcode(final String currentLocationBarcode) {
        this.currentLocationBarcode = currentLocationBarcode;
        return this;
    }

    public AppendLocationApi targetLocationBarcode(final String targetLocationBarcode) {
        this.targetLocationBarcode = targetLocationBarcode;
        return this;
    }

    public Scenario request() {
        final AppendLocation.Request request = new AppendLocation.Request(
                currentLocationBarcode,
                targetLocationBarcode
        );

        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/locations/append")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());

        return new Scenario();

    }
}
