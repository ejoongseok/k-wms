package com.example.kwms.location.domain;

public enum StorageType {
    TOTE("토트 바구니"),
    PALLET("파레트"),
    RACK("랙"),
    SHELF("선반"),
    BIN("바구니(셀)"),
    ;


    private final String description;

    StorageType(final String description) {
        this.description = description;
    }
}
