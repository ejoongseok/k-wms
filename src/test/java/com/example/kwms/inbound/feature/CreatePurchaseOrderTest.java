package com.example.kwms.inbound.feature;

import com.example.kwms.common.ApiTest;
import com.example.kwms.common.Scenario;
import com.example.kwms.inbound.domain.PurchaseOrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class CreatePurchaseOrderTest extends ApiTest {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Test
    @DisplayName("발주 생성")
    void createInbound() {
        Scenario
                .createPurchaseOrder().request();

        assertThat(purchaseOrderRepository.findAll()).hasSize(1);
    }

}
