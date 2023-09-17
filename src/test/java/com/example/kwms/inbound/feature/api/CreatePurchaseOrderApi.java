package com.example.kwms.inbound.feature.api;

import com.example.kwms.common.Scenario;
import com.example.kwms.inbound.feature.command.CreatePurchaseOrder;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class CreatePurchaseOrderApi {
    private final Long warehouseNo = 1L;
    private String title = "블랙핑크 앨범 입고";
    private LocalDateTime estimatedArrivalAt = LocalDateTime.now().plusDays(1L);
    private LocalDateTime orderRequestedAt = LocalDateTime.now();
    private String description = "23년도 블랙핑크 신규 앨범 주문";
    private List<CreatePurchaseOrder.Request.Product> products = List.of(new CreatePurchaseOrder.Request.Product(
            1L,
            2_000L,
            15_000L,
            "블랙핑크 3집 앨범[]"
    ));

    public CreatePurchaseOrderApi title(final String title) {
        this.title = title;
        return this;
    }

    public CreatePurchaseOrderApi estimatedArrivalAt(final LocalDateTime estimatedArrivalAt) {
        this.estimatedArrivalAt = estimatedArrivalAt;
        return this;
    }

    public CreatePurchaseOrderApi orderRequestedAt(final LocalDateTime orderRequestedAt) {
        this.orderRequestedAt = orderRequestedAt;
        return this;
    }

    public CreatePurchaseOrderApi description(final String description) {
        this.description = description;
        return this;
    }

    public CreatePurchaseOrderApi products(final CreatePurchaseOrder.Request.Product... products) {
        this.products = Arrays.asList(products);
        return this;
    }

    public Scenario request() {
        final CreatePurchaseOrder.Request request = new CreatePurchaseOrder.Request(
                warehouseNo,
                title,
                description,
                products);
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/purchase-orders")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
        return new Scenario();
    }
}
