package com.example.kwms.common.auth;

public class UserNoToken {
    private final Long userId;

    public UserNoToken(final Long userId) {
        this.userId = userId;
    }

    public static UserNoToken of(final long userId) {
        return new UserNoToken(userId);
    }

    public boolean isEmpty() {
        return null == userId;
    }

    public Long getUserNo() {
        return userId;
    }
}
