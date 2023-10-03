package com.example.kwms.common.auth;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserNoLoader {
    Long getUserId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            throw new IllegalArgumentException("anonymous user");
        }

        final UserNoToken userNoToken = (UserNoToken) authentication.getPrincipal();
        return userNoToken.getUserNo();
    }
}
