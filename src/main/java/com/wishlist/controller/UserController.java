package com.wishlist.controller;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.ReadOnlyJWTClaimsSet;
import com.wishlist.bean.profile.UserFollowerStatistic;
import com.wishlist.model.User;
import com.wishlist.service.IUserService;
import com.wishlist.util.auth.AuthUtils;
import com.wishlist.util.auth.AuthorizeHeaderRequired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

@RestController
@RequestMapping(value = "/api/users")
@SessionAttributes("userBean")
public class UserController {

    @Autowired private IUserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody @AuthorizeHeaderRequired
    ResponseEntity<Object> user(HttpServletRequest request){
        String header = request.getHeader(com.wishlist.util.auth.AuthUtils.AUTH_HEADER_KEY);
        try {
            ReadOnlyJWTClaimsSet set = AuthUtils.decodeToken(header);
            User user = userService.getById(set.getSubject());
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (ParseException | JOSEException e) {
            return new ResponseEntity<Object>("Somethink went wrong", HttpStatus.BAD_REQUEST);
        }

    }

    public @ResponseBody @AuthorizeHeaderRequired
    @RequestMapping(value = "/nick/{nick}", method = RequestMethod.GET)
    ResponseEntity<Object> user(@PathVariable String nick){
        try {
            return new ResponseEntity<>(userService.getProfileByNick(nick), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<Object>("Somethink went wrong", HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(value = "/follow/{nick}")
    public @ResponseBody @AuthorizeHeaderRequired
    ResponseEntity<Boolean> makeFollowLink(@PathVariable String nick, HttpServletRequest request){
        userService.follow(nick);
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    @RequestMapping(value = "/unfollow/{nick}")
    public @ResponseBody @AuthorizeHeaderRequired
    ResponseEntity<Boolean> unFollowLink(@PathVariable String nick, HttpServletRequest request){
        User authUser = (User) request.getSession().getAttribute("userBean");
        userService.unfollow(nick);
        return new ResponseEntity<Boolean>(false, HttpStatus.OK);
    }

    @RequestMapping(value = "/{nick}/statistic", method = RequestMethod.GET) @AuthorizeHeaderRequired
    public ResponseEntity<UserFollowerStatistic> getFollowerStatistic(@PathVariable String nick,
                                                                      @RequestParam(value = "full",defaultValue = "false")Boolean full){
        return new ResponseEntity<UserFollowerStatistic>(userService.getStatistic(nick), HttpStatus.OK);
    }

}
