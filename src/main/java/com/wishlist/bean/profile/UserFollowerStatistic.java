package com.wishlist.bean.profile;


import com.wishlist.model.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserFollowerStatistic {

    //user follow
    private Set<User> following = new HashSet<>();
    //follow user
    private Set<User> followers = new HashSet<>();


    private long totalFollowing;
    private long totalFollowers;

    boolean followedByMe;

    public long getTotalFollowing() {
        return totalFollowing;
    }

    public void setTotalFollowing(long totalFollowing) {
        this.totalFollowing = totalFollowing;
    }

    public long getTotalFollowers() {
        return totalFollowers;
    }

    public void setTotalFollowers(long totalFollowers) {
        this.totalFollowers = totalFollowers;
    }

    public UserFollowerStatistic(){

    }

    public Set<User> getFollowing() {
        return following;
    }

    public void setFollowing(Set<User> following) {
        this.following = following;
    }

    public Set<User> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<User> followers) {
        this.followers = followers;
    }

    public boolean isFollowedByMe() {
        return followedByMe;
    }

    public void setFollowedByMe(boolean followedByMe) {
        this.followedByMe = followedByMe;
    }
}
