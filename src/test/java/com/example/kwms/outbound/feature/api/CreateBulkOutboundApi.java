package com.example.kwms.outbound.feature.api;

import com.example.kwms.common.Scenario;
import com.example.kwms.outbound.feature.CreateBulkOutbound;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

public class CreateBulkOutboundApi {

    private List<Long> outboundNos = Arrays.asList(1L, 2L);

    public CreateBulkOutboundApi outboundNos(final Long... outboundNos) {
        this.outboundNos = Arrays.asList(outboundNos);
        return this;
    }


    public Scenario request() {
        outboundNos = List.of(1L, 2L);
        final CreateBulkOutbound.Request request = new CreateBulkOutbound.Request(
                outboundNos
        );

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/outbounds/bulk")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        return new Scenario();
    }
}
