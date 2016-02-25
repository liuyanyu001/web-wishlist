package com.wishlist.service;


import com.wishlist.bean.user.CreateWishBean;
import com.wishlist.util.auth.Wish;

import java.util.List;

public interface IWishService {

    void create(CreateWishBean wish);

    Wish getById(String id);

    List<Wish> getAllByUser(String userId);

}
