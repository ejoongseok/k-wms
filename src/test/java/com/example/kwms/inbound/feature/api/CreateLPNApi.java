package com.example.kwms.inbound.feature.api;

import com.example.kwms.common.Scenario;
import com.example.kwms.inbound.feature.command.CreateLPN;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class CreateLPNApi {
    private Long purchaseOrderNo = 1L;
    private Long purchaseOrderProductNo = 1L;
    private String lpnBarcode = "1234567890";
    private LocalDateTime expiringAt = LocalDateTime.now().plusDays(30);

    public CreateLPNApi purchaseOrderNo(final Long purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo;
        return this;
    }

    public CreateLPNApi purchaseOrderProductNo(final Long purchaseOrderProductNo) {
        this.purchaseOrderProductNo = purchaseOrderProductNo;
        return this;
    }

    public CreateLPNApi lpnBarcode(final String lpnBarcode) {
        this.lpnBarcode = lpnBarcode;
        return this;
    }

    public CreateLPNApi expiringAt(final LocalDateTime expiringAt) {
        this.expiringAt = expiringAt;
        return this;
    }


    public Scenario request() {
        final CreateLPN.Request request = new CreateLPN.Request(
                lpnBarcode,
                expiringAt
        );

        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/purchase-orders/{purchaseOrderNo}/purchase-order-products/{purchaseOrderProductNo}/lpns", purchaseOrderNo, purchaseOrderProductNo)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());

        return new Scenario();
    }
}
