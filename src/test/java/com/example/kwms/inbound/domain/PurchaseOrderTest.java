package com.example.kwms.inbound.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.kwms.inbound.domain.LPNFixture.aLPN;
import static com.example.kwms.inbound.domain.PurchaseOrderFixture.aPurchaseOrder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
    @DisplayName("LPN 추가")
    public void addLPN() {
        final PurchaseOrder purchaseOrder = aPurchaseOrder().build();
        assertThat(purchaseOrder.getPurchaseOrderProducts().get(0).getLpns()).isEmpty();

        purchaseOrder.addLPN(1L, aLPN().build());

        assertThat(purchaseOrder.getPurchaseOrderProducts().get(0).getLpns()).isNotEmpty();
    }

    @Test
    @DisplayName("LPN 추가 - 발주 상품에 동일한 LPN 바코드가 이미 존재하는 경우 예외 발생")
    public void fail_addLPN() {
        final PurchaseOrder purchaseOrder = aPurchaseOrder().build();
        assertThat(purchaseOrder.getPurchaseOrderProducts().get(0).getLpns()).isEmpty();

        assertThatThrownBy(() -> {
            purchaseOrder.addLPN(1L, aLPN().build());
            purchaseOrder.addLPN(1L, aLPN().build());
        }).isInstanceOf(AlreadyExistLPNException.class)
                .hasMessageContaining("이미 등록된 LPN 바코드입니다.");
    }

}