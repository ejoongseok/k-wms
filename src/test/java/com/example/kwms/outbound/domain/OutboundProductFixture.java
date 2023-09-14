package com.example.kwms.outbound.domain;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class OutboundProductFixture {
    private Long productNo = 1L;
    private Long orderQuantity = 1L;
    private Long unitPrice = 1500L;
    private Long outboundProductNo = 1L;
    private List<PickingFixture> pickings = new ArrayList<>();

    public static OutboundProductFixture anOutboundProduct() {
        return new OutboundProductFixture();
    }

    public OutboundProductFixture outboundProductNo(final Long outboundProductNo) {
        this.outboundProductNo = outboundProductNo;
        return this;
    }

    public OutboundProductFixture productNo(final Long productNo) {
        this.productNo = productNo;
        return this;
    }

    public OutboundProductFixture orderQuantity(final Long orderQuantity) {
        this.orderQuantity = orderQuantity;
        return this;
    }

    public OutboundProductFixture unitPrice(final Long unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public OutboundProductFixture pickings(final PickingFixture... pickings) {
        this.pickings = Arrays.asList(pickings);
        return this;
    }


    public OutboundProduct build() {

        return new OutboundProduct(
                productNo,
                orderQuantity,
                unitPrice,
                pickings.stream()
                        .map(PickingFixture::build)
                        .collect(Collectors.toList())
        );
    }
}