package com.example.kwms.outbound.feature.api;

import com.example.kwms.common.Scenario;
import com.example.kwms.outbound.feature.command.CreateOutbound;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

public class CreateOutboundApi {
    private Long orderNo = 1L;
    private Long warehouseNo = 1L;
    private boolean isPriorityDelivery;
    private LocalDate desiredDeliveryAt = LocalDate.now();

    public CreateOutboundApi orderNo(final Long orderNo) {
        this.orderNo = orderNo;
        return this;
    }

    public CreateOutboundApi warehouseNo(final Long warehouseNo) {
        this.warehouseNo = warehouseNo;
        return this;
    }

    public CreateOutboundApi isPriorityDelivery(final boolean isPriorityDelivery) {
        this.isPriorityDelivery = isPriorityDelivery;
        return this;
    }

    public CreateOutboundApi desiredDeliveryAt(final LocalDate desiredDeliveryAt) {
        this.desiredDeliveryAt = desiredDeliveryAt;
        return this;
    }

    public Scenario request() {
        final CreateOutbound.Request request = new CreateOutbound.Request(
                warehouseNo,
                orderNo,
                isPriorityDelivery,
                desiredDeliveryAt
        );

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/outbounds")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        return new Scenario();
    }
}
