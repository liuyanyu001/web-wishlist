package com.wishlist.service.impl;

import com.wishlist.bean.auth.RegistrationFields;
import com.wishlist.bean.profile.UserFollowerStatistic;
import com.wishlist.bean.profile.UserProfileBean;
import com.wishlist.model.User;
import com.wishlist.repository.UserRepository;
import com.wishlist.service.IUserService;
import com.wishlist.util.auth.PasswordService;
import com.wishlist.util.auth.SecurityUtil;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements IUserService {

    @Autowired private UserRepository userRepository;
    @Autowired private SecurityUtil securityUtil;
    @Autowired private MongoTemplate mongoTemplate;

    @Override
    public User create(String email, String password, String fName, String nick) throws Exception {
        Pair<Boolean, String> isExist = checkExists(email, nick);
        if (!isExist.getKey()) {
            User user = new User(email, password, fName, nick);
            user.setPassword(PasswordService.hashPassword(password));
            user.addRole(new SimpleGrantedAuthority("ROLE_USER"));
            userRepository.save(user);
            return user;
        } else {
            throw new Exception(isExist.getValue());
        }
    }

    private Pair<Boolean, String> checkExists(String email, String nick) {
        StringBuilder sb = new StringBuilder();
        Pair<Boolean, String> response;
        boolean isExist = false;

        if (null != userRepository.findByEmail(email)) {
            sb.append("This email ");
            isExist = true;
        }

        if (null != userRepository.findByNick(nick)) {
            if (isExist) {
                sb.append("and  nick");
            } else {
                sb.append("This nick");
                isExist = true;
            }
        }

        if (isExist) {
            sb.append(" already exist!");
        }

        response = new ImmutablePair<>(isExist, sb.toString());

        return response;
    }

    @Override
    public User create(RegistrationFields registrationFields) throws Exception {
        return create(registrationFields.getEmail(), registrationFields.getPassword(), registrationFields.getFirstName(), registrationFields.getNick());
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void clearAll() {
        userRepository.deleteAll();
    }

    @Override
    public User getByName(String name) {
        return userRepository.findByFirstName(name);
    }

    @Override
    public User getById(String id) {
        return userRepository.findOne(id);
    }

    @Override
    public User getByNick(String nick) {
        return userRepository.findByNick(nick);
    }

    @Override
    public UserProfileBean getProfileByNick(String nick) throws Exception {

        User user = getByNick(nick);
        if (null == user) {
            throw new Exception("User not found");
        }

        UserProfileBean profileBean = new UserProfileBean(user);
        profileBean.setMe(user.getEmail().equals(securityUtil.getAuthUser().getEmail()));
        UserFollowerStatistic ufs = makeStatistic(user);
        profileBean.setFollowerStatistic(ufs);
        return profileBean;
    }

    public UserFollowerStatistic makeStatistic(User user) {

        UserFollowerStatistic statistic = new UserFollowerStatistic();

        List<User> followers = getUserFollowers(user);

        User authUser = getByEmail(securityUtil.getAuthUser().getEmail());

        statistic.setFollowedByMe(authUser.isFollowingFor(user));

        statistic.setFollowing(user.getFollowing().stream().limit(6).collect(Collectors.toSet()));
        statistic.setFollowers(followers.stream().limit(6).collect(Collectors.toSet()));

        statistic.setTotalFollowers(followers.size());
        statistic.setTotalFollowing(user.getFollowing().size());


        return statistic;
    }

    private List<User> getUserFollowers(User user){
        Query query = new Query(Criteria.where("following.$id").is(new ObjectId(user.getId())));
        return mongoTemplate.find(query, User.class);
    }

    @Override
    public void follow(String followerNick) {
        User follower = getByNick(followerNick);
        User user = getByNick(securityUtil.getAuthUser().getNick());
        if (null == follower || null == user) {
            throw new IllegalArgumentException("Wrong parameters");
        }

        user.following(follower);

        userRepository.save(user);
    }

    @Override
    public void unfollow(String followingNick) {
        User follower = getByNick(followingNick);
        User user = getByNick(securityUtil.getAuthUser().getNick());
        if (null == follower || null == user) {
            throw new IllegalArgumentException("Wrong parameters");
        }

        user.endFollowing(follower);
        userRepository.save(user);
    }

    @Override
    public UserFollowerStatistic getStatistic(String nick) {
        User user = getByNick(nick);
        return makeStatistic(user);
    }
}
