package com.example.kwms.location.domain;

public enum UsagePurpose {
    MOVE("이동"),
    STACK("적치"),
    FILL("보충"),
    DISPLAY("진열"),
    DEFECTIVE("불량"),

    WAREHOUSE_MOVE("창고간 이동");


    private final String description;

    UsagePurpose(final String description) {
        this.description = description;
    }
}
