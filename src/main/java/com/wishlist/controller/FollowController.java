package com.wishlist.controller;

import com.wishlist.model.User;
import com.wishlist.service.IUserService;
import com.wishlist.service.impl.FollowerService;
import com.wishlist.util.auth.AuthorizeHeaderRequired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@SessionAttributes("userBean")
@RequestMapping("/api/followers")
public class FollowController {

    @Autowired private FollowerService followerService;
    @Autowired private IUserService userService;

    @RequestMapping(value = "/makelink/{nick}")
    public @ResponseBody @AuthorizeHeaderRequired
    ResponseEntity<Boolean> makeFollowLink(@PathVariable String nick, HttpServletRequest request){
        User authUser = (User) request.getSession().getAttribute("userBean");
        User userToFollow = userService.getByNick(nick);
        Boolean result = followerService.makeLink(authUser.getId(), userToFollow.getId());
        return new ResponseEntity<Boolean>(result, HttpStatus.OK);
    }

}
