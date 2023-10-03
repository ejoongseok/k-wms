package com.example.kwms.common.auth;

import lombok.Getter;

public class UserNoToken {
    @Getter(lombok.AccessLevel.PACKAGE)
    private final Long userNo;

    private UserNoToken(final Long userNo) {
        this.userNo = userNo;
    }

    public static UserNoToken of(final long userNo) {
        return new UserNoToken(userNo);
    }

    public boolean isEmpty() {
        return null == userNo;
    }

}
