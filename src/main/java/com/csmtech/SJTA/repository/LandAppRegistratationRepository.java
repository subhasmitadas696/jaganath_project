package com.csmtech.SJTA.repository;

/**
 * @Auth Rashmi Ranjan Jena
 */

import java.io.Serializable;
import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.csmtech.SJTA.entity.LandAppRegistratationEntity;

@Repository
public interface LandAppRegistratationRepository extends JpaRepository<LandAppRegistratationEntity, Serializable> {

	//Get The Use Mobile NO Through Data And Check To Valid Or Not
	LandAppRegistratationEntity findBymobileno(String username);
	
	//findbymobileno the user are block are not
	@Query(value = "select count(*) "
			+ "			   from "
			+ "			   user_details  "
			+ "			   where  "
			+ "			   mobile_no=?  "
			+ "			   AND user_block_status=false "
			+ "            AND status='0' ",nativeQuery = true)
	Integer findBymobilenoBlockOrNot(String username);
	
	
	@Query(value = " INSERT INTO public.user_details_test (user_name,password, full_name, mobile_no, email_id, otp, user_type, created_by, created_on ) "
			+ "      VALUES (    :username ,   :password ,   :fullname , :mobileno , :email  , :otp , :usertype  ,   "
			+ "      (SELECT currval(pg_get_serial_sequence('public.user_details_test', 'user_id')) ), "
			+ "      CURRENT_TIMESTAMP)",nativeQuery = true)
	public Integer saveUserData(
			@Param("username") String userName,
			@Param("password") String password,
			@Param("fullname") String fullName,
			@Param("mobileno") String mobileNo,
			@Param("email") String email,
			@Param("otp") String otp,
			@Param("usertype") String usertype
			);
}
