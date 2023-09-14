package com.example.kwms.common.auth;

import org.springframework.stereotype.Component;

@Component
public class UserIdTokenProvider {
    public UserIdToken parseToken(final String authorization) {
        // TODO : parse token
        return UserIdToken.of(1L);
    }
}
