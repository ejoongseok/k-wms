package com.example.kwms.location.feature.api;

import com.example.kwms.common.Scenario;
import com.example.kwms.location.feature.AdjustInventory;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

public class AdjustInventoryApi {
    private String locationBarcode = "TOTE-001";
    private String lpnBarcode = "LPN-001";
    private Long quantity = 1L;
    private String reason = "재고가 맞지 않음.";


    public AdjustInventoryApi locationBarcode(final String locationBarcode) {
        this.locationBarcode = locationBarcode;
        return this;
    }

    public AdjustInventoryApi lpnBarcode(final String lpnBarcode) {
        this.lpnBarcode = lpnBarcode;
        return this;
    }

    public AdjustInventoryApi quantity(final Long quantity) {
        this.quantity = quantity;
        return this;
    }

    public AdjustInventoryApi reason(final String reason) {
        this.reason = reason;
        return this;
    }


    public Scenario request() {
        final AdjustInventory.Request request = new AdjustInventory.Request(
                quantity,
                reason
        );

        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/locations/{locationBarcode}/adjust-inventories/{lpnBarcode}",
                        locationBarcode, lpnBarcode)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
        return new Scenario();

    }
}
