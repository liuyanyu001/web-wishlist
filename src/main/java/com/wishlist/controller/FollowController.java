package com.wishlist.controller;

import com.wishlist.bean.profile.UserFollowerStatistic;
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
        followerService.makeLink(authUser.getId(), userToFollow.getId());
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    @RequestMapping(value = "/unlink/{nick}")
    public @ResponseBody @AuthorizeHeaderRequired
    ResponseEntity<Boolean> unFollowLink(@PathVariable String nick, HttpServletRequest request){
        User authUser = (User) request.getSession().getAttribute("userBean");
        User userToFollow = userService.getByNick(nick);
        followerService.unLink(authUser.getId(), userToFollow.getId());
        return new ResponseEntity<Boolean>(false, HttpStatus.OK);
    }

    @RequestMapping(value = "/{nick}/statistic", method = RequestMethod.GET) @AuthorizeHeaderRequired
    public ResponseEntity<UserFollowerStatistic> getFollowerStatistic(@PathVariable String nick,
                                                                      @RequestParam(value = "full",defaultValue = "false")Boolean full){
        return new ResponseEntity<UserFollowerStatistic>(followerService.getFollowerStatistic(nick, full), HttpStatus.OK);
    }
}
