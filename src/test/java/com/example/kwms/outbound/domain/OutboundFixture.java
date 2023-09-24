package com.example.kwms.outbound.domain;

import com.example.kwms.location.domain.LocationFixture;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.kwms.location.domain.LocationFixture.aLocation;
import static com.example.kwms.outbound.domain.OutboundProductFixture.anOutboundProduct;

public class OutboundFixture {
    private Long outboundNo = 1L;
    private Long orderNo = 1L;
    private Long warehouseNo = 1L;
    private boolean isPriorityDelivery;
    private LocalDate desiredDeliveryAt = LocalDate.now();
    private List<OutboundProductFixture> outboundProducts = List.of(anOutboundProduct());
    private PackagingMaterialFixture packagingMaterial;
    private LocationFixture pickingTote = aLocation();
    private Long pickerNo = 1L;

    public static OutboundFixture anOutbound() {
        return new OutboundFixture();
    }

    public OutboundFixture outboundNo(final Long outboundNo) {
        this.outboundNo = outboundNo;
        return this;
    }

    public OutboundFixture orderNo(final Long orderNo) {
        this.orderNo = orderNo;
        return this;
    }

    public OutboundFixture warehouseNo(final Long warehouseNo) {
        this.warehouseNo = warehouseNo;
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

    public OutboundFixture pickingTote(final LocationFixture pickingTote) {
        this.pickingTote = pickingTote;
        return this;
    }

    public OutboundFixture pickerNo(final Long pickerNo) {
        this.pickerNo = pickerNo;
        return this;
    }


    public Outbound build() {
        return new Outbound(
                outboundNo,
                warehouseNo,
                orderNo,
                buildOutboundProducts(),
                isPriorityDelivery,
                desiredDeliveryAt,
                buildPackagingMaterial(),
                null == pickingTote ? null : pickingTote.build(),
                null == pickerNo ? null : pickerNo
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