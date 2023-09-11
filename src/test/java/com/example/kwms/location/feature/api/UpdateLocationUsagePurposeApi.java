package com.example.kwms.location.feature.api;

import com.example.kwms.common.Scenario;
import com.example.kwms.location.feature.UpdateLocationUsagePurpose;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

public class UpdateLocationUsagePurposeApi {
    private String locationBarcode = "TOTE-001";
    private String usagePurpose = "FILL";

    public UpdateLocationUsagePurposeApi locationBarcode(final String locationBarcode) {
        this.locationBarcode = locationBarcode;
        return this;
    }

    public UpdateLocationUsagePurposeApi usagePurpose(final String usagePurpose) {
        this.usagePurpose = usagePurpose;
        return this;
    }

    public Scenario request() {
        final UpdateLocationUsagePurpose.Request request = new UpdateLocationUsagePurpose.Request(usagePurpose);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/locations/{locationBarcode}/usage-purpose", locationBarcode)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());

        return new Scenario();
    }
}
