package com.wishlist.service;

import com.wishlist.bean.RegistrationFields;
import com.wishlist.model.User;

/**
 * Created by root on 13.02.2016.
 */
public interface IUserService {
    void create(String email, String password, String fName, String lName) throws Exception;
    void create(RegistrationFields registrationFields) throws Exception;
    User getByEmail(String email);
    void clearAll();
    User getByName(String name);
}
