package com.wishlist.repository;

import com.wishlist.model.ForgotPassword;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ForgotPasswordRepository extends MongoRepository<ForgotPassword, String> {
    ForgotPassword findByToken(String token);

}
