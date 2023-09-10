package com.example.kwms.inbound.domain;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.kwms.inbound.domain.InboundProductFixture.aInboundProduct;

public class InboundFixture {

    private final Long inboundNo = 1L;
    private String title = "입고 제목";
    private LocalDateTime estimatedArrivalAt = LocalDateTime.now().plusDays(1L);
    private LocalDateTime orderRequestedAt = LocalDateTime.now();
    private String description = "입고 설명";
    private List<InboundProductFixture> inboundProducts = List.of(aInboundProduct());

    public static InboundFixture aInbound() {
        return new InboundFixture();
    }

    public InboundFixture title(final String title) {
        this.title = title;
        return this;
    }

    public InboundFixture estimatedArrivalAt(final LocalDateTime estimatedArrivalAt) {
        this.estimatedArrivalAt = estimatedArrivalAt;
        return this;
    }

    public InboundFixture orderRequestedAt(final LocalDateTime orderRequestedAt) {
        this.orderRequestedAt = orderRequestedAt;
        return this;
    }

    public InboundFixture description(final String description) {
        this.description = description;
        return this;
    }

    public InboundFixture inboundProducts(final InboundProductFixture... inboundProducts) {
        this.inboundProducts = Arrays.asList(inboundProducts);
        return this;
    }

    public Inbound build() {
        return new Inbound(
                inboundNo,
                title,
                estimatedArrivalAt,
                orderRequestedAt,
                description,
                buildInboundProducts());
    }

    private List<InboundProduct> buildInboundProducts() {
        return inboundProducts.stream()
                .map(InboundProductFixture::build)
                .collect(Collectors.toList());
    }
}
