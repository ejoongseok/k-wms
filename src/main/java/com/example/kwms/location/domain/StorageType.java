package com.example.kwms.location.domain;

public enum StorageType {
    BIN("바구니(셀)", 1),
    TOTE("토트 바구니", 1),
    PALLET("파레트", 2),
    SMALL_RACK("소규모 랙", 2),
    SHELF("선반", 3),
    RACK("랙", 4),
    ;

    private final String description;
    private final int size;

    StorageType(final String description, final int size) {
        this.description = description;
        this.size = size;
    }

    int getSize() {
        return size;
    }
}
