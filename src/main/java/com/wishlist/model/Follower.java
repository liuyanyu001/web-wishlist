package com.wishlist.model;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "followers")
public class Follower extends BaseObject{

    @DBRef
    private User user;

    @DBRef
    private User follower;

    public Follower(User user, User follower) {
        this.user = user;
        this.follower = follower;
    }

    public Follower() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getFollower() {
        return follower;
    }

    public void setFollower(User follower) {
        this.follower = follower;
    }
}
