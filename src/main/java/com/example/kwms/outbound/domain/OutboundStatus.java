package com.example.kwms.outbound.domain;

public enum OutboundStatus {
    READY("출고 대기"),
    ALLOCATED_PICKING_TOTE("피킹 토트 할당됨"),
    PICKING_COMPLETED("피킹 완료됨"),
    PACKAGING_COMPLETED("포장 완료됨"),
    SHIPPED("출고 완료됨"),
    CANCELLED("출고 취소됨");


    private final String description;

    OutboundStatus(final String description) {
        this.description = description;
    }
}
