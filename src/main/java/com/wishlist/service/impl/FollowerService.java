package com.wishlist.service.impl;

import com.wishlist.model.Follower;
import com.wishlist.model.User;
import com.wishlist.repository.FollowerRepository;
import com.wishlist.service.IUserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FollowerService {

    @Autowired
    private IUserService userService;

    @Autowired
    private FollowerRepository followerRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public boolean makeLink(String futureFollower, String user) {
        Follower follower = followerRepository.findByUserIdAndFollowerId(user, futureFollower);
        if (null != follower) {
            return false;
        }

        User fFolowwer = userService.getById(futureFollower);
        User uUser = userService.getById(user);
        if (null == fFolowwer || null == uUser) {
            return false;
        }

        //is following? unfollow
        if (isFollowedForUser(user, futureFollower)) {
            followerRepository.delete(getLinkByUserAndFollower(user, futureFollower));
            return false;
        }

        follower = new Follower(uUser, fFolowwer);
        followerRepository.save(follower);
        return true;
    }

    public boolean isFollowedForUser(String user, String follower) {
        Follower fLink = getLinkByUserAndFollower(user, follower);
        return (null != fLink);
    }

    private Follower getLinkByUserAndFollower(String user, String follower) {
        Query query = new Query(Criteria.where("user.$id").is(new ObjectId(user)).andOperator(Criteria.where("follower.$id").is(new ObjectId(follower))));
        return mongoTemplate.findOne(query, Follower.class);
    }

    public List<User> getFollowersOf(String user) {
        List<User> followers;
        List<Follower> links = followerRepository.findByUserId(user);
        followers = links.stream().map(Follower::getFollower).collect(Collectors.toList());
        return followers;
    }

    public List<User> getUserFollowedOf(String user) {
        List<User> followed;
        List<Follower> links = followerRepository.findByFollowerId(user);
        followed = links.stream().map(Follower::getUser).collect(Collectors.toList());
        return followed;
    }
}
