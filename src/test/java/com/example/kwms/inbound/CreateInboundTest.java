package com.example.kwms.inbound;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CreateInboundTest {

    private CreateInbound createInbound;

    @BeforeEach
    void setUp() {
        createInbound = new CreateInbound();
    }

    @Test
    @DisplayName("입고 생성")
    void createInbound() {
        createInbound.request();
    }

    private class CreateInbound {
        public void request() {
            throw new UnsupportedOperationException("Unsupported request");
        }
    }
}
