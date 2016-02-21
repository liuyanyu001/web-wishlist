/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wishlist.conf.filter;


import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import com.wishlist.model.User;
import com.wishlist.util.auth.AuthUtils;
import com.wishlist.util.auth.UserAuthentication;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.concurrent.TimeUnit;

//@Component
public class AuthFilter implements Filter {

    private static final String AUTH_ERROR_MSG = "Please make sure your request has an Authorization header",
            EXPIRE_ERROR_MSG = "Token has expired",
            JWT_ERROR_MSG = "Unable to parse JWT",
            JWT_INVALID_MSG = "Invalid JWT token";

    AuthUtils authUtils;

    public AuthFilter(AuthUtils authUtils) {
        this.authUtils = authUtils;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {


        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String authHeader = httpRequest.getHeader(AuthUtils.AUTH_HEADER_KEY);


        if (StringUtils.isBlank(authHeader) || authHeader.split(" ").length != 2) {
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, AUTH_ERROR_MSG);
        } else {
            JWTClaimsSet claimSet = null;
            try {
                claimSet = (JWTClaimsSet) AuthUtils.decodeToken(authHeader);
                // ensure that the token is not expired
                if (new DateTime(claimSet.getExpirationTime()).isBefore(DateTime.now())) {
                    httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, EXPIRE_ERROR_MSG);
                } else {
                    User user = authUtils.parseUserFromToken(authHeader);
                    SecurityContextHolder.getContext().setAuthentication(new UserAuthentication(user));

                    if (authUtils.isCurrentUserAuth()) {
                        httpRequest.getSession().setAttribute("userBean", user);
                        httpRequest.getSession().setMaxInactiveInterval((int) TimeUnit.HOURS.toSeconds(AuthUtils.TOKEN_ALIVE_DAYS));
                    }

                    chain.doFilter(request, response);
                }
            } catch (ParseException e) {
                httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, JWT_ERROR_MSG);
            } catch (JOSEException e) {
                httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, JWT_INVALID_MSG);
            }
        }
    }

    @Override
    public void destroy() { /* unused */ }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException { /* unused */ }

}
