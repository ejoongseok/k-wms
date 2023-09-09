package com.example.kwms.inbound.feature.api;

import com.example.kwms.common.Scenario;
import com.example.kwms.inbound.feature.UpdateInboundProduct;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

public class UpdateInboundProductApi {
    private Long inboundNo = 1L;
    private Long inboundProductNo = 1L;
    private Long productNo = 1L;
    private Long requestQuantity = 1_000L;
    private Long unitPrice = 15_000L;
    private String description = "블랙핑크 입고 수정";

    public UpdateInboundProductApi inboundNo(final Long inboundNo) {
        this.inboundNo = inboundNo;
        return this;
    }

    public UpdateInboundProductApi inboundProductNo(final Long inboundProductNo) {
        this.inboundProductNo = inboundProductNo;
        return this;
    }

    public UpdateInboundProductApi productNo(final long productNo) {
        this.productNo = productNo;
        return this;
    }

    public UpdateInboundProductApi requestQuantity(final long requestQuantity) {
        this.requestQuantity = requestQuantity;
        return this;
    }

    public UpdateInboundProductApi unitPrice(final long unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public UpdateInboundProductApi description(final String description) {
        this.description = description;
        return this;
    }

    public Scenario request() {
        final UpdateInboundProduct.Request request = new UpdateInboundProduct.Request(
                productNo,
                requestQuantity,
                unitPrice,
                description
        );
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .patch("/inbounds/{inboundNo}/products/{inboundProductNo}", inboundNo, inboundProductNo)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
        return new Scenario();
    }
}