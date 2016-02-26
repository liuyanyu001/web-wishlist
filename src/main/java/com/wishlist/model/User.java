package com.wishlist.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wishlist.util.GrantedAuthorityDeserializer;
import com.wishlist.util.GrantedAuthoritySerializer;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;

import java.util.*;

@Document(collection = "users")
public class User extends BaseObject{

    @Field(value = "name")
    @JsonProperty(value = "name")
    private String firstName;

    private String nick;

    private String password;

    private String email;

    private boolean online;

    @JsonSerialize(contentUsing = GrantedAuthoritySerializer.class)
    @JsonDeserialize(contentUsing = GrantedAuthorityDeserializer.class)
    private Set<GrantedAuthority> roles = new HashSet<>();

    @DBRef
    private Set<User> following = new HashSet<>();

    public User() {}

    public User(String email, String password, String firstName, String nick) {
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.nick = nick;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String name) {
        this.firstName = name;
    }


    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public Set<GrantedAuthority> getRoles() {
        return roles;
    }

    public void setRoles(Set<GrantedAuthority> roles) {
        this.roles = roles;
    }


    public void addRole(GrantedAuthority role){
        roles.add(role);
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }


    public Set<User> getFollowing() {
        return following;
    }

    public void following(User user){
        if (notContains(user)){
            this.following.add(user);
        }
    }

    private boolean notContains(User user) {
        return 0 == this.following.stream().filter(u->u.getId().equals(user.getId())).count();
    }


    public void endFollowing(User user){
        Iterator<User> iterator = following.iterator();
        removeUser(user, iterator);
    }

    private void removeUser(User user, Iterator<User> iterator) {
        while (iterator.hasNext()){
            if (iterator.next().getId().equals(user.getId())){
                iterator.remove();
            }
        }
    }

    public boolean isFollowingFor(User user){
        long count = following.stream().filter(u->u.getNick().equals(user.getNick())).count();
        return count != 0;
    }
}
