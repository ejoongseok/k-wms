package com.example.kwms.inbound.feature.api;

import com.example.kwms.common.Scenario;
import com.example.kwms.inbound.feature.command.AddReceive;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

public class AddReceiveApi {
    List<AddReceive.Request.ReceiveRequest> receiveRequests = List.of(
            new AddReceive.Request.ReceiveRequest(
                    1L,
                    "1000개만 먼저 입고 됨.",
                    1000L,
                    0L
            )
    );
    private final String name = "입고 명";
    private Long purchaseOrderNo = 1L;

    public AddReceiveApi purchaseOrderNo(final Long purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo;
        return this;
    }

    public AddReceiveApi receives(final AddReceive.Request.ReceiveRequest... receiveRequests) {
        this.receiveRequests = Arrays.asList(receiveRequests);
        return this;
    }

    public Scenario request() {
        final AddReceive.Request request = new AddReceive.Request(
                name,
                receiveRequests
        );

        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/purchase-orders/{purchaseOrderNo}/receives", purchaseOrderNo)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());

        return new Scenario();
    }
}
