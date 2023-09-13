package com.example.kwms.outbound.feature.api;

import com.example.kwms.common.Scenario;
import io.restassured.RestAssured;
import org.springframework.http.HttpStatus;

public class TransferOutboundApi {
    private Long outboundNo = 1L;
    private Long toWarehouseNo = 2L;

    public TransferOutboundApi outboundNo(final Long outboundNo) {
        this.outboundNo = outboundNo;
        return this;
    }

    public TransferOutboundApi toWarehouseNo(final Long toWarehouseNo) {
        this.toWarehouseNo = toWarehouseNo;
        return this;
    }

    public Scenario request() {
        RestAssured.given().log().all()
                .when()
                .post("/outbounds/{outboundNo}/transfer-warehouse/{toWarehouseNo}",
                        outboundNo, toWarehouseNo)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());

        return new Scenario();
    }
}
