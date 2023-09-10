package com.example.kwms.location.feature.api;

import com.example.kwms.common.Scenario;
import com.example.kwms.location.feature.TransferInventory;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

public class TransferInventoryApi {
    private String currentLocationBarcode = "TOTE-001";
    private String targetLocationBarcode = "TOTE-002";
    private String lpnBarcode = "LPN-001";
    private Long quantity = 1L;

    public TransferInventoryApi currentLocationBarcode(final String currentLocationBarcode) {
        this.currentLocationBarcode = currentLocationBarcode;
        return this;
    }

    public TransferInventoryApi targetLocationBarcode(final String targetLocationBarcode) {
        this.targetLocationBarcode = targetLocationBarcode;
        return this;
    }

    public TransferInventoryApi lpnBarcode(final String lpnBarcode) {
        this.lpnBarcode = lpnBarcode;
        return this;
    }

    public TransferInventoryApi quantity(final Long quantity) {
        this.quantity = quantity;
        return this;
    }

    public Scenario request() {
        final TransferInventory.Request request = new TransferInventory.Request(
                currentLocationBarcode,
                targetLocationBarcode,
                lpnBarcode,
                quantity
        );

        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .contentType("application/json")
                .when()
                .post("/locations/transfer-inventory")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());


        return new Scenario();
    }
}
