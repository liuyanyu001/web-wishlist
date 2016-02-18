package com.wishlist.repository;

import com.wishlist.model.Wish;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface WishRepository extends MongoRepository<Wish, String> {

    @Query(value = "{'owner.id' : ?0 }")
    List<Wish> findAllByOwnerId(String id);

}
