package com.wishlist.controller;


import com.nimbusds.jose.JOSEException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.core.Context;

import com.wishlist.bean.auth.RegistrationFields;
import com.wishlist.bean.response.TextResponse;
import com.wishlist.bean.response.UserTokenResponse;
import com.wishlist.util.auth.AuthorizeHeaderRequired;
import com.wishlist.util.auth.Token;
import com.wishlist.model.User;
import com.wishlist.service.IUserService;
import com.wishlist.util.auth.AuthUtils;
import com.wishlist.util.auth.PasswordService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {
    private static Logger LOG = Logger.getLogger(AuthController.class);
 
    @Autowired
    private IUserService userService;

    @RequestMapping("/api/auth/login")
    public ResponseEntity login(@RequestBody @Valid final User user, @Context final HttpServletRequest request)
            throws JOSEException {
        LOG.info("User: '" + user + "'");
        final User foundUser = userService.getByEmail(user.getEmail());
        if (foundUser != null
                && PasswordService.checkPassword(user.getPassword(), foundUser.getPassword())) {
            final Token token = AuthUtils.createToken(request.getRemoteHost(), foundUser);
            return new ResponseEntity<UserTokenResponse>(new UserTokenResponse(token.getToken(), foundUser), HttpStatus.OK);
        }
        return new ResponseEntity<String>("Wrong email and/or password", HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "api/auth/registration", method = RequestMethod.POST)
    public ResponseEntity<Object> registration(@RequestBody RegistrationFields registrationFields) {
        try {
            User user = userService.create(registrationFields);
            return new ResponseEntity<Object>(user, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<Object>(new TextResponse(ex.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value = "/auth/isNickFree/{nick}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Boolean> isNickFree(@PathVariable  String nick){
        return new ResponseEntity<Boolean>(null == userService.getByNick(nick), HttpStatus.OK);
    }

    @RequestMapping(value = "/auth/isEmailFree/{email}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Boolean> isEmailFree(@PathVariable  String email){
        return new ResponseEntity<Boolean>(null == userService.getByEmail(email), HttpStatus.OK);
    }

    @RequestMapping(value = "/api/auth/logout", method = RequestMethod.GET)
    @ResponseBody @AuthorizeHeaderRequired
    public ResponseEntity<Boolean> logout(HttpServletRequest request){
        AuthUtils.logout(request);
        return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/auth/nick", method = RequestMethod.GET)
    @ResponseBody @AuthorizeHeaderRequired
    public ResponseEntity<String> getAuthUserNick(HttpServletRequest request){
        User o = (User) request.getSession().getAttribute("userBean");
        return new ResponseEntity<String>(o.getNick(), HttpStatus.OK);
    }
}
