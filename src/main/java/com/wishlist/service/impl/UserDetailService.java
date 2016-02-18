package com.wishlist.service.impl;

import com.wishlist.repository.UserRepository;
import com.wishlist.util.Cipher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailService  implements UserDetailsService{

    @Autowired private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //todo check for remember me auth failed logic
        UserDetails loadedUser = new User("UserNotFound", "UserNotFound", new ArrayList<>());
        try {
            com.wishlist.model.User user = userRepository.findByLogin(s);
            loadedUser = new User(s, Cipher.encrypt(user.getPassword()), user.getRoles());
        } catch (Exception repositoryProblem) {
            //throw new InternalAuthenticationServiceException(repositoryProblem.getMessage(), repositoryProblem);
        }finally {
            return loadedUser;
        }

    }
}
