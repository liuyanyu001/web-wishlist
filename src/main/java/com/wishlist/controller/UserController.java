package com.wishlist.controller;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.ReadOnlyJWTClaimsSet;
import com.wishlist.model.User;
import com.wishlist.service.IUserService;
import com.wishlist.util.auth.AuthUtils;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Response;
import java.text.ParseException;

@RestController
@RequestMapping(value = "/api/auth")
public class UserController {

    @Autowired private IUserService userService;

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<Object> user(HttpServletRequest request){
        String header = request.getHeader(com.wishlist.util.auth.AuthUtils.AUTH_HEADER_KEY);
        System.out.println(header);
        try {
            ReadOnlyJWTClaimsSet set = AuthUtils.decodeToken(header);
            User user = userService.getById(set.getSubject());
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (ParseException | JOSEException e) {
            return new ResponseEntity<Object>("Somethink went wrong", HttpStatus.BAD_REQUEST);
        }

    }

}
