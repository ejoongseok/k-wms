package com.example.kwms.inbound.feature;

import com.example.kwms.common.ApiTest;
import com.example.kwms.common.Scenario;
import com.example.kwms.inbound.domain.PurchaseOrder;
import com.example.kwms.inbound.domain.PurchaseOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

public class AddReceiveTest extends ApiTest {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @BeforeEach
    void addReceiveSetUp() {
        Scenario.createPurchaseOrder().request();
    }

    @Test
    @DisplayName("입고 등록")
    @Transactional
    void addReceive() {
        Scenario.
                addReceive().request();

        final PurchaseOrder purchaseOrder = purchaseOrderRepository.getBy(1L);
        assertThat(purchaseOrder.getReceives()).hasSize(1);
    }

}
