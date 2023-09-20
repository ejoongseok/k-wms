package com.example.kwms.location.feature.api;

import com.example.kwms.common.Scenario;
import com.example.kwms.location.feature.UpdateWarehouseTransfer;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

public class UpdateWarehouseTransferApi {
    private Long warehouseTransferNo = 1L;
    private long fromWarehouseNo = 1L;
    private long toWarehouseNo = 2L;
    private String barcode = "WT-002";
    List<UpdateWarehouseTransfer.Request.Product> products = List.of(new UpdateWarehouseTransfer.Request.Product(2L, 2L));

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

    public UpdateWarehouseTransferApi products(final List<UpdateWarehouseTransfer.Request.Product> products) {
        this.products = products;
        return this;
    }


    public Scenario request() {
        final UpdateWarehouseTransfer.Request request = new UpdateWarehouseTransfer.Request(
                fromWarehouseNo,
                toWarehouseNo,
                barcode,
                products);

        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .put("/warehouse-transfers/{warehouseTransferNo}", warehouseTransferNo)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());

        return new Scenario();
    }
}
