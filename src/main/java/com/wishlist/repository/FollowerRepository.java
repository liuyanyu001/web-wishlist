package com.wishlist.repository;

import com.wishlist.model.Follower;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface FollowerRepository extends MongoRepository<Follower, String> {

    @Query("{ 'user.$id': ?0, follower.$id: ?1 }")
    Follower findByUserIdAndFollowerId(String userId, String followerId);

    List<Follower> findByUserId(String userId);

    List<Follower> findByFollowerId(String followerId);

}
