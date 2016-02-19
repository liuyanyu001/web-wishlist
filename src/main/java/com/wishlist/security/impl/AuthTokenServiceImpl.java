package com.wishlist.security.impl;

import com.wishlist.model.AuthToken;
import com.wishlist.repository.AuthTokenRepository;
import com.wishlist.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;


@Service
public class AuthTokenServiceImpl implements AuthTokenService {

	@Autowired
	private AuthTokenRepository authTokenRepository;

	@Autowired private MongoTemplate mongoTemplate;

	@Override
	public AuthToken create(AuthToken authToken) {
		return authTokenRepository.save(authToken);
	}

	@Override
	public AuthToken findUserByTokenAndSeries(String token, String series) {
		return authTokenRepository.findByTokenAndSeries(token, series);
	}

	@Override
	public void deleteByTokenAndSeries(String token, String series) {
		authTokenRepository.deleteByTokenAndSeries(token, series);
	}

	@Override
	public AuthToken update(AuthToken authToken) {
		return authTokenRepository.save(authToken);
	}

	@Override
	public void deleteExpiredTokens() {
		Query query = new Query(Criteria.where("created").and("updated").lte(DateUtil.getCurrentTimeMinusMinutes(2)));
		mongoTemplate.remove(query, AuthToken.class);
	}

}


