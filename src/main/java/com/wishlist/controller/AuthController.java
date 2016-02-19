package com.wishlist.controller;


import com.nimbusds.jose.JOSEException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.core.Context;

import com.wishlist.bean.RegistrationFields;
import com.wishlist.util.auth.Token;
import com.wishlist.model.User;
import com.wishlist.service.IUserService;
import com.wishlist.util.auth.AuthUtils;
import com.wishlist.util.auth.PasswordService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private static Logger LOG = Logger.getLogger(AuthController.class);
 
    @Autowired
    private IUserService userService;

    @RequestMapping("/auth/login")
    public ResponseEntity login(@RequestBody @Valid final User user, @Context final HttpServletRequest request)
            throws JOSEException {
        LOG.info("User: '" + user + "'");
        final User foundUser = userService.getByLogin(user.getLogin());
        if (foundUser != null
                && PasswordService.checkPassword(user.getPassword(), foundUser.getPassword())) {
            final Token token = AuthUtils.createToken(request.getRemoteHost(), foundUser.getId());
            return new ResponseEntity<Token>(token, HttpStatus.OK);
        }
        return new ResponseEntity<String>("Wrong email and/or password", HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "/auth/registration", method = RequestMethod.POST)
    public ResponseEntity<Object> registration(@RequestBody RegistrationFields registrationFields) {
        try {
            User user = userService.create(registrationFields);
            return new ResponseEntity<Object>(user, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
