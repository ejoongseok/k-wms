package com.example.kwms.outbound.feature.api;

import com.example.kwms.common.Scenario;
import com.example.kwms.outbound.feature.command.ManualAllocatePicker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

public class ManualAllocatePickerApi {

    private final Long outboundNo = 1L;
    private final Long pickerNo = 1L;

    public Scenario request() {
        final ManualAllocatePicker.Request request = new ManualAllocatePicker.Request(pickerNo);

        RestAssured
                .given().log().all()
                .body(request)
                .contentType(ContentType.JSON)
                .when()
                .post("/outbounds/{outboundNo}/allocate-picker", outboundNo)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());

        return new Scenario();
    }
}
