package com.wishlist.controller;

import com.wishlist.util.auth.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * Created by root on 13.02.2016.
 */
@Controller
@RequestMapping(value = "/")
@SessionAttributes("userBean")
public class IndexController {

    @Autowired
    private SecurityUtil securityUtil;

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        if (securityUtil.isCurrentUserAuth()) {
            return "redirect:/profile/me/";
        }
        return "index";
    }


}
