package com.example.kwms.inbound.feature.query;

import com.example.kwms.inbound.domain.PurchaseOrder;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public final class PurchaseOrderPresenter {
    private final Long purchaseOrderProductNo;
    private final PurchaseOrder purchaseOrder;

    PurchaseOrderPresenter(final Long purchaseOrderProductNo, final PurchaseOrder purchaseOrder) {
        this.purchaseOrderProductNo = purchaseOrderProductNo;
        this.purchaseOrder = purchaseOrder;
    }

}