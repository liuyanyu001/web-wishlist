package com.wishlist.service;

import com.wishlist.bean.auth.RegistrationFields;
import com.wishlist.model.User;

/**
 * Created by root on 13.02.2016.
 */
public interface IUserService {
    User create(String email, String password, String fName, String nick) throws Exception;
    User create(RegistrationFields registrationFields) throws Exception;
    User getByEmail(String email);
    void clearAll();
    User getByName(String name);
    User getById(String id);
    User getByNick(String nick);
}
