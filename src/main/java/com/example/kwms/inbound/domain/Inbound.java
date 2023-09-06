package com.example.kwms.inbound.domain;

import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Inbound {
    private final String title;
    private final LocalDateTime estimatedArrivalAt;
    private final LocalDateTime orderRequestedAt;
    private final String description;
    private final List<InboundProduct> inboundProducts = new ArrayList<>();
    private final InboundStatus status;


    public Inbound(
            final String title,
            final LocalDateTime estimatedArrivalAt,
            final LocalDateTime orderRequestedAt,
            final String description) {
        Assert.hasText(title, "입고 제목은 필수입니다.");
        Assert.notNull(estimatedArrivalAt, "입고 예정일은 필수입니다.");
        Assert.notNull(orderRequestedAt, "주문 요청일은 필수입니다.");
        Assert.notNull(inboundProducts, "입고 상품은 필수입니다.");
        this.title = title;
        this.estimatedArrivalAt = estimatedArrivalAt;
        this.orderRequestedAt = orderRequestedAt;
        this.description = description;
        status = InboundStatus.ORDER_REQUESTED;
    }

    public void assignProducts(final List<InboundProduct> products) {
        Assert.notEmpty(products, "입고 상품은 필수입니다.");
        for (final InboundProduct product : products) {
            product.assignInbound(this);
            inboundProducts.add(product);
        }
    }
}
