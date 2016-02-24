package com.wishlist.controller;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.ReadOnlyJWTClaimsSet;
import com.wishlist.bean.UserProfileBean;
import com.wishlist.model.User;
import com.wishlist.service.IUserService;
import com.wishlist.service.impl.FollowerService;
import com.wishlist.util.auth.AuthUtils;
import com.wishlist.util.auth.AuthorizeHeaderRequired;
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
    @Autowired private FollowerService followerService;

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
    ResponseEntity<Object> user(@PathVariable String nick, HttpServletRequest request){
        User authUser = (User) request.getSession().getAttribute("userBean");
        try {
            User user = userService.getByNick(nick);
            if (null == user){
                throw new Exception("User not found");
            }

            UserProfileBean profileBean = new UserProfileBean(user);
            profileBean.setMe(user.getId().equals(authUser.getId()));
            if (!profileBean.isMe()){
                boolean isIFollowing = followerService.isFollowedForUser(user.getId(), authUser.getId());
                profileBean.setFollowedByMe(isIFollowing);
            }
            profileBean.setFollowers(followerService.getFollowersOf(user.getId()));
            profileBean.setFollowed(followerService.getUserFollowedOf(user.getId()));
            return new ResponseEntity<>(profileBean, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<Object>("Somethink went wrong", HttpStatus.BAD_REQUEST);
        }

    }

}
