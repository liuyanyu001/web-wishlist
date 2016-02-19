package com.wishlist.security.impl;


import com.wishlist.model.AuthToken;

public interface AuthTokenService {

	AuthToken create(AuthToken authToken);

	AuthToken update(AuthToken authToken);

	AuthToken findUserByTokenAndSeries(final String token, final String series);

	void deleteByTokenAndSeries(final String token, final String series);

	void deleteExpiredTokens();
}