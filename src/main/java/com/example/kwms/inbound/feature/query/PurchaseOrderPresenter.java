package com.example.kwms.inbound.feature.query;

import com.example.kwms.common.NotFoundException;
import com.example.kwms.inbound.domain.LPN;
import com.example.kwms.inbound.domain.PurchaseOrder;
import com.example.kwms.inbound.domain.PurchaseOrderProduct;

import java.util.List;

final class PurchaseOrderPresenter {
    private final PurchaseOrder purchaseOrder;

    PurchaseOrderPresenter(final PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    List<LPN> getLPNs(final Long purchasedOrderProductNo) {
        final PurchaseOrderProduct purchaseOrderProduct = getPurchaseOrderProduct(
                purchaseOrder.getPurchaseOrderProducts(),
                purchasedOrderProductNo);
        return purchaseOrderProduct.getLpns();
    }

    private PurchaseOrderProduct getPurchaseOrderProduct(
            final List<PurchaseOrderProduct> purchaseOrderProducts,
            final Long purchasedOrderProductNo) {
        return purchaseOrderProducts.stream()
                .filter(product -> product.getProductNo().equals(purchasedOrderProductNo))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(
                        "발주 상품 번호에 해당하는 발주 상품이 존재하지 않습니다. 상품 번호: %s".formatted(purchasedOrderProductNo)));
    }
}