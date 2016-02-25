package com.wishlist.service.impl;

import com.wishlist.bean.profile.UserFollowerStatistic;
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

        if(null == getLinkByUserAndFollower(user, futureFollower)) {
            follower = new Follower(uUser, fFolowwer);
            followerRepository.save(follower);
        }

        return true;
    }

    public Boolean unLink(String futureFollower, String user) {
        User fFolowwer = userService.getById(futureFollower);
        User uUser = userService.getById(user);
        if (null == fFolowwer || null == uUser) {
            return false;
        }

        if (isFollowedForUser(user, futureFollower)) {
            followerRepository.delete(getLinkByUserAndFollower(user, futureFollower));
            return true;
        }

        return false;
    }

    public boolean isFollowedForUser(String user, String follower) {
        Follower fLink = getLinkByUserAndFollower(user, follower);
        return (null != fLink);
    }

    private Follower getLinkByUserAndFollower(String userId, String followerId) {
        Query query = new Query(Criteria.where("user.$id").is(new ObjectId(userId))
                .andOperator(Criteria.where("follower.$id").is(new ObjectId(followerId))));
        return mongoTemplate.findOne(query, Follower.class);
    }

    public List<User> getFollowersOf(String userId) {
        List<User> followers;
        List<Follower> links = followerRepository.findByUserId(userId);
        followers = links.stream().map(Follower::getFollower).collect(Collectors.toList());
        return followers;
    }

    public List<User> getUserFollowedOf(String userId) {
        List<User> followed;
        List<Follower> links = followerRepository.findByFollowerId(userId);
        followed = links.stream().map(Follower::getUser).collect(Collectors.toList());
        return followed;
    }

    public List<User> getFollowersOf(String id, int resultCount) {
        Query query = new Query(Criteria.where("user.$id").is(new ObjectId(id))).limit(resultCount);
        List<User> followers;
        List<Follower> links = mongoTemplate.find(query, Follower.class);
        followers = links.stream().map(Follower::getFollower).collect(Collectors.toList());
        return followers;
    }

    public List<User> getUserFollowedOf(String id, int resultCount) {
        Query query = new Query(Criteria.where("follower.$id").is(new ObjectId(id))).limit(resultCount);
        List<User> followed;
        List<Follower> links = mongoTemplate.find(query, Follower.class);
        followed = links.stream().map(Follower::getFollower).collect(Collectors.toList());
        return followed;
    }

    public UserFollowerStatistic getFollowerStatistic(String nick, boolean full){
        UserFollowerStatistic ufs = new UserFollowerStatistic();
        User u = userService.getByNick(nick);

        if (full){
            ufs.setFollowers(getFollowersOf(u.getId()));
            ufs.setFollowing(getUserFollowedOf(u.getId()));
        }else {
            int usersCount = 6;
            ufs.setFollowers(getFollowersOf(u.getId(), usersCount));
            ufs.setFollowing(getUserFollowedOf(u.getId(), usersCount));
        }

        Query qFollowing = new Query(Criteria.where("follower.$id").is(new ObjectId(u.getId())));
        Query qFollowers = new Query(Criteria.where("user.$id").is(new ObjectId(u.getId())));

        ufs.setTotalFollowers(mongoTemplate.count(qFollowers, Follower.class));
        ufs.setTotalFollowing(mongoTemplate.count(qFollowing, Follower.class));

        return ufs;
    }

}
