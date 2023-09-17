package com.example.kwms.inbound.feature;

import com.example.kwms.common.ApiTest;
import com.example.kwms.common.Scenario;
import com.example.kwms.inbound.domain.PurchaseOrder;
import com.example.kwms.inbound.domain.PurchaseOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class UpdatePurchaseOrderTest extends ApiTest {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @BeforeEach
    void updateInboundSetUp() {
        Scenario
                .createPurchaseOrder().request();
    }

    @Test
    @DisplayName("발주 수정")
    void updatePurchaseOrder() {
        final PurchaseOrder before = purchaseOrderRepository.getBy(1L);
        assertThat(before.getTitle()).isEqualTo("블랙핑크 앨범 입고");

        Scenario
                .updatePurchaseOrder().request();

        final PurchaseOrder after = purchaseOrderRepository.getBy(1L);
        assertThat(after.getTitle()).isEqualTo("블랙핑크 앨범 수정");
    }

}
