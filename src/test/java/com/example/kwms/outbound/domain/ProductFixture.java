package com.example.kwms.outbound.domain;

public class ProductFixture {
    private Long productNo = 1L;
    private String productName = "상품명";
    private String productCode = "상품바코드";
    private String option = "상품옵션";
    private Long weightInGrams = 100L;
    private Long widthInMillimeters = 10L;
    private Long heightInMillimeters = 10L;
    private Long lengthInMillimeters = 10L;

    public static ProductFixture aProduct() {
        return new ProductFixture();
    }

    public ProductFixture productNo(final Long productNo) {
        this.productNo = productNo;
        return this;
    }

    public ProductFixture productName(final String productName) {
        this.productName = productName;
        return this;
    }

    public ProductFixture productCode(final String productCode) {
        this.productCode = productCode;
        return this;
    }

    public ProductFixture option(final String option) {
        this.option = option;
        return this;
    }

    public ProductFixture weightInGrams(final Long weightInGrams) {
        this.weightInGrams = weightInGrams;
        return this;
    }

    public ProductFixture widthInMillimeters(final Long widthInMillimeters) {
        this.widthInMillimeters = widthInMillimeters;
        return this;
    }

    public ProductFixture heightInMillimeters(final Long heightInMillimeters) {
        this.heightInMillimeters = heightInMillimeters;
        return this;
    }

    public ProductFixture lengthInMillimeters(final Long lengthInMillimeters) {
        this.lengthInMillimeters = lengthInMillimeters;
        return this;
    }

    public Product build() {
        return new Product(
                productNo,
                productName,
                productCode,
                option,
                weightInGrams,
                widthInMillimeters,
                heightInMillimeters,
                lengthInMillimeters);
    }
}