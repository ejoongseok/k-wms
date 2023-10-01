package com.example.kwms.location.feature.api;

import com.example.kwms.common.Scenario;
import com.example.kwms.location.feature.command.AddManualInventory;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

public class AddManualInventoryApi {
    private String locationBarcode = "TOTE-001";
    private String lpnBarcode = "LPN-001";
    private Long quantity = 10L;

    public AddManualInventoryApi locationBarcode(final String locationBarcode) {
        this.locationBarcode = locationBarcode;
        return this;
    }

    public AddManualInventoryApi lpnBarcode(final String lpnBarcode) {
        this.lpnBarcode = lpnBarcode;
        return this;
    }

    public AddManualInventoryApi quantity(final Long quantity) {
        this.quantity = quantity;
        return this;
    }

    public Scenario request() {
        final AddManualInventory.Request request = new AddManualInventory.Request(
                quantity
        );
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("locations/{locationBarcode}/manual-inventories/{lpnBarcode}",
                        locationBarcode, lpnBarcode)
                .then()
                .statusCode(HttpStatus.OK.value());
        return new Scenario();
    }
}
