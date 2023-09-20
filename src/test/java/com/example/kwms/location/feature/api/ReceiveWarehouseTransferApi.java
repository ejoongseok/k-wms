package com.example.kwms.location.feature.api;

import com.example.kwms.common.Scenario;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class ReceiveWarehouseTransferApi {
    private Long warehouseTransferNo = 1L;

    public ReceiveWarehouseTransferApi warehouseTransferNo(final Long warehouseTransferNo) {
        this.warehouseTransferNo = warehouseTransferNo;
        return this;
    }

    public Scenario request() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .when()
                .post("/warehouse-transfers/{warehouseTransferNo}/receive", warehouseTransferNo)
                .then().log().all()
                .statusCode(200);

        return new Scenario();
    }
}
