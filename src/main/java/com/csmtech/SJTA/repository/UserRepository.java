package com.csmtech.SJTA.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.csmtech.SJTA.entity.User;

/*
 * @Auth  GuruPrasad
 */

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUserName(String userName);

	User findBymobileNo(String mobileNo);

	User findByuserId(BigInteger userId);

	@Query(value = "select * from user_details where user_id=:userId and status='0' ", nativeQuery = true)
	User findByuserIdFalse(@Param("userId") BigInteger userId);


}
