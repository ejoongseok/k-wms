package com.example.kwms.inbound.domain;

import java.time.LocalDateTime;

public class LPNFixture {

    private String lpnBarcode = "1234567890";
    private LocalDateTime expiringAt = LocalDateTime.now().plusDays(30);
    private PurchaseOrderProductFixture inboundProduct = new PurchaseOrderProductFixture();

    public static LPNFixture aLPN() {
        return new LPNFixture();
    }

    public LPNFixture lpnBarcode(final String lpnBarcode) {
        this.lpnBarcode = lpnBarcode;
        return this;
    }

    public LPNFixture expiringAt(final LocalDateTime expiringAt) {
        this.expiringAt = expiringAt;
        return this;
    }

    public LPNFixture inboundProduct(final PurchaseOrderProductFixture inboundProduct) {
        this.inboundProduct = inboundProduct;
        return this;
    }

    public LPN build() {
        return new LPN(lpnBarcode, expiringAt, inboundProduct.build());
    }


}
