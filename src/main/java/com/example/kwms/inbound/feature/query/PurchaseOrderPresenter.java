package com.example.kwms.inbound.feature.query;

import com.example.kwms.common.NotFoundException;
import com.example.kwms.inbound.domain.LPN;
import com.example.kwms.inbound.domain.PurchaseOrder;
import com.example.kwms.inbound.domain.PurchaseOrderProduct;
import com.example.kwms.inbound.domain.Receive;
import com.example.kwms.inbound.domain.ReceiveProduct;

import java.util.List;

public final class PurchaseOrderPresenter {
    private final PurchaseOrder purchaseOrder;

    public PurchaseOrderPresenter(final PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    List<LPN> getLPNs(final Long purchasedOrderProductNo) {
        final PurchaseOrderProduct purchaseOrderProduct = getPurchaseOrderProduct(
                purchasedOrderProductNo);
        return purchaseOrderProduct.getLpns();
    }

    public PurchaseOrderProduct getPurchaseOrderProduct(
            final Long purchasedOrderProductNo) {
        return purchaseOrder.getPurchaseOrderProducts().stream()
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

    public boolean hasReceivedList() {
        return !purchaseOrder.getReceives().isEmpty();
    }

    public boolean isAllReceived() {
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

    public void getReceive(final Long receiveNo) {
        final List<Receive> receives = purchaseOrder.getReceives();
        receives.stream()
                .filter(r -> r.getReceiveNo().equals(receiveNo))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(
                        "입고 번호에 해당하는 입고가 존재하지 않습니다. 입고 번호: %s".formatted(receiveNo)));
    }
}