package com.example.kwms.common.auth;

import org.springframework.stereotype.Component;

@Component
public class UserNoTokenProvider {
    public UserNoToken parseToken(final String authorization) {
        // TODO : parse token
        return UserNoToken.of(1L);
    }
}
