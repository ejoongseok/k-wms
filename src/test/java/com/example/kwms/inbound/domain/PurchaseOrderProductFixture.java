package com.example.kwms.inbound.domain;

public class PurchaseOrderProductFixture {
    private final Long inboundProductNo = 1L;
    private Long productNo = 1L;
    private Long requestQuantity = 1L;
    private Long unitPrice = 1000L;
    private String description = "상품 입고 설명";

    public static PurchaseOrderProductFixture aPurchaseOrderProduct() {
        return new PurchaseOrderProductFixture();
    }


    public PurchaseOrderProductFixture productNo(final Long productNo) {
        this.productNo = productNo;
        return this;
    }

    public PurchaseOrderProductFixture requestQuantity(final Long requestQuantity) {
        this.requestQuantity = requestQuantity;
        return this;
    }

    public PurchaseOrderProductFixture unitPrice(final Long unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public PurchaseOrderProductFixture description(final String description) {
        this.description = description;
        return this;
    }

    public PurchaseOrderProduct build() {
        return new PurchaseOrderProduct(
                inboundProductNo,
                requestQuantity,
                unitPrice,
                description,
                productNo);
    }
}
