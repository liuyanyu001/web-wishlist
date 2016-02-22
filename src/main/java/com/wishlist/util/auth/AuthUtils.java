package com.wishlist.util.auth;

import java.io.IOException;
import java.text.ParseException;
import java.util.concurrent.TimeUnit;

import com.nimbusds.jose.crypto.MACVerifier;
import com.wishlist.model.User;
import com.wishlist.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;


import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.ReadOnlyJWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthUtils {

    private static final JWSHeader JWT_HEADER = new JWSHeader(JWSAlgorithm.HS256);
    private static final String TOKEN_SECRET = "Bearer";
    public static final String AUTH_HEADER_KEY = "Authorization";

    private static final String AUTH_ERROR_MSG = "Please make sure your request has an Authorization header",
            EXPIRE_ERROR_MSG = "Token has expired",
            JWT_ERROR_MSG = "Unable to parse JWT",
            JWT_INVALID_MSG = "Invalid JWT token";

    public static final int TOKEN_ALIVE_DAYS = 1;

    @Autowired
    private IUserService userService;

    @Autowired
    private SecurityUtil securityUtil;


    public boolean isCurrentUserAuth() {
        return securityUtil.isCurrentUserAuth();
    }

    public static String getSubject(String authHeader) throws ParseException, JOSEException {
        return decodeToken(authHeader).getSubject();
    }

    public static ReadOnlyJWTClaimsSet decodeToken(String authHeader) throws ParseException, JOSEException {
        SignedJWT signedJWT = SignedJWT.parse(getSerializedToken(authHeader));
        if (signedJWT.verify(new MACVerifier(TOKEN_SECRET))) {
            return signedJWT.getJWTClaimsSet();
        } else {
            throw new JOSEException("Signature verification failed");
        }
    }

    public static Token createToken(String host, User user) throws JOSEException {
        JWTClaimsSet claim = new JWTClaimsSet();
        claim.setSubject(user.getId());
        claim.setIssuer(host);
        claim.setIssueTime(DateTime.now().toDate());
        claim.setExpirationTime(DateTime.now().plusDays(TOKEN_ALIVE_DAYS).toDate());

        JWSSigner signer = new MACSigner(TOKEN_SECRET);
        SignedJWT jwt = new SignedJWT(JWT_HEADER, claim);
        jwt.sign(signer);

        return new Token(jwt.serialize());
    }

    public User parseUserFromToken(String token) {
        String username = null;
        try {
            ReadOnlyJWTClaimsSet set = AuthUtils.decodeToken(token);
            username = set.getSubject();
        } catch (ParseException | JOSEException e) {
            e.printStackTrace();
        } finally {
            return userService.getById(username);
        }
    }

    public void checkAuthHeader(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException {
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
                    User user = parseUserFromToken(authHeader);
                    SecurityContextHolder.getContext().setAuthentication(new UserAuthentication(user));

                    if (isCurrentUserAuth()) {
                        httpRequest.getSession().setAttribute("userBean", user);
                        httpRequest.getSession().setMaxInactiveInterval((int) TimeUnit.HOURS.toSeconds(AuthUtils.TOKEN_ALIVE_DAYS));
                    }
                }
            } catch (ParseException | JOSEException e) {
                httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, JWT_ERROR_MSG);
            }
        }
    }

    public static String getSerializedToken(String authHeader) {
        return authHeader.split(" ")[1];
    }
}
