package com.wishlist.repository;

import com.wishlist.model.AuthToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface AuthTokenRepository extends MongoRepository<AuthToken, Long> {

	@Query("{'token' : ?0 , 'series' : ?0}")
	AuthToken findByTokenAndSeries(@Param("token") String token,
									   @Param("series") String series);


	@Query("{'token' : ?0 , 'series' : ?0}")
	void deleteByTokenAndSeries(@Param("token") String token,
								@Param("series") String series);

}
