package com.example.kwms.common.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class LoginUserNoArgumentResolver implements HandlerMethodArgumentResolver {
    private final UserNoLoader userNoLoader;

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return null != parameter.getParameterAnnotation(LoginUserNo.class)
                && parameter.getParameterType().equals(Long.class);
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer, final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) throws Exception {
        return userNoLoader.getUserId();
    }
}
