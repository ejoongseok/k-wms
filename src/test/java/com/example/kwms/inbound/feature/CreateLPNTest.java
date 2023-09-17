package com.example.kwms.inbound.feature;

import com.example.kwms.common.ApiTest;
import com.example.kwms.common.Scenario;
import com.example.kwms.inbound.domain.PurchaseOrder;
import com.example.kwms.inbound.domain.PurchaseOrderProduct;
import com.example.kwms.inbound.domain.PurchaseOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateLPNTest extends ApiTest {


    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @BeforeEach
    void createLPNSetUp() {
        Scenario
                .createPurchaseOrder().request()
                .addReceive().request();
    }

    @Test
    @DisplayName("검수한 상품의 LPN을 생성한다.")
    @Transactional
    void createLPN() {
        Scenario.createLPN().request();

        final PurchaseOrder purchaseOrder = purchaseOrderRepository.getBy(1L);
        final PurchaseOrderProduct purchaseOrderProduct = purchaseOrder.getPurchaseOrderProducts().get(0);
        assertThat(purchaseOrderProduct.getLpns()).isNotEmpty();
    }

}
