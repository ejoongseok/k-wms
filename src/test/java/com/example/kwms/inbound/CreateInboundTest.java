package com.example.kwms.inbound;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class CreateInboundTest {

    private CreateInbound createInbound;

    @BeforeEach
    void setUp() {
        createInbound = new CreateInbound();
    }

    @Test
    @DisplayName("입고 생성")
    void createInbound() {
        final String title = "블랙핑크 앨범 입고";
        final LocalDateTime estimatedArrivalAt = LocalDateTime.now().plusDays(1L);
        final LocalDateTime orderRequestedAt = LocalDateTime.now();
        final String description = "23년도 블랙핑크 신규 앨범 주문";

        final CreateInbound.Request request = new CreateInbound.Request(
                title,
                estimatedArrivalAt,
                orderRequestedAt,
                description);

        createInbound.request(request);

        // inboundRepository.findById(1L).asn();
    }

    private class CreateInbound {
        public void request(final Request request) {
            throw new UnsupportedOperationException("Unsupported request");
        }

        public record Request(
                String title,
                LocalDateTime estimatedArrivalAt,
                LocalDateTime orderRequestedAt,
                String description) {
        }
    }
}
