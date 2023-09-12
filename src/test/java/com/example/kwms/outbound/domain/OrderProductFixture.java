package com.example.kwms.outbound.domain;

public class OrderProductFixture {

    private Long productNo = 1L;
    private Long orderQuantity = 1L;
    private Long unitPrice = 1500L;

    public static OrderProductFixture anOrderProduct() {
        return new OrderProductFixture();
    }

    public OrderProductFixture productNo(final Long productNo) {
        this.productNo = productNo;
        return this;
    }

    public OrderProductFixture orderQuantity(final Long orderQuantity) {
        this.orderQuantity = orderQuantity;
        return this;
    }

    public OrderProductFixture unitPrice(final Long unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public OrderProduct build() {
        return new OrderProduct(
                productNo,
                orderQuantity,
                unitPrice);
    }
}