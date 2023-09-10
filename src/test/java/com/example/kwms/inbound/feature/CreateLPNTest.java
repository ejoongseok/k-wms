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

public class CreateLPNTest extends ApiTest {


    @Autowired
    private InboundRepository inboundRepository;

    @BeforeEach
    void createLPNSetUp() {
        Scenario
                .createInbound().request()
                .registerInboundProductInspectionResult().request();
    }

    @Test
    @DisplayName("검수한 상품의 LPN을 생성한다.")
    @Transactional
    void createLPN() {
        Scenario.createLPN().request();

        final Inbound inbound = inboundRepository.getBy(1L);
        final InboundProduct inboundProduct = inbound.getInboundProducts().get(0);
        assertThat(inboundProduct.getLpns()).isNotEmpty();
    }

}
