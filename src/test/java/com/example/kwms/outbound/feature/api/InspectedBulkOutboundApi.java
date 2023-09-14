package com.example.kwms.outbound.feature.api;

import com.example.kwms.common.Scenario;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

public class InspectedBulkOutboundApi {
    private Long bulkOutboundNo = 1L;

    public InspectedBulkOutboundApi bulkOutboundNo(final Long bulkOutboundNo) {
        this.bulkOutboundNo = bulkOutboundNo;
        return this;
    }

    public Scenario request() {
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .when()
                .post("/bulk-outbounds/{bulkOutboundNo}/inspected", bulkOutboundNo)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
        return new Scenario();
    }
}
