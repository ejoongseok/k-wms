package com.example.kwms.location.feature.api;

import com.example.kwms.common.Scenario;
import io.restassured.RestAssured;
import org.springframework.http.HttpStatus;

public class AddInventoryApi {
    private String locationBarcode = "TOTE-001";
    private String lpnBarcode = "LPN-001";

    public AddInventoryApi locationBarcode(final String locationBarcode) {
        this.locationBarcode = locationBarcode;
        return this;
    }

    public AddInventoryApi lpnBarcode(final String lpnBarcode) {
        this.lpnBarcode = lpnBarcode;
        return this;
    }

    public Scenario request() {
        RestAssured.given().log().all()
                .when()
                .post("/locations/{locationBarcode}/inventories/{lpnBarcode}",
                        locationBarcode, lpnBarcode)
                .then()
                .statusCode(HttpStatus.OK.value());
        return new Scenario();
    }
}
