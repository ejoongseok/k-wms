package com.example.kwms.location.feature.api;

import com.example.kwms.common.Scenario;
import com.example.kwms.location.feature.command.UpdateLocationBarcode;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

public class UpdateLocationBarcodeApi {
    private Long locationNo = 1L;
    private String locationBarcode = "TOTE-001";

    public UpdateLocationBarcodeApi locationNo(final Long locationNo) {
        this.locationNo = locationNo;
        return this;
    }

    public UpdateLocationBarcodeApi locationBarcode(final String locationBarcode) {
        this.locationBarcode = locationBarcode;
        return this;
    }

    public Scenario request() {
        final UpdateLocationBarcode.Request request = new UpdateLocationBarcode.Request(locationBarcode);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .patch("/locations/{locationNo}/barcode", locationNo)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());

        return new Scenario();
    }
}
