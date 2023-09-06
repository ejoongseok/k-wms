package com.example.kwms.inbound.domain;

import org.springframework.util.Assert;

public class InboundProduct {
    private final Long productNo;
    private final Long requestQuantity;
    private final Long unitPrice;
    private final String description;
    private Inbound inbound;
    /**
     * 발주요청 상태에서 생성되었는지 입고 이후에 추가로 입고된 상품인지를 구분하기 위한 필드
     */
    private boolean isAdded;

    public InboundProduct(
            final Long productNo,
            final Long requestQuantity,
            final Long unitPrice,
            final String description) {
        Assert.notNull(productNo, "상품 번호는 필수입니다.");
        Assert.notNull(requestQuantity, "상품 입고 요청 수량은 필수입니다.");
        if (1 > requestQuantity)
            throw new IllegalArgumentException("상품 입고 요청 수량은 1개 이상이어야 합니다.");
        Assert.notNull(unitPrice, "상품 입고 요청 단가는 필수입니다.");
        if (0 > unitPrice)
            throw new IllegalArgumentException("상품 입고 요청 단가는 0원 이상이어야 합니다.");

        this.productNo = productNo;
        this.requestQuantity = requestQuantity;
        this.unitPrice = unitPrice;
        this.description = description;
    }

    void assignInbound(final Inbound inbound) {
        Assert.notNull(inbound, "입고는 필수입니다.");
        this.inbound = inbound;
    }

}
