package com.example.kwms.outbound.feature.api;

import com.example.kwms.common.Scenario;
import com.example.kwms.outbound.feature.ScanToPick;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

public class ScanToPickApi {
    private String pickingToteBarcode = "TOTE-002";
    private String lpnBarcode = "LPN-001";
    private String barcodeForPickingLocation = "TOTE-001";

    public ScanToPickApi pickingToteBarcode(final String pickingToteBarcode) {
        this.pickingToteBarcode = pickingToteBarcode;
        return this;
    }

    public ScanToPickApi lpnBarcode(final String lpnBarcode) {
        this.lpnBarcode = lpnBarcode;
        return this;
    }

    public ScanToPickApi barcodeForPickingLocation(final String barcodeForPickingLocation) {
        this.barcodeForPickingLocation = barcodeForPickingLocation;
        return this;
    }

    public Scenario request() {
        final ScanToPick.Request request = new ScanToPick.Request(
                pickingToteBarcode,
                lpnBarcode,
                barcodeForPickingLocation
        );

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/outbounds/scan-to-pick")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());

        return new Scenario();
    }
}
