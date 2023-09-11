package com.example.kwms.outbound.feature.api;

import com.example.kwms.common.Scenario;
import com.example.kwms.outbound.domain.MaterialType;
import com.example.kwms.outbound.feature.UpdatePackagingMaterial;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

public class UpdatePackagingMaterialApi {
    private Long packagingMaterialNo = 1L;
    private String name = "update name";
    private String code = "code";
    private Long innerWidthInMillimeters = 1000L;
    private Long innerHeightInMillimeters = 1000L;
    private Long innerLengthInMillimeters = 1000L;
    private Long outerWidthInMillimeters = 1000L;
    private Long outerHeightInMillimeters = 1000L;
    private Long outerLengthInMillimeters = 1000L;
    private Long weightInGrams = 100L;
    private Long maxWeightInGrams = 10000L;
    private MaterialType materialType = MaterialType.CORRUGATED_BOX;

    public UpdatePackagingMaterialApi packagingMaterialNo(final Long packagingMaterialNo) {
        this.packagingMaterialNo = packagingMaterialNo;
        return this;
    }


    public UpdatePackagingMaterialApi name(final String name) {
        this.name = name;
        return this;
    }

    public UpdatePackagingMaterialApi code(final String code) {
        this.code = code;
        return this;
    }

    public UpdatePackagingMaterialApi innerWidthInMillimeters(final Long innerWidthInMillimeters) {
        this.innerWidthInMillimeters = innerWidthInMillimeters;
        return this;
    }

    public UpdatePackagingMaterialApi innerHeightInMillimeters(final Long innerHeightInMillimeters) {
        this.innerHeightInMillimeters = innerHeightInMillimeters;
        return this;
    }

    public UpdatePackagingMaterialApi innerLengthInMillimeters(final Long innerLengthInMillimeters) {
        this.innerLengthInMillimeters = innerLengthInMillimeters;
        return this;
    }

    public UpdatePackagingMaterialApi outerWidthInMillimeters(final Long outerWidthInMillimeters) {
        this.outerWidthInMillimeters = outerWidthInMillimeters;
        return this;
    }

    public UpdatePackagingMaterialApi outerHeightInMillimeters(final Long outerHeightInMillimeters) {
        this.outerHeightInMillimeters = outerHeightInMillimeters;
        return this;
    }

    public UpdatePackagingMaterialApi outerLengthInMillimeters(final Long outerLengthInMillimeters) {
        this.outerLengthInMillimeters = outerLengthInMillimeters;
        return this;
    }

    public UpdatePackagingMaterialApi weightInGrams(final Long weightInGrams) {
        this.weightInGrams = weightInGrams;
        return this;
    }

    public UpdatePackagingMaterialApi maxWeightInGrams(final Long maxWeightInGrams) {
        this.maxWeightInGrams = maxWeightInGrams;
        return this;
    }

    public UpdatePackagingMaterialApi materialType(final MaterialType materialType) {
        this.materialType = materialType;
        return this;
    }

    public Scenario request() {
        final UpdatePackagingMaterial.Request request = new UpdatePackagingMaterial.Request(
                name,
                code,
                innerWidthInMillimeters,
                innerHeightInMillimeters,
                innerLengthInMillimeters,
                outerWidthInMillimeters,
                outerHeightInMillimeters,
                outerLengthInMillimeters,
                weightInGrams,
                maxWeightInGrams,
                materialType
        );

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .patch("/packaging-materials/{packagingMaterialNo}", packagingMaterialNo)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
        return new Scenario();
    }
}
