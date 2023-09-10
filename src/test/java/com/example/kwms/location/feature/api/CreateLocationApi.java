package com.example.kwms.location.feature.api;

import com.example.kwms.common.Scenario;
import com.example.kwms.location.domain.StorageType;
import com.example.kwms.location.domain.UsagePurpose;
import com.example.kwms.location.feature.CreateLocation;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

public class CreateLocationApi {
    private String locationBarcode = "TOTE-001";
    private StorageType storageType = StorageType.TOTE;
    private UsagePurpose usagePurpose = UsagePurpose.MOVE;

    public CreateLocationApi locationBarcode(final String locationBarcode) {
        this.locationBarcode = locationBarcode;
        return this;
    }

    public CreateLocationApi storageType(final StorageType storageType) {
        this.storageType = storageType;
        return this;
    }

    public CreateLocationApi usagePurpose(final UsagePurpose usagePurpose) {
        this.usagePurpose = usagePurpose;
        return this;
    }

    public Scenario request() {
        final CreateLocation.Request request = new CreateLocation.Request(
                locationBarcode,
                storageType,
                usagePurpose
        );
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/locations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
        return new Scenario();
    }
}
