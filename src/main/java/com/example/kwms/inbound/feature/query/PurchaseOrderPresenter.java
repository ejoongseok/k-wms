package com.example.kwms.inbound.feature.query;

import com.example.kwms.common.NotFoundException;
import com.example.kwms.inbound.domain.LPN;
import com.example.kwms.inbound.domain.PurchaseOrder;
import com.example.kwms.inbound.domain.PurchaseOrderProduct;
import com.example.kwms.inbound.domain.Receive;
import com.example.kwms.inbound.domain.ReceiveProduct;

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

    String getPurchaseOrderStatus() {
        if (isAllReceived()) {
            return "입고 완료";
        } else if (hasReceivedList()) {
            return "입고 중";
        }
        return "발주";
    }

    private boolean hasReceivedList() {
        return !purchaseOrder.getReceives().isEmpty();
    }

    private boolean isAllReceived() {
        final List<PurchaseOrderProduct> purchaseOrderProducts = purchaseOrder.getPurchaseOrderProducts();
        final long totalRequestedQuantity = purchaseOrderProducts.stream()
                .mapToLong(PurchaseOrderProduct::getRequestQuantity)
                .sum();
        final List<Receive> receives = purchaseOrder.getReceives();
        final long totalReceivedQuantity = receives.stream()
                .flatMap(r -> r.getReceiveProducts().stream())
                .mapToLong(ReceiveProduct::totalQuantity)
                .sum();
        return totalRequestedQuantity == totalReceivedQuantity;
    }
}