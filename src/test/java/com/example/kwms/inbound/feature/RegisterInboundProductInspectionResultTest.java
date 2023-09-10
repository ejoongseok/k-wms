package com.example.kwms.inbound.feature;

import com.example.kwms.common.ApiTest;
import com.example.kwms.common.Scenario;
import com.example.kwms.inbound.domain.Inbound;
import com.example.kwms.inbound.domain.InboundProduct;
import com.example.kwms.inbound.domain.InboundRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

public class RegisterInboundProductInspectionResultTest extends ApiTest {

    @Autowired
    private InboundRepository inboundRepository;

    @BeforeEach
    void inspectInboundProductSetUp() {
        Scenario.createInbound().request();
    }

    @Test
    @DisplayName("입고 상품 검수")
    @Transactional
    void inspectInboundProduct() {
        Scenario.
                registerInboundProductInspectionResult().request();

        final Inbound inbound = inboundRepository.getBy(1L);
        final InboundProduct inboundProduct = inbound.getInboundProducts().get(0);
        assertThat(inboundProduct.getInspectedAt()).isNotNull();
    }

}
