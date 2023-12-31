package com.example.kwms.location.domain;

import lombok.Getter;

@Getter
public enum UsagePurpose {
    MOVE("이동"),
    STACK("적치"),
    FILL("보충"),
    DISPLAY("진열"),
    DEFECTIVE("불량"),

    WAREHOUSE_TRANSFER("창고간 이동");


    private final String description;

    UsagePurpose(final String description) {
        this.description = description;
    }

    public static UsagePurpose from(final String usagePurpose) {
        return valueOf(usagePurpose);
    }
}
