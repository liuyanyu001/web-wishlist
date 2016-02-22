package com.wishlist.util.auth;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthorizeInterceptor extends HandlerInterceptorAdapter {


    private AuthUtils authUtils;

    public AuthorizeInterceptor(AuthUtils authUtils) {
        this.authUtils = authUtils;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HandlerMethod handlerMethod = (HandlerMethod) handler;

        AuthorizeHeaderRequired headerRequired = handlerMethod.getMethod().getAnnotation(AuthorizeHeaderRequired.class);
        if (headerRequired != null) {
            authUtils.checkAuthHeader(request, response);
        }

        return super.preHandle(request, response, handler);
    }


}
