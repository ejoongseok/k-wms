package com.example.kwms.outbound.feature.api;

import com.example.kwms.common.Scenario;
import com.example.kwms.outbound.domain.MaterialType;
import com.example.kwms.outbound.feature.CreatePackagingMaterial;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

public class CreatePackagingMaterialApi {
    private String name = "name";
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

    public CreatePackagingMaterialApi name(final String name) {
        this.name = name;
        return this;
    }

    public CreatePackagingMaterialApi code(final String code) {
        this.code = code;
        return this;
    }

    public CreatePackagingMaterialApi innerWidthInMillimeters(final Long innerWidthInMillimeters) {
        this.innerWidthInMillimeters = innerWidthInMillimeters;
        return this;
    }

    public CreatePackagingMaterialApi innerHeightInMillimeters(final Long innerHeightInMillimeters) {
        this.innerHeightInMillimeters = innerHeightInMillimeters;
        return this;
    }

    public CreatePackagingMaterialApi innerLengthInMillimeters(final Long innerLengthInMillimeters) {
        this.innerLengthInMillimeters = innerLengthInMillimeters;
        return this;
    }

    public CreatePackagingMaterialApi outerWidthInMillimeters(final Long outerWidthInMillimeters) {
        this.outerWidthInMillimeters = outerWidthInMillimeters;
        return this;
    }

    public CreatePackagingMaterialApi outerHeightInMillimeters(final Long outerHeightInMillimeters) {
        this.outerHeightInMillimeters = outerHeightInMillimeters;
        return this;
    }

    public CreatePackagingMaterialApi outerLengthInMillimeters(final Long outerLengthInMillimeters) {
        this.outerLengthInMillimeters = outerLengthInMillimeters;
        return this;
    }

    public CreatePackagingMaterialApi weightInGrams(final Long weightInGrams) {
        this.weightInGrams = weightInGrams;
        return this;
    }

    public CreatePackagingMaterialApi maxWeightInGrams(final Long maxWeightInGrams) {
        this.maxWeightInGrams = maxWeightInGrams;
        return this;
    }

    public CreatePackagingMaterialApi materialType(final MaterialType materialType) {
        this.materialType = materialType;
        return this;
    }

    public Scenario request() {
        final CreatePackagingMaterial.Request request = new CreatePackagingMaterial.Request(
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
                .post("/packaging-materials")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
        return new Scenario();
    }
}
