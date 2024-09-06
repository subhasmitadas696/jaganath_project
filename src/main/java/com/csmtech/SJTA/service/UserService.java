package com.csmtech.SJTA.service;

import java.math.BigInteger;
import java.util.Date;

import com.csmtech.SJTA.dto.UserDetails;
import com.csmtech.SJTA.entity.User;

/*
 * @Auth  GuruPrasad
 */

public interface UserService {

	User findByUserName(String userName);

	User save(User user);

	User findBymobileNo(String mobileNo);

	User findByuserId(BigInteger userId);

	Integer updateDetails(String newFullName, String newemailId, BigInteger newUpdatedBy, BigInteger userId);

	Integer updateUser(String encodedPassword, String newUpdatedBy, BigInteger userId);

	User findByuserIdFalse(BigInteger userId);

}
