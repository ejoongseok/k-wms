package com.example.kwms.outbound.feature.api;

import com.example.kwms.common.Scenario;
import io.restassured.RestAssured;
import org.springframework.http.HttpStatus;

public class PopBulkOutboundApi {
    private Long bulkOutboundNo = 1L;
    private Long quantity = 1L;

    public PopBulkOutboundApi bulkOutboundNo(final Long bulkOutboundNo) {
        this.bulkOutboundNo = bulkOutboundNo;
        return this;
    }

    public PopBulkOutboundApi quantity(final Long quantity) {
        this.quantity = quantity;
        return this;
    }

    public Scenario request() {
        RestAssured
                .given().log().all()
                .when()
                .post("/bulk-outbounds/{bulkOutboundNo}/pop/{quantity}", bulkOutboundNo, quantity)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
        return new Scenario();
    }
}
