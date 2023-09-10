package com.example.kwms.inbound.domain;

import java.time.LocalDateTime;

public class InboundProductFixture {
    private final Long inboundProductNo = 1L;
    private Long productNo = 1L;
    private Long requestQuantity = 1L;
    private Long unitPrice = 1000L;
    private String description = "상품 입고 설명";
    private boolean isAdded;
    private LocalDateTime inspectedAt = LocalDateTime.now();
    private LocalDateTime arrivedAt = LocalDateTime.now();
    private String inspectionComment = "검수 코멘트";
    private Long acceptableQuantity = 1L;
    private Long rejectedQuantity = 0L;

    public static InboundProductFixture aInboundProduct() {
        return new InboundProductFixture();
    }

    public static InboundProductFixture aNoInspectedInboundProduct() {
        return aInboundProduct()
                .isAdded(false)
                .inspectedAt(null)
                .arrivedAt(null)
                .inspectionComment(null)
                .acceptableQuantity(null)
                .rejectedQuantity(null);
    }

    public InboundProductFixture productNo(final Long productNo) {
        this.productNo = productNo;
        return this;
    }

    public InboundProductFixture requestQuantity(final Long requestQuantity) {
        this.requestQuantity = requestQuantity;
        return this;
    }

    public InboundProductFixture unitPrice(final Long unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public InboundProductFixture description(final String description) {
        this.description = description;
        return this;
    }

    public InboundProductFixture isAdded(final boolean isAdded) {
        this.isAdded = isAdded;
        return this;
    }

    public InboundProductFixture inspectedAt(final LocalDateTime inspectedAt) {
        this.inspectedAt = inspectedAt;
        return this;
    }

    public InboundProductFixture arrivedAt(final LocalDateTime arrivedAt) {
        this.arrivedAt = arrivedAt;
        return this;
    }

    public InboundProductFixture inspectionComment(final String inspectionComment) {
        this.inspectionComment = inspectionComment;
        return this;
    }

    public InboundProductFixture acceptableQuantity(final Long acceptableQuantity) {
        this.acceptableQuantity = acceptableQuantity;
        return this;
    }

    public InboundProductFixture rejectedQuantity(final Long rejectedQuantity) {
        this.rejectedQuantity = rejectedQuantity;
        return this;
    }

    public InboundProduct build() {
        return new InboundProduct(
                inboundProductNo,
                productNo,
                requestQuantity,
                unitPrice,
                description,
                isAdded,
                inspectedAt,
                arrivedAt,
                inspectionComment,
                acceptableQuantity,
                rejectedQuantity);
    }
}
