package com.example.kwms.inbound.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.kwms.inbound.domain.PurchaseOrderFixture.aPurchaseOrder;
import static org.assertj.core.api.Assertions.assertThat;

class PurchaseOrderTest {

    @Test
    @DisplayName("입고 상품 검수 결과 등록")
    void registerInspectionResult() {
        final PurchaseOrder purchaseOrder = aPurchaseOrder().build();
        assertThat(purchaseOrder.getReceives()).hasSize(0);

        final LocalDateTime inspectedAt = LocalDateTime.now();
        final String inspectionComment = "검수 결과 코멘트";
        final long acceptableQuantity = 1L;
        final long rejectedQuantity = 0L;
        final long inboundProductNo = 1L;
        final Receive receive = new Receive(
                "입고명",
                List.of(
                        new ReceiveProduct(
                                inboundProductNo,
                                acceptableQuantity,
                                rejectedQuantity,
                                inspectionComment,
                                inspectedAt)
                )
        );
        purchaseOrder.addReceive(receive);

        assertThat(purchaseOrder.getReceives()).hasSize(1);
    }


    @Test
    @DisplayName("LPN 바코드 할당")
    public void assignLPN() {
        final PurchaseOrder purchaseOrder = aPurchaseOrder().build();
        final List<PurchaseOrderProduct> purchaseOrderProducts = purchaseOrder.getPurchaseOrderProducts();
        final PurchaseOrderProduct purchaseOrderProduct = purchaseOrderProducts.get(0);
        assertThat(purchaseOrderProduct.getLpns()).isEmpty();

        final String lpnBarcode = "1234567890";
        final LocalDateTime expiringAt = LocalDateTime.now().plusDays(30);
        final long inboundProductNo = 1L;
        purchaseOrder.assignLPN(
                inboundProductNo,
                lpnBarcode,
                expiringAt
        );

        assertThat(purchaseOrderProduct.getLpns()).isNotEmpty();
    }

}