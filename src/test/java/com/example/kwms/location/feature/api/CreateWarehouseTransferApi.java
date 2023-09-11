package com.example.kwms.location.feature.api;

import com.example.kwms.common.Scenario;
import com.example.kwms.location.feature.CreateWarehouseTransfer;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

import java.util.List;

public class CreateWarehouseTransferApi {
    final Long fromWarehouseNo = 1L;
    final Long toWarehouseNo = 2L;
    final String barcode = "WT-001";
    final List<CreateWarehouseTransfer.Request.Product> products = List.of(new CreateWarehouseTransfer.Request.Product(
            1L,
            1L
    ));

    public Scenario request() {
        final CreateWarehouseTransfer.Request request = new CreateWarehouseTransfer.Request(
                fromWarehouseNo,
                toWarehouseNo,
                barcode,
                products
        );

        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/warehouse-transfers")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        return new Scenario();
    }
}
