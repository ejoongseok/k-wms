package com.example.kwms.inbound.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.kwms.inbound.domain.PurchaseOrderProductFixture.aPurchaseOrderProduct;

public class PurchaseOrderFixture {

    private Long purchaseOrderNo = 1L;
    private Long warehouseNo = 1L;
    private String title = "입고 제목";
    private String description = "입고 설명";
    private List<PurchaseOrderProductFixture> purchaseOrderProducts = List.of(aPurchaseOrderProduct());
    private List<ReceiveFixture> receives = new ArrayList<>();

    public static PurchaseOrderFixture aPurchaseOrder() {
        return new PurchaseOrderFixture();
    }

    public PurchaseOrderFixture purchaseOrderNo(final Long purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo;
        return this;
    }

    public PurchaseOrderFixture warehouseNo(final Long warehouseNo) {
        this.warehouseNo = warehouseNo;
        return this;
    }

    public PurchaseOrderFixture title(final String title) {
        this.title = title;
        return this;
    }

    public PurchaseOrderFixture description(final String description) {
        this.description = description;
        return this;
    }

    public PurchaseOrderFixture purchaseOrderProducts(final PurchaseOrderProductFixture... purchaseOrderProductList) {
        if (null == purchaseOrderProductList) {
            purchaseOrderProducts = Collections.emptyList();
            return this;
        }
        purchaseOrderProducts = Arrays.asList(purchaseOrderProductList);
        return this;
    }

    public PurchaseOrderFixture receives(final ReceiveFixture... receives) {
        this.receives = Arrays.asList(receives);
        return this;
    }

    public PurchaseOrder build() {
        return new PurchaseOrder(
                purchaseOrderNo,
                title,
                description,
                warehouseNo,
                null == purchaseOrderProducts || purchaseOrderProducts.isEmpty() ? new ArrayList<>() : buildInboundProducts(),
                null == receives || receives.isEmpty() ? new ArrayList<>() : buildReceives());
    }

    private List<PurchaseOrderProduct> buildInboundProducts() {
        return purchaseOrderProducts.stream()
                .map(PurchaseOrderProductFixture::build)
                .collect(Collectors.toList());
    }

    private List<Receive> buildReceives() {
        return receives.stream()
                .map(ReceiveFixture::build)
                .collect(Collectors.toList());
    }
}
