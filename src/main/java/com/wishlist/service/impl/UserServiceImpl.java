package com.wishlist.service.impl;

import com.wishlist.bean.RegistrationFields;
import com.wishlist.model.User;
import com.wishlist.repository.UserRepository;
import com.wishlist.service.IUserService;
import com.wishlist.util.Cipher;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements IUserService {

    @Autowired private UserRepository userRepository;

    @Override
    public void create(String email, String password, String fName, String login) throws Exception {
        Pair<Boolean, String> isExist = checkExists(email, login);
        if (!isExist.getKey()) {
            User user = new User(email, password, fName, login);
            user.setPassword(Cipher.encrypt(password));
            user.addRole(new SimpleGrantedAuthority("ROLE_USER"));
            userRepository.save(user);
        }else {
            throw new Exception(isExist.getValue());
        }
    }

    private Pair<Boolean, String> checkExists(String email, String login) {
        StringBuilder sb = new StringBuilder();
        Pair<Boolean, String> response;
        boolean isExist = false;

        if (null != userRepository.findByEmail(email)){
            sb.append("User with this email ");
            isExist = true;
        }

        if (null != userRepository.findByLogin(login)) {
            if(isExist){
                sb.append(" and this login");
            }else {
                sb.append("User with this login");
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
    public void create(RegistrationFields registrationFields) throws Exception {
        create(registrationFields.getEmail(), registrationFields.getPassword(), registrationFields.getFirstName(), registrationFields.getLogin());
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
    public User getByLogin(String login) {
        return userRepository.findByLogin(login);
    }
}
