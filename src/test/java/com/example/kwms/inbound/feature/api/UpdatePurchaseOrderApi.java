package com.example.kwms.inbound.feature.api;

import com.example.kwms.common.Scenario;
import com.example.kwms.inbound.feature.command.UpdatePurchaseOrder;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

public class UpdatePurchaseOrderApi {
    private Long warehouseNo = 1L;

    private Long purchaseOrderNo = 1L;
    private String title = "블랙핑크 앨범 수정";
    private String description = "23년도 블랙핑크 신규 앨범 주문";
    private List<UpdatePurchaseOrder.Request.Product> products = List.of(
            new UpdatePurchaseOrder.Request.Product(
                    1L,
                    1_000L,
                    15_000L,
                    "블랙핑크 앨범"
            )
    );

    public UpdatePurchaseOrderApi warehouseNo(final Long warehouseNo) {
        this.warehouseNo = warehouseNo;
        return this;
    }

    public UpdatePurchaseOrderApi purchaseOrderNo(final Long purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo;
        return this;
    }

    public UpdatePurchaseOrderApi title(final String title) {
        this.title = title;
        return this;
    }

    public UpdatePurchaseOrderApi description(final String description) {
        this.description = description;
        return this;
    }

    public UpdatePurchaseOrderApi products(final List<UpdatePurchaseOrder.Request.Product> products) {
        this.products = products;
        return this;
    }


    public Scenario request() {
        final UpdatePurchaseOrder.Request request = new UpdatePurchaseOrder.Request(
                warehouseNo,
                title,
                description,
                products);
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .put("/purchase-orders/{purchaseOrderNo}", purchaseOrderNo)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
        return new Scenario();
    }
}
