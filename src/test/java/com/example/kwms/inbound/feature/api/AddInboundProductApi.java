package com.example.kwms.inbound.feature.api;

import com.example.kwms.common.Scenario;
import com.example.kwms.inbound.feature.AddInboundProduct;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class AddInboundProductApi {

    private Long inboundNo = 1L;
    private Long productNo = 1L;
    private Long requestQuantity = 1_000L;
    private Long unitPrice = 15_000L;
    private String description = "블랙핑크 3집 앨범[] - 미입고분 추가 입고";
    private AddInboundProduct.Request.Product product = new AddInboundProduct.Request.Product(
            productNo,
            requestQuantity,
            unitPrice,
            description
    );

    public AddInboundProductApi inboundNo(final Long inboundNo) {
        this.inboundNo = inboundNo;
        return this;
    }

    public AddInboundProductApi productNo(final long productNo) {
        this.productNo = productNo;
        return this;
    }

    public AddInboundProductApi requestQuantity(final long requestQuantity) {
        this.requestQuantity = requestQuantity;
        return this;
    }

    public AddInboundProductApi unitPrice(final long unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public AddInboundProductApi description(final String description) {
        this.description = description;
        return this;
    }

    public AddInboundProductApi product(final AddInboundProduct.Request.Product product) {
        this.product = product;
        return this;
    }

    public Scenario request() {
        final AddInboundProduct.Request request = new AddInboundProduct.Request(
                inboundNo,
                product
        );
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/inbounds/products")
                .then().log().all()
                .statusCode(201);
        return new Scenario();
    }
}
