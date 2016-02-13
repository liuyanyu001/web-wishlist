package com.wishlist.controller;

import com.wishlist.model.User;
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

    @RequestMapping(method = RequestMethod.GET)
    public String index(){
        return "index";
    }

    @RequestMapping(value = "me", method = RequestMethod.GET)
    public String me(@ModelAttribute(value = "userBean")User user, ModelMap map){
        map.addAttribute("user", user);
        return "me";
    }
}
