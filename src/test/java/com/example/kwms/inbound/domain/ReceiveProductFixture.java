package com.example.kwms.inbound.domain;

import java.time.LocalDateTime;

public class ReceiveProductFixture {
    private long productNo = 1L;
    private long acceptableQuantity = 1L;
    private long rejectedQuantity;
    private String inspectionComment = "검수 코멘트";
    private LocalDateTime inspectedAt = LocalDateTime.now();

    static ReceiveProductFixture aReceiveProduct() {
        return new ReceiveProductFixture();
    }

    public ReceiveProductFixture productNo(final long productNo) {
        this.productNo = productNo;
        return this;
    }

    public ReceiveProductFixture acceptableQuantity(final long acceptableQuantity) {
        this.acceptableQuantity = acceptableQuantity;
        return this;
    }

    public ReceiveProductFixture rejectedQuantity(final long rejectedQuantity) {
        this.rejectedQuantity = rejectedQuantity;
        return this;
    }

    public ReceiveProductFixture inspectionComment(final String inspectionComment) {
        this.inspectionComment = inspectionComment;
        return this;
    }

    public ReceiveProductFixture inspectedAt(final LocalDateTime inspectedAt) {
        this.inspectedAt = inspectedAt;
        return this;
    }

    public ReceiveProduct build() {
        return new ReceiveProduct(
                productNo,
                acceptableQuantity,
                rejectedQuantity,
                inspectionComment,
                inspectedAt
        );
    }
}