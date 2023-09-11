package com.example.kwms.location.feature.api;

import com.example.kwms.common.Scenario;
import com.example.kwms.location.feature.CreateWarehouseTransfer;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

public class CreateWarehouseTransferApi {
    private Long fromWarehouseNo = 1L;
    private Long toWarehouseNo = 2L;
    private String barcode = "WT-001";
    private List<CreateWarehouseTransfer.Request.Product> products = List.of(new CreateWarehouseTransfer.Request.Product(
            1L,
            1L
    ));

    public CreateWarehouseTransferApi fromWarehouseNo(final Long fromWarehouseNo) {
        this.fromWarehouseNo = fromWarehouseNo;
        return this;
    }

    public CreateWarehouseTransferApi toWarehouseNo(final Long toWarehouseNo) {
        this.toWarehouseNo = toWarehouseNo;
        return this;
    }

    public CreateWarehouseTransferApi barcode(final String barcode) {
        this.barcode = barcode;
        return this;
    }

    public CreateWarehouseTransferApi products(final CreateWarehouseTransfer.Request.Product... products) {
        this.products = Arrays.asList(products);
        return this;
    }

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
