package com.example.kwms.location.feature.api;

import com.example.kwms.common.Scenario;
import com.example.kwms.location.feature.command.UpdateLocationUsagePurpose;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

public class UpdateLocationUsagePurposeApi {
    private Long locationNo = 1L;
    private String usagePurpose = "FILL";

    public UpdateLocationUsagePurposeApi locationNo(final Long locationNo) {
        this.locationNo = locationNo;
        return this;
    }

    public UpdateLocationUsagePurposeApi usagePurpose(final String usagePurpose) {
        this.usagePurpose = usagePurpose;
        return this;
    }

    public Scenario request() {
        final UpdateLocationUsagePurpose.Request request = new UpdateLocationUsagePurpose.Request(usagePurpose);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .patch("/locations/{locationNo}/usage-purpose", locationNo)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());

        return new Scenario();
    }
}
