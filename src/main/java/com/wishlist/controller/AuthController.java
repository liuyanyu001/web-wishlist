package com.wishlist.controller;

import com.wishlist.bean.RegistrationFields;
import com.wishlist.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    private IUserService userService;

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ResponseEntity<String> registration(@RequestBody RegistrationFields registrationFields) {
        try {
            userService.create(registrationFields);
            return new ResponseEntity<String>("User successful created!", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/success", method = RequestMethod.POST)
    public @ResponseBody
    void success(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        System.out.println("success");
        //request.getRequestDispatcher("/").forward(request, response);
    }

    @RequestMapping(value = "/failure", method = RequestMethod.POST)
    public @ResponseBody
    void failure(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        System.out.println("failure");
        //request.getRequestDispatcher("/login").forward(request, response);
    }
}
