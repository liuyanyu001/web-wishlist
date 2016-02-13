package com.wishlist.service.impl;

import com.wishlist.model.User;
import com.wishlist.repository.UserRepository;
import com.wishlist.service.IUserService;
import com.wishlist.util.Cipher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements IUserService {

    @Autowired private UserRepository userRepository;

    @Override
    public void create(String email, String password, String fName, String lName) {
        User user = new User(email, password, fName, lName);
        user.setPassword(Cipher.encrypt(password));
        user.addRole(new SimpleGrantedAuthority("ROLE_USER"));
        userRepository.save(user);
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void clearAll() {
        userRepository.deleteAll();
    }

    @Override
    public User getByName(String name) {
        return userRepository.findByFirstName(name);
    }
}
