package com.example.kwms.outbound.feature.api;

import com.example.kwms.common.Scenario;
import com.example.kwms.outbound.feature.command.ScanToPickManual;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

public class ScanToPickManualApi {
    private String pickingToteBarcode = "TOTE-002";
    private String lpnBarcode = "LPN-001";
    private String barcodeForPickingLocation = "TOTE-001";
    private Long quantity = 1L;

    public ScanToPickManualApi pickingToteBarcode(final String pickingToteBarcode) {
        this.pickingToteBarcode = pickingToteBarcode;
        return this;
    }

    public ScanToPickManualApi lpnBarcode(final String lpnBarcode) {
        this.lpnBarcode = lpnBarcode;
        return this;
    }

    public ScanToPickManualApi barcodeForPickingLocation(final String barcodeForPickingLocation) {
        this.barcodeForPickingLocation = barcodeForPickingLocation;
        return this;
    }

    public ScanToPickManualApi quantity(final Long quantity) {
        this.quantity = quantity;
        return this;
    }

    public Scenario request() {
        final ScanToPickManual.Request request = new ScanToPickManual.Request(
                pickingToteBarcode,
                lpnBarcode,
                barcodeForPickingLocation,
                quantity
        );

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/outbounds/scan-to-pick-manual")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());

        return new Scenario();
    }
}
