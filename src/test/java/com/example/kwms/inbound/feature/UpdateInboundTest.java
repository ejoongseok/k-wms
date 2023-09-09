package com.example.kwms.inbound.feature;

import com.example.kwms.common.ApiTest;
import com.example.kwms.common.Scenario;
import com.example.kwms.inbound.domain.Inbound;
import com.example.kwms.inbound.domain.InboundRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class UpdateInboundTest extends ApiTest {

    @Autowired
    private InboundRepository inboundRepository;

    @BeforeEach
    void updateInboundSetUp() {
        Scenario
                .createInbound().request();
    }

    @Test
    @DisplayName("입고 생성")
    void createInbound() {
        final Inbound before = inboundRepository.getBy(1L);
        assertThat(before.getTitle()).isEqualTo("블랙핑크 앨범 입고");

        Scenario
                .updateInbound().request();

        final Inbound after = inboundRepository.getBy(1L);
        assertThat(after.getTitle()).isEqualTo("블랙핑크 앨범 수정");
    }

}
