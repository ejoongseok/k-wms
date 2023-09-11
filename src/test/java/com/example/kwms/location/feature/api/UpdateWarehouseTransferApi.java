package com.example.kwms.location.feature.api;

import com.example.kwms.common.Scenario;
import com.example.kwms.location.feature.UpdateWarehouseTransfer;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

public class UpdateWarehouseTransferApi {
    private Long warehouseTransferNo = 1L;
    private long fromWarehouseNo = 1L;
    private long toWarehouseNo = 2L;
    private String barcode = "WT-002";

    public UpdateWarehouseTransferApi warehouseTransferNo(final Long warehouseTransferNo) {
        this.warehouseTransferNo = warehouseTransferNo;
        return this;
    }

    public UpdateWarehouseTransferApi fromWarehouseNo(final long fromWarehouseNo) {
        this.fromWarehouseNo = fromWarehouseNo;
        return this;
    }

    public UpdateWarehouseTransferApi toWarehouseNo(final long toWarehouseNo) {
        this.toWarehouseNo = toWarehouseNo;
        return this;
    }

    public UpdateWarehouseTransferApi barcode(final String barcode) {
        this.barcode = barcode;
        return this;
    }


    public Scenario request() {
        final UpdateWarehouseTransfer.Request request = new UpdateWarehouseTransfer.Request(
                fromWarehouseNo,
                toWarehouseNo,
                barcode);

        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .patch("/warehouse-transfers/{warehouseTransferNo}", warehouseTransferNo)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());

        return new Scenario();
    }
}
