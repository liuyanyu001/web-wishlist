package com.wishlist.repository;

import com.wishlist.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {

    User findByFirstName(String firstName);

    List<User> findByLastName(String lastName);

    @Query(value = "{online : true}")
    List <User> findAllOnlyOnline();
}
