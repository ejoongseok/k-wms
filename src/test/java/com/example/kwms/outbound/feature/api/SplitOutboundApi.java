package com.example.kwms.outbound.feature.api;

import com.example.kwms.common.Scenario;
import com.example.kwms.outbound.feature.SplitOutbound;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

public class SplitOutboundApi {
    private Long outboundNo = 1L;
    private Long productNo = 1L;
    private Long quantity = 1L;
    private List<SplitOutbound.Request.Product> targets = List.of(new SplitOutbound.Request.Product(productNo, quantity));

    public SplitOutboundApi outboundNo(final Long outboundNo) {
        this.outboundNo = outboundNo;
        return this;
    }

    public SplitOutboundApi productNo(final Long productNo) {
        this.productNo = productNo;
        return this;
    }

    public SplitOutboundApi quantity(final Long quantity) {
        this.quantity = quantity;
        return this;
    }

    public SplitOutboundApi targets(final SplitOutbound.Request.Product... targets) {
        this.targets = Arrays.asList(targets);
        return this;
    }

    public Scenario request() {
        final SplitOutbound.Request request = new SplitOutbound.Request(
                targets
        );

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/outbounds/{outboundNo}/split", outboundNo)
                .then()
                .statusCode(HttpStatus.OK.value());

        return new Scenario();
    }
}
