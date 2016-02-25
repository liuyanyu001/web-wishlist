package com.wishlist.util.auth;

import com.wishlist.bean.auth.ForgotPasswordBean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class SecurityUtil {

    public boolean isCurrentUserAuth(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal.equals("anonymousUser")) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isTokenExpired(ForgotPasswordBean forgotPasswordBean){
        LocalDateTime expiredDate = forgotPasswordBean.getDate().plus(2, ChronoUnit.HOURS);
        return LocalDateTime.now().isAfter(expiredDate);
    }
}
