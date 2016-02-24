package com.wishlist.service.impl;

import com.wishlist.bean.RegistrationFields;
import com.wishlist.model.User;
import com.wishlist.repository.UserRepository;
import com.wishlist.service.IUserService;
import com.wishlist.util.auth.PasswordService;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements IUserService {

    @Autowired private UserRepository userRepository;

    @Override
    public User create(String email, String password, String fName, String nick) throws Exception {
        Pair<Boolean, String> isExist = checkExists(email, nick);
        if (!isExist.getKey()) {
            User user = new User(email, password, fName, nick);
            user.setPassword(PasswordService.hashPassword(password));
            user.addRole(new SimpleGrantedAuthority("ROLE_USER"));
            userRepository.save(user);
            return user;
        }else {
            throw new Exception(isExist.getValue());
        }
    }

    private Pair<Boolean, String> checkExists(String email, String nick) {
        StringBuilder sb = new StringBuilder();
        Pair<Boolean, String> response;
        boolean isExist = false;

        if (null != userRepository.findByEmail(email)){
            sb.append("This email ");
            isExist = true;
        }

        if (null != userRepository.findByNick(nick)) {
            if(isExist){
                sb.append("and  nick");
            }else {
                sb.append("This nick");
                isExist = true;
            }
        }

        if(isExist){
            sb.append(" already exist!");
        }

        response = new ImmutablePair<>(isExist, sb.toString());

        return response;
    }

    @Override
    public User create(RegistrationFields registrationFields) throws Exception {
       return create(registrationFields.getEmail(), registrationFields.getPassword(), registrationFields.getFirstName(), registrationFields.getNick());
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

    @Override
    public User getById(String id) {
        return userRepository.findOne(id);
    }

    @Override
    public User getByNick(String nick) {
        return userRepository.findByNick(nick);
    }
}
