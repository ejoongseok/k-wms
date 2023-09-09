package com.example.kwms.inbound.feature;

import com.example.kwms.common.ApiTest;
import com.example.kwms.inbound.domain.InboundRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CreateInboundTest extends ApiTest {

    @Autowired
    private CreateInbound createInbound;
    @Autowired
    private InboundRepository inboundRepository;

    @Test
    @DisplayName("입고 생성")
    void createInbound() {
        final CreateInbound.Request.Product product = new CreateInbound.Request.Product(
                1L,
                2_000L,
                15_000L,
                "블랙핑크 3집 앨범[]"
        );

        final CreateInbound.Request request = new CreateInbound.Request(
                "블랙핑크 앨범 입고",
                LocalDateTime.now().plusDays(1L),
                LocalDateTime.now(),
                "23년도 블랙핑크 신규 앨범 주문",
                List.of(product));


        createInbound.request(request);

        assertThat(inboundRepository.findAll()).hasSize(1);
    }

}
