package com.example.kwms.common.auth;

public class UserIdToken {
    private final Long userId;

    public UserIdToken(final Long userId) {
        this.userId = userId;
    }

    public static UserIdToken of(final long userId) {
        return new UserIdToken(userId);
    }

    public boolean isEmpty() {
        return null == userId;
    }

    public Long getUserId() {
        return userId;
    }
}
