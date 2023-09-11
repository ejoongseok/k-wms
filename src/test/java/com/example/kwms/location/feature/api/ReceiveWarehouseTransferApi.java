package com.example.kwms.location.feature.api;

import com.example.kwms.common.Scenario;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class ReceiveWarehouseTransferApi {
    private String warehouseTransferBarcode = "WT-001";

    public ReceiveWarehouseTransferApi warehouseTransferBarcode(final String warehouseTransferBarcode) {
        this.warehouseTransferBarcode = warehouseTransferBarcode;
        return this;
    }

    public Scenario request() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .when()
                .post("/warehouse-transfers/{warehouseTransferBarcode}/receive", warehouseTransferBarcode)
                .then().log().all()
                .statusCode(200);

        return new Scenario();
    }
}
