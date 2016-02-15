package com.wishlist.controller;

import com.wishlist.model.User;
import com.wishlist.util.auth.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
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
            return "redirect:/me/";
        }
        return "index";
    }

    @RequestMapping(value = "me", method = RequestMethod.GET)
    public String me(@ModelAttribute(value = "userBean") User user, ModelMap map) {
        map.addAttribute("user", user);
        return "me";
    }
}
