package com.example.kwms.outbound.domain;

import lombok.Getter;

@Getter
public class Product {
    private final Long productNo;
    private final String productName;
    private final String productCode;
    private final String option;
    private final Long weightInGrams;
    private final Long widthInMillimeters;
    private final Long heightInMillimeters;
    private final Long lengthInMillimeters;

    public Product(
            final Long productNo,
            final String productName,
            final String productCode,
            final String option,
            final Long weightInGrams,
            final Long widthInMillimeters,
            final Long heightInMillimeters,
            final Long lengthInMillimeters) {
        this.productNo = productNo;
        this.productName = productName;
        this.productCode = productCode;
        this.option = option;
        this.weightInGrams = weightInGrams;
        this.widthInMillimeters = widthInMillimeters;
        this.heightInMillimeters = heightInMillimeters;
        this.lengthInMillimeters = lengthInMillimeters;
    }

    public Long volume() {
        return widthInMillimeters * heightInMillimeters * lengthInMillimeters;
    }
}
