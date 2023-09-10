package com.example.kwms.inbound.feature.api;

import com.example.kwms.common.Scenario;
import com.example.kwms.inbound.feature.RegisterInboundProductInspectionResult;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class RegisterInboundProductInspectionResultApi {
    private Long inboundNo = 1L;
    private Long inboundProductNo = 1L;
    private LocalDateTime inspectedAt = LocalDateTime.now();
    private LocalDateTime arrivedAt = LocalDateTime.now();
    private String comment = "1000개만 먼저 입고 됨.";
    private Long acceptableQuantity = 1000L;
    private Long rejectedQuantity = 0L;

    public RegisterInboundProductInspectionResultApi inboundNo(final Long inboundNo) {
        this.inboundNo = inboundNo;
        return this;
    }

    public RegisterInboundProductInspectionResultApi inboundProductNo(final Long inboundProductNo) {
        this.inboundProductNo = inboundProductNo;
        return this;
    }

    public RegisterInboundProductInspectionResultApi inspectedAt(final LocalDateTime inspectedAt) {
        this.inspectedAt = inspectedAt;
        return this;
    }

    public RegisterInboundProductInspectionResultApi arrivedAt(final LocalDateTime arrivedAt) {
        this.arrivedAt = arrivedAt;
        return this;
    }

    public RegisterInboundProductInspectionResultApi comment(final String comment) {
        this.comment = comment;
        return this;
    }

    public RegisterInboundProductInspectionResultApi acceptableQuantity(final Long acceptableQuantity) {
        this.acceptableQuantity = acceptableQuantity;
        return this;
    }

    public RegisterInboundProductInspectionResultApi rejectedQuantity(final Long rejectedQuantity) {
        this.rejectedQuantity = rejectedQuantity;
        return this;
    }

    public Scenario request() {
        final RegisterInboundProductInspectionResult.Request request = new RegisterInboundProductInspectionResult.Request(
                inspectedAt,
                arrivedAt,
                comment,
                acceptableQuantity,
                rejectedQuantity
        );

        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/inbounds/{inboundNo}/products/{inboundProductNo}/inspection-result",
                        inboundNo,
                        inboundProductNo)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());

        return new Scenario();
    }
}
