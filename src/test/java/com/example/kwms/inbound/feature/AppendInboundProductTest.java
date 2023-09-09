package com.example.kwms.inbound.feature;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AppendInboundProductTest {


    private AppendInboundProduct appendInboundProduct;

    @BeforeEach
    void setUp() {
        appendInboundProduct = new AppendInboundProduct();
    }

    @Test
    @DisplayName("입고 상품을 추가한다.")
    void appendInboundProduct() {
        final AppendInboundProduct.Request request = new AppendInboundProduct.Request();
        appendInboundProduct.request(request);
    }

    private class AppendInboundProduct {
        public void request(final Request request) {
            throw new UnsupportedOperationException("Unsupported request");
        }

        public record Request() {
        }
    }
}
