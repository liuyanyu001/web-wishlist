package com.wishlist.controller;


import com.wishlist.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/profile")
@SessionAttributes("userBean")
public class ProfileController {

    @RequestMapping(value = "me", method = RequestMethod.GET)
    public String me(@ModelAttribute(value = "userBean") User user, ModelMap map) {
        map.addAttribute("user", user);
        return "me";
    }

    @RequestMapping(value = "settings", method = RequestMethod.GET)
    public String settings(@ModelAttribute(value = "userBean") User user, ModelMap map) {
        map.addAttribute("user", user);
        return "settings";
    }

}
