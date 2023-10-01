package com.example.kwms.outbound.feature.api;

import com.example.kwms.common.Scenario;
import com.example.kwms.outbound.feature.command.PickingBulkOutbound;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

public class PickingBulkOutboundApi {
    private Long bulkOutboundNo = 1L;
    private List<PickingBulkOutbound.Request.Picked> pickedList = List.of(new PickingBulkOutbound.Request.Picked(
            "TOTE-001",
            "LPN-001",
            1L));

    public PickingBulkOutboundApi bulkOutboundNo(final Long bulkOutboundNo) {
        this.bulkOutboundNo = bulkOutboundNo;
        return this;
    }

    public PickingBulkOutboundApi bulkOutboundNo(final PickingBulkOutbound.Request.Picked... pickeds) {
        pickedList = Arrays.asList(pickeds);
        return this;
    }


    public Scenario request() {
        final PickingBulkOutbound.Request request = new PickingBulkOutbound.Request(pickedList);

        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/outbounds/bulk/{bulkOutboundNo}/picking", bulkOutboundNo)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());

        return new Scenario();
    }
}
