package com.wishlist.bean;

import com.wishlist.model.Follower;
import com.wishlist.model.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class UserProfileBean{

    private String name;

    private String nick;

    private String email;

    private Set<String> roles = new HashSet<>();

    boolean me;

    boolean followedByMe;

    private List<User> followers;
    private List<User> followed;

    /**

     //todo add fields isFollowMe and isIFollow

     */


    public UserProfileBean(User user){
        setNick(user.getNick());
        setEmail(user.getEmail());
        setName(user.getFirstName());
        setRoles(user.getRoles().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()));
    }

    public boolean isMe() {
        return me;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public void setMe(boolean me) {
        this.me = me;
    }

    public boolean isFollowedByMe() {
        return followedByMe;
    }

    public void setFollowedByMe(boolean followedByMe) {
        this.followedByMe = followedByMe;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }

    public List<User> getFollowed() {
        return followed;
    }

    public void setFollowed(List<User> followed) {
        this.followed = followed;
    }
}
