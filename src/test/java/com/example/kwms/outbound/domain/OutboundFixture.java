package com.example.kwms.outbound.domain;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.kwms.outbound.domain.OutboundProductFixture.anOutboundProduct;

public class OutboundFixture {
    private Long orderNo = 1L;
    private boolean isPriorityDelivery;
    private LocalDate desiredDeliveryAt = LocalDate.now();
    private List<OutboundProductFixture> outboundProducts = List.of(anOutboundProduct());
    private PackagingMaterialFixture packagingMaterial;

    public static OutboundFixture anOutbound() {
        return new OutboundFixture();
    }

    public OutboundFixture orderNo(final Long orderNo) {
        this.orderNo = orderNo;
        return this;
    }

    public OutboundFixture isPriorityDelivery(final boolean isPriorityDelivery) {
        this.isPriorityDelivery = isPriorityDelivery;
        return this;
    }

    public OutboundFixture desiredDeliveryAt(final LocalDate desiredDeliveryAt) {
        this.desiredDeliveryAt = desiredDeliveryAt;
        return this;
    }

    public OutboundFixture outboundProducts(final OutboundProductFixture... outboundProducts) {
        this.outboundProducts = Arrays.asList(outboundProducts);
        return this;
    }


    public OutboundFixture packagingMaterial(final PackagingMaterialFixture packagingMaterial) {
        this.packagingMaterial = packagingMaterial;
        return this;
    }


    public Outbound build() {
        return new Outbound(
                orderNo,
                buildOutboundProducts(),
                isPriorityDelivery,
                desiredDeliveryAt,
                buildPackagingMaterial()
        );
    }

    private PackagingMaterial buildPackagingMaterial() {
        return null == packagingMaterial ? null : packagingMaterial.build();
    }


    private List<OutboundProduct> buildOutboundProducts() {
        return outboundProducts.stream()
                .map(OutboundProductFixture::build)
                .collect(Collectors.toList());
    }
}