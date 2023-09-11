package com.example.kwms.location.feature.api;

import com.example.kwms.common.Scenario;
import com.example.kwms.location.feature.UpdateWarehouseTransferProduct;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

public class UpdateWarehouseTransferProductApi {
    private Long warehouseTransferNo = 1L;
    private Long warehouseTransferProductNo = 1L;
    private Long productNo = 2L;
    private Long quantity = 2L;

    public UpdateWarehouseTransferProductApi warehouseTransferNo(final Long warehouseTransferNo) {
        this.warehouseTransferNo = warehouseTransferNo;
        return this;
    }

    public UpdateWarehouseTransferProductApi warehouseTransferProductNo(final Long warehouseTransferProductNo) {
        this.warehouseTransferProductNo = warehouseTransferProductNo;
        return this;
    }

    public UpdateWarehouseTransferProductApi productNo(final Long productNo) {
        this.productNo = productNo;
        return this;
    }

    public UpdateWarehouseTransferProductApi quantity(final Long quantity) {
        this.quantity = quantity;
        return this;
    }

    public Scenario request() {
        final UpdateWarehouseTransferProduct.Request request = new UpdateWarehouseTransferProduct.Request(
                productNo,
                quantity);

        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .patch("/warehouse-transfers/{warehouseTransferNo}/transfer-products/{warehouseTransferProductNo}",
                        warehouseTransferNo, warehouseTransferProductNo)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());

        return new Scenario();
    }
}
