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

class UpdateInboundProductTest extends ApiTest {

    @Autowired
    private InboundRepository inboundRepository;

    @BeforeEach
    void UpdateInboundProductSetUp() {
        Scenario.
                createInbound().request();
    }

    @Test
    @DisplayName("입고 상품을 수정한다.")
    @Transactional
    void updateInboundProduct() {
        Scenario.
                updateInboundProduct().request();


        final Inbound after = inboundRepository.findById(1L).get();
        final InboundProduct afterProduct = after.getInboundProducts().get(0);
        assertThat(afterProduct.getDescription()).isEqualTo("블랙핑크 입고 수정");
    }

}
