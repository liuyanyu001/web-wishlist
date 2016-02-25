package com.wishlist.bean.auth;

import com.wishlist.model.ForgotPassword;
import com.wishlist.util.DateUtil;

import java.time.LocalDateTime;

public class ForgotPasswordBean {

    private String token;
    private String email;
    private LocalDateTime date;

    public ForgotPasswordBean(){}

    public ForgotPasswordBean(ForgotPassword forgotPassword){
        this.token = forgotPassword.getToken();
        this.email = forgotPassword.getUser().getEmail();
        this.date = DateUtil.fromMillis(forgotPassword.getDate());
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
