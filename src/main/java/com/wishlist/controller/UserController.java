package com.wishlist.controller;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.ReadOnlyJWTClaimsSet;
import com.wishlist.model.User;
import com.wishlist.service.IUserService;
import com.wishlist.util.auth.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

@RestController
@RequestMapping(value = "/api/auth/users")
@SessionAttributes("userBean")
public class UserController {

    @Autowired private IUserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<Object> user(HttpServletRequest request){
        String header = request.getHeader(com.wishlist.util.auth.AuthUtils.AUTH_HEADER_KEY);
        Object o = request.getSession().getAttribute("userBean");
        try {
            ReadOnlyJWTClaimsSet set = AuthUtils.decodeToken(header);
            User user = userService.getById(set.getSubject());
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (ParseException | JOSEException e) {
            return new ResponseEntity<Object>("Somethink went wrong", HttpStatus.BAD_REQUEST);
        }

    }

}
