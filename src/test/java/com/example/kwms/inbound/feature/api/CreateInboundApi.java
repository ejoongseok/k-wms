package com.example.kwms.inbound.feature.api;

import com.example.kwms.common.Scenario;
import com.example.kwms.inbound.feature.CreateInbound;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class CreateInboundApi {

    private String title = "블랙핑크 앨범 입고";
    private LocalDateTime estimatedArrivalAt = LocalDateTime.now().plusDays(1L);
    private LocalDateTime orderRequestedAt = LocalDateTime.now();
    private String description = "23년도 블랙핑크 신규 앨범 주문";
    private List<CreateInbound.Request.Product> products = List.of(new CreateInbound.Request.Product(
            1L,
            2_000L,
            15_000L,
            "블랙핑크 3집 앨범[]"
    ));

    public CreateInboundApi title(final String title) {
        this.title = title;
        return this;
    }

    public CreateInboundApi estimatedArrivalAt(final LocalDateTime estimatedArrivalAt) {
        this.estimatedArrivalAt = estimatedArrivalAt;
        return this;
    }

    public CreateInboundApi orderRequestedAt(final LocalDateTime orderRequestedAt) {
        this.orderRequestedAt = orderRequestedAt;
        return this;
    }

    public CreateInboundApi description(final String description) {
        this.description = description;
        return this;
    }

    public CreateInboundApi products(final CreateInbound.Request.Product... products) {
        this.products = Arrays.asList(products);
        return this;
    }

    public Scenario request() {
        final CreateInbound.Request request = new CreateInbound.Request(
                title,
                estimatedArrivalAt,
                orderRequestedAt,
                description,
                products);
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/inbounds")
                .then().log().all()
                .statusCode(201);
        return new Scenario();
    }
}
