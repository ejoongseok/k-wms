package com.example.kwms.location.feature.api;

import com.example.kwms.common.Scenario;
import com.example.kwms.location.domain.UsagePurpose;
import com.example.kwms.location.feature.UpdateLocation;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

public class UpdateLocationApi {
    private String currentLocationBarcode = "TOTE-001";
    private String locationBarcode = "TOTE-002";
    private UsagePurpose usagePurpose = UsagePurpose.MOVE;

    public UpdateLocationApi currentLocationBarcode(final String currentLocationBarcode) {
        this.currentLocationBarcode = currentLocationBarcode;
        return this;
    }

    public UpdateLocationApi locationBarcode(final String locationBarcode) {
        this.locationBarcode = locationBarcode;
        return this;
    }

    public UpdateLocationApi usagePurpose(final UsagePurpose usagePurpose) {
        this.usagePurpose = usagePurpose;
        return this;
    }


    public Scenario request() {
        final UpdateLocation.Request request = new UpdateLocation.Request(
                locationBarcode,
                usagePurpose
        );
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().patch("/locations/{locationBarcode}", currentLocationBarcode)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
        return new Scenario();
    }
}
