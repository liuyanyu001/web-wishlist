package com.wishlist.service.impl;

import com.wishlist.bean.user.CreateWishBean;
import com.wishlist.util.auth.Wish;
import com.wishlist.repository.UserRepository;
import com.wishlist.repository.WishRepository;
import com.wishlist.service.IWishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishServiceImpl implements IWishService {

    @Autowired private WishRepository wishRepository;
    @Autowired private UserRepository userRepository;

    @Override
    public void create(CreateWishBean wish) {
        wishRepository.save(fillWishData(wish));
    }

    @Override
    public Wish getById(String id) {
        return wishRepository.findOne(id);
    }

    @Override
    public List<Wish> getAllByUser(String userId) {
        return wishRepository.findAllByOwnerId(userId);
    }

    private Wish fillWishData(CreateWishBean createWishBean){
        Wish wish = new Wish();
        wish.setDescription(createWishBean.getDescription());
        wish.setPicture(createWishBean.getPicture());
        wish.setTitle(createWishBean.getTitle());
        wish.setOwner(userRepository.findOne(createWishBean.getOwnerId()));
        return wish;
    }
}
