package com.example.kwms.location.feature.api;

import com.example.kwms.common.Scenario;
import com.example.kwms.location.feature.command.CreateWarehouse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

public class CreateWarehouseApi {
    private String name = "창고명";
    private String address = "주소";
    private String telNumber = "전화번호";
    private String managerName = "담당자명";
    private String managerTelNumber = "담당자 연락처";
    private String description = "비고";

    public CreateWarehouseApi name(final String name) {
        this.name = name;
        return this;
    }

    public CreateWarehouseApi address(final String address) {
        this.address = address;
        return this;
    }

    public CreateWarehouseApi telNumber(final String telNumber) {
        this.telNumber = telNumber;
        return this;
    }

    public CreateWarehouseApi managerName(final String managerName) {
        this.managerName = managerName;
        return this;
    }

    public CreateWarehouseApi managerTelNumber(final String managerTelNumber) {
        this.managerTelNumber = managerTelNumber;
        return this;
    }

    public CreateWarehouseApi description(final String description) {
        this.description = description;
        return this;
    }

    public Scenario request() {
        final CreateWarehouse.Request request = new CreateWarehouse.Request(
                name,
                address,
                telNumber,
                managerName,
                managerTelNumber,
                description
        );

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/warehouses")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        return new Scenario();
    }
}
