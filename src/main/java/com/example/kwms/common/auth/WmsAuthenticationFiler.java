package com.example.kwms.common.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Set;

@RequiredArgsConstructor
public class WmsAuthenticationFiler extends OncePerRequestFilter {
    private final UserNoTokenProvider userNoTokenProvider;

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {
        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        final UserNoToken userNoToken = userNoTokenProvider.parseToken(authorization);
        if (userNoToken.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        final Authentication authentication = createAuthentication(userNoToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    private Authentication createAuthentication(final UserNoToken userNoToken) {
        return new UsernamePasswordAuthenticationToken(userNoToken, null, Set.of(() -> "ROLE_USER"));
    }
}
