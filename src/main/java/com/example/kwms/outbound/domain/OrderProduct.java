package com.example.kwms.outbound.domain;

import lombok.Getter;

@Getter
public class OrderProduct {
    private final Long productNo;
    private final Long orderQuantity;
    private final Long unitPrice;

    public OrderProduct(final Long productNo, final Long orderQuantity, final Long unitPrice) {
        this.productNo = productNo;
        this.orderQuantity = orderQuantity;
        this.unitPrice = unitPrice;
    }
}
