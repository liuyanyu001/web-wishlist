package com.wishlist.service.impl;

import com.wishlist.bean.ForgotPasswordBean;
import com.wishlist.model.ForgotPassword;
import com.wishlist.model.User;
import com.wishlist.repository.ForgotPasswordRepository;
import com.wishlist.repository.UserRepository;
import com.wishlist.service.IForgotPassword;
import com.wishlist.util.Cipher;
import com.wishlist.util.DateUtil;
import com.wishlist.util.auth.SecurityUtil;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ForgotPasswordService implements IForgotPassword {

    @Autowired
    private ForgotPasswordRepository forgotPasswordRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private SecurityUtil securityUtil;

    private ForgotPassword getByUser(String email) {
        Query query = new Query(Criteria.where("user.$id").is(new ObjectId(userRepository.findByEmail(email).getId())));
        ForgotPassword token = mongoTemplate.findOne(query, ForgotPassword.class);
        return token;
    }

    @Override
    public void restorePassword(String email) {

        User user = userRepository.findByEmail(email);
        if (null == user) {
            throw new UsernameNotFoundException(String.format("User with email %s not found.", email));
        }

        ForgotPassword forgotPassword = getByUser(email);

        if (null != forgotPassword) {
            forgotPassword.setDate(DateUtil.dateTimeToMillis(LocalDateTime.now()));
        } else {
            forgotPassword = new ForgotPassword();
            forgotPassword.setUser(user);
        }

        forgotPasswordRepository.save(forgotPassword);
    }

    @Override
    public boolean confirmPassword(String token, String newPassword) {
        ForgotPasswordBean fpb = getByToken(token);
        if (null != fpb) {
            boolean isExpired = securityUtil.isTokenExpired(fpb);
            if (isExpired) {
                forgotPasswordRepository.delete(forgotPasswordRepository.findByToken(token));
                return false;
            } else {
                User user = userRepository.findByEmail(fpb.getEmail());
                user.setPassword(Cipher.encrypt(newPassword));
                userRepository.save(user);
                forgotPasswordRepository.delete(forgotPasswordRepository.findByToken(token));
                return true;
            }
        }
        return false;
    }

    @Override
    public ForgotPasswordBean getByToken(String token) {
        ForgotPassword forgotPassword = forgotPasswordRepository.findByToken(token);
        return new ForgotPasswordBean(forgotPassword);
    }

    @Override
    public boolean isTokenAlive(String token) {
        boolean isAlive = false;
        try {
            ForgotPasswordBean fpb = getByToken(token);
            if (null != fpb) {
                boolean isExpired = securityUtil.isTokenExpired(fpb);
                if (isExpired) {
                    forgotPasswordRepository.delete(forgotPasswordRepository.findByToken(token));
                } else {
                    isAlive = true;
                }
            }
        }catch (Exception ex){

        }finally {
            return isAlive;
        }
    }

}

