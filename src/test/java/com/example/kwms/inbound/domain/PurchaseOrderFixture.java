package com.example.kwms.inbound.domain;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.kwms.inbound.domain.PurchaseOrderProductFixture.aPurchaseOrderProduct;

public class PurchaseOrderFixture {

    private final Long purchaseOrderNo = 1L;
    private String title = "입고 제목";
    private String description = "입고 설명";
    private List<PurchaseOrderProductFixture> purchaseOrderProducts = List.of(aPurchaseOrderProduct());

    public static PurchaseOrderFixture aPurchaseOrder() {
        return new PurchaseOrderFixture();
    }

    public PurchaseOrderFixture title(final String title) {
        this.title = title;
        return this;
    }

    public PurchaseOrderFixture description(final String description) {
        this.description = description;
        return this;
    }

    public PurchaseOrderFixture inboundProducts(final PurchaseOrderProductFixture... inboundProducts) {
        purchaseOrderProducts = Arrays.asList(inboundProducts);
        return this;
    }

    public PurchaseOrder build() {
        return new PurchaseOrder(
                purchaseOrderNo,
                title,
                description,
                buildInboundProducts());
    }

    private List<PurchaseOrderProduct> buildInboundProducts() {
        return purchaseOrderProducts.stream()
                .map(PurchaseOrderProductFixture::build)
                .collect(Collectors.toList());
    }
}
