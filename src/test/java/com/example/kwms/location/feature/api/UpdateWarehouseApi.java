package com.example.kwms.location.feature.api;

import com.example.kwms.common.Scenario;
import com.example.kwms.location.feature.UpdateWarehouse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

public class UpdateWarehouseApi {
    private Long warehouseNo = 1L;
    private String name = "창고명 수정";
    private String address = "주소 수정";
    private String telNumber = "전화번호 수정";
    private String managerName = "담당자명 수정";
    private String managerTelNumber = "담당자 연락처 수정";
    private String description = "비고 수정";

    public UpdateWarehouseApi warehouseNo(final Long warehouseNo) {
        this.warehouseNo = warehouseNo;
        return this;
    }

    public UpdateWarehouseApi name(final String name) {
        this.name = name;
        return this;
    }

    public UpdateWarehouseApi address(final String address) {
        this.address = address;
        return this;
    }

    public UpdateWarehouseApi telNumber(final String telNumber) {
        this.telNumber = telNumber;
        return this;
    }

    public UpdateWarehouseApi managerName(final String managerName) {
        this.managerName = managerName;
        return this;
    }

    public UpdateWarehouseApi managerTelNumber(final String managerTelNumber) {
        this.managerTelNumber = managerTelNumber;
        return this;
    }

    public UpdateWarehouseApi description(final String description) {
        this.description = description;
        return this;
    }

    public Scenario request() {
        final UpdateWarehouse.Request request = new UpdateWarehouse.Request(
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
                .patch("/warehouses/{warehouseNo}", warehouseNo)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());

        return new Scenario();
    }
}
