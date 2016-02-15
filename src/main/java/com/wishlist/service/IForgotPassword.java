package com.wishlist.service;

import com.wishlist.bean.ForgotPasswordBean;

public interface IForgotPassword {

    void restorePassword(String email);
    boolean confirmPassword(String token, String newPassword);
    ForgotPasswordBean getByToken(String token);
    boolean isTokenAlive(String token);
}
