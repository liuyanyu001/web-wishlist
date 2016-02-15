package com.wishlist.model;

import com.wishlist.util.DateUtil;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.*;
import java.util.UUID;

@Document(collection = "forgot_passwords")
public class ForgotPassword {

    @Id
    private String id;

    @DBRef
    private User user;

    private String token;

    private long date;

    public ForgotPassword(){
        date = System.currentTimeMillis();
        token = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ForgotPassword{" +
                "id='" + id + '\'' +
                ", user=" + user +
                ", token='" + token + '\'' +
                ", date=" + date +
                '}';
    }
}
