package com.example.kwms.inbound.feature.query;

import com.example.kwms.common.NotFoundException;
import com.example.kwms.inbound.domain.LPN;
import com.example.kwms.inbound.domain.PurchaseOrder;
import com.example.kwms.inbound.domain.PurchaseOrderProduct;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Accessors(fluent = true)
public final class PurchaseOrderPresenter {
    private final Long purchaseOrderProductNo;
    private final PurchaseOrder purchaseOrder;

    PurchaseOrderPresenter(final Long purchaseOrderProductNo, final PurchaseOrder purchaseOrder) {
        this.purchaseOrderProductNo = purchaseOrderProductNo;
        this.purchaseOrder = purchaseOrder;
    }

    List<LPN> getLPNs() {
        final List<PurchaseOrderProduct> purchaseOrderProducts = purchaseOrder().getPurchaseOrderProducts();
        final PurchaseOrderProduct purchaseOrderProduct = purchaseOrderProducts.stream()
                .filter(product -> product.getProductNo().equals(purchaseOrderProductNo()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(
                        "발주 상품 번호에 해당하는 발주 상품이 존재하지 않습니다. 상품 번호: %s".formatted(purchaseOrderProductNo())));

        return purchaseOrderProduct.getLpns();
    }
}