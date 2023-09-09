package com.example.kwms.inbound.feature;

import com.example.kwms.common.ApiTest;
import com.example.kwms.common.Scenario;
import com.example.kwms.inbound.domain.Inbound;
import com.example.kwms.inbound.domain.InboundRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

class AddInboundProductTest extends ApiTest {

    @Autowired
    private InboundRepository inboundRepository;

    @Test
    @DisplayName("입고 상품을 추가한다.")
    @Transactional
    void appendInboundProduct() {
        Scenario.createInbound().request()
                .addInboundProduct().request();

        final Inbound inbound = inboundRepository.findById(1L).get();
        assertThat(inbound.getInboundProducts()).hasSize(2);
    }

}
