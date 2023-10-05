package com.example.kwms.inbound.domain;

import com.example.kwms.common.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static com.example.kwms.inbound.domain.LPNFixture.aLPN;
import static com.example.kwms.inbound.domain.PurchaseOrderFixture.aPurchaseOrder;
import static com.example.kwms.inbound.domain.PurchaseOrderProductFixture.aPurchaseOrderProduct;
import static com.example.kwms.inbound.domain.ReceiveFixture.aReceive;
import static com.example.kwms.inbound.domain.ReceiveProductFixture.aReceiveProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PurchaseOrderTest {

    @Test
    @DisplayName("발주 수정")
    void updatePurchaseOrder() {
        final PurchaseOrder purchaseOrder = aPurchaseOrder()
                .title("발주 제목")
                .description("발주 설명")
                .purchaseOrderProducts(
                        aPurchaseOrderProduct().productNo(1L),
                        aPurchaseOrderProduct().productNo(2L))
                .build();

        purchaseOrder.updatePurchaseOrder(
                1L,
                "수정된 발주 제목",
                "수정된 발주 설명",
                Arrays.asList(
                        aPurchaseOrderProduct().productNo(1L).build(),
                        aPurchaseOrderProduct().productNo(2L).build()));

        assertThat(purchaseOrder.getTitle()).isEqualTo("수정된 발주 제목");
        assertThat(purchaseOrder.getDescription()).isEqualTo("수정된 발주 설명");
    }

    @Test
    @DisplayName("발주 수정 - 발주 상품이 없는 경우 예외 발생")
    void fail_updatePurchaseOrder() {
        final PurchaseOrder purchaseOrder = aPurchaseOrder()
                .title("발주 제목")
                .description("발주 설명")
                .purchaseOrderProducts(
                        aPurchaseOrderProduct().productNo(1L),
                        aPurchaseOrderProduct().productNo(2L))
                .build();

        assertThatThrownBy(() -> {
            purchaseOrder.updatePurchaseOrder(
                    1L,
                    "title",
                    "description",
                    Collections.emptyList()
            );
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("발주 상품은 필수입니다.");
    }

    @Test
    @DisplayName("발주 수정 - 중복된 발주 상품이 있는 경우 예외 발생")
    void fail_updatePurchaseOrder2() {
        final PurchaseOrder purchaseOrder = aPurchaseOrder()
                .title("발주 제목")
                .description("발주 설명")
                .purchaseOrderProducts(
                        aPurchaseOrderProduct().productNo(1L),
                        aPurchaseOrderProduct().productNo(2L))
                .build();

        assertThatThrownBy(() -> {
            purchaseOrder.updatePurchaseOrder(
                    1L,
                    "title",
                    "description",
                    Arrays.asList(
                            aPurchaseOrderProduct().productNo(1L).build(),
                            aPurchaseOrderProduct().productNo(1L).build()));
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("중복된 발주 상품이 존재합니다.");
    }

    @Test
    @DisplayName("발주 수정 - 이미 입고된 상품이 있는 경우 예외 발생")
    void fail_updatePurchaseOrder3() {
        final PurchaseOrder purchaseOrder = aPurchaseOrder()
                .title("발주 제목")
                .description("발주 설명")
                .purchaseOrderProducts(
                        aPurchaseOrderProduct().productNo(1L),
                        aPurchaseOrderProduct().productNo(2L))
                .receives(aReceive())
                .build();

        assertThatThrownBy(() -> {
            purchaseOrder.updatePurchaseOrder(
                    1L,
                    "title",
                    "description",
                    Arrays.asList(
                            aPurchaseOrderProduct().productNo(1L).build(),
                            aPurchaseOrderProduct().productNo(1L).build()));
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이미 입고된 상품이 있으면 수정이 불가능합니다.");
    }

    @Test
    @DisplayName("발주 상품 입고 추가")
    void addReceive() {
        final PurchaseOrder purchaseOrder = aPurchaseOrder().build();

        purchaseOrder.addReceive(aReceive().build());

        assertThat(purchaseOrder.getReceives()).hasSize(1);
    }

    @Test
    @DisplayName("발주 상품 입고 추가 - 발주한 수량을 초과하는 경우 예외 발생")
    void fail_addReceive() {
        final PurchaseOrder purchaseOrder = aPurchaseOrder()
                .purchaseOrderProducts(
                        aPurchaseOrderProduct().productNo(1L).requestQuantity(1L))
                .build();

        assertThatThrownBy(() -> {
            purchaseOrder.addReceive(aReceive().receiveProducts(aReceiveProduct().productNo(1L).acceptableQuantity(1L)).build());
            purchaseOrder.addReceive(aReceive().receiveProducts(aReceiveProduct().productNo(1L).acceptableQuantity(1L)).build());
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이미 발주한 상품 모두 입고되었습니다.");
    }

    @Test
    @DisplayName("발주 상품 입고 추가 - 발주한 수량을 초과하는 경우 예외 발생")
    void fail_addReceive2() {
        final PurchaseOrder purchaseOrder = aPurchaseOrder()
                .purchaseOrderProducts(
                        aPurchaseOrderProduct().productNo(1L).requestQuantity(1L))
                .build();

        assertThatThrownBy(() -> {
            purchaseOrder.addReceive(aReceive().receiveProducts(aReceiveProduct().productNo(1L).acceptableQuantity(2L)).build());
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("발주 수량을 초과하여 입고할 수 없습니다.");
    }

    @Test
    @DisplayName("발주 상품 입고 추가 - 발주한 수량을 초과하는 경우 예외 발생")
    void fail_addReceive3() {
        final PurchaseOrder purchaseOrder = aPurchaseOrder()
                .purchaseOrderProducts(
                        aPurchaseOrderProduct().productNo(1L).requestQuantity(1L))
                .build();

        assertThatThrownBy(() -> {
            purchaseOrder.addReceive(aReceive().receiveProducts(aReceiveProduct().productNo(2L)).build());
        }).isInstanceOf(NotFoundException.class)
                .hasMessageContaining("상품 번호에 해당하는 발주 상품이 존재하지 않습니다.");
    }

    @Test
    @DisplayName("LPN 추가")
    public void addLPN() {
        final PurchaseOrder purchaseOrder = aPurchaseOrder().build();
        final Long purchaseOrderProductNo = 1L;

        purchaseOrder.addLPN(purchaseOrderProductNo, aLPN().build());

        assertThat(purchaseOrder.getPurchaseOrderProducts().get(0).getLpns()).hasSize(1);
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