package com.example.kwms.inbound.domain;

public enum InboundStatus {
    ORDER_REQUESTED("발주 요청");

    private final String description;

    InboundStatus(final String description) {
        this.description = description;
    }
}
