package com.example.kwms.inbound.feature.query;

import com.example.kwms.inbound.domain.PurchaseOrder;

public record PurchaseOrderPresenter(Long purchaseOrderProductNo, PurchaseOrder purchaseOrder) {
}