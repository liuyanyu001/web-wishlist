package com.wishlist.controller;

import com.wishlist.service.IForgotPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/password")
public class ForgotPasswordController {

    @Autowired private IForgotPassword forgotPassword;

    @RequestMapping(value = "/forgot", method = RequestMethod.GET)
    public String forgotPassPage(){
        return "password-forgot";
    }

    @RequestMapping(value = "/restore", method = RequestMethod.POST)
    public String restore(@RequestParam("email")String email, ModelMap map){
        String msg = null;
        try{
            forgotPassword.restorePassword(email);
            msg = String.format("Instructions for restore your pass was sent to %s", email);
        }catch (UsernameNotFoundException ex){
            msg = ex.getMessage();
        }finally {
            map.addAttribute("msg", msg);

        }
        return "password-forgot";
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.GET)
    public String confirm(@RequestParam("token")String token, ModelMap map){
        boolean isTokenAlive = forgotPassword.isTokenAlive(token);
        if(isTokenAlive){
            map.addAttribute("token", token);
            return "confirm-password";
        }else {
            return "redirect:/";
        }
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    public String confirm(@RequestParam("token")String token, @RequestParam("password")String password, ModelMap map){
        boolean result = forgotPassword.confirmPassword(token, password);
        if (result){
            map.addAttribute("token", token);
            map.addAttribute("msg", "Password successful changed.");
            return "confirm-password";
        }else {
            map.addAttribute("token", token);
            map.addAttribute("msg", "Problem with password change.");
            return "confirm-password";
        }
    }

}
