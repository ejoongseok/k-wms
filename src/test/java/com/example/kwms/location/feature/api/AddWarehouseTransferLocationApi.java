package com.example.kwms.location.feature.api;

import com.example.kwms.common.Scenario;
import com.example.kwms.location.feature.command.AddWarehouseTransferLocation;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

public class AddWarehouseTransferLocationApi {
    private Long warehouseTransferNo = 1L;
    private String locationBarcode = "TOTE-001";

    public AddWarehouseTransferLocationApi warehouseTransferNo(final Long warehouseTransferNo) {
        this.warehouseTransferNo = warehouseTransferNo;
        return this;
    }

    public AddWarehouseTransferLocationApi locationBarcode(final String locationBarcode) {
        this.locationBarcode = locationBarcode;
        return this;
    }

    public Scenario request() {
        final AddWarehouseTransferLocation.Request request = new AddWarehouseTransferLocation.Request(locationBarcode);

        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/warehouse-transfers/{warehouseTransferNo}/locations", warehouseTransferNo)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());

        return new Scenario();
    }
}
