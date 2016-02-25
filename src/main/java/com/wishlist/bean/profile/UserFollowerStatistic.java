package com.wishlist.bean.profile;


import com.wishlist.model.User;

import java.util.List;

public class UserFollowerStatistic {

    //user follow
    private List<User> following;
    //follow user
    private List<User> followers;

    private long totalFollowing;
    private long totalFollowers;

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

    public List<User> getFollowing() {
        return following;
    }

    public void setFollowing(List<User> following) {
        this.following = following;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }
}
