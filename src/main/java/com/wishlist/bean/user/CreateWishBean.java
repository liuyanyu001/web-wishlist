package com.wishlist.bean.user;


public class CreateWishBean {

    private String title;
    private String picture;
    private String description;
    private String ownerId;

    public CreateWishBean() {
    }

    public CreateWishBean(String title, String picture, String description, String ownerId) {
        this.title = title;
        this.picture = picture;
        this.description = description;
        this.ownerId = ownerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }
}
