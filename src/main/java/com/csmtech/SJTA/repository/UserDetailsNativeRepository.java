package com.csmtech.SJTA.repository;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

/*
 * @Auth  GuruPrasad
 */

@Repository
public class UserDetailsNativeRepository {

	@PersistenceContext
	EntityManager entityManager;

	@Transactional
	public Integer updateDetails(String newFullName, String newemailId, BigInteger newUpdatedBy, BigInteger userId) {
		entityManager.createNativeQuery("SET CONSTRAINTS ALL DEFERRED").executeUpdate();
		Date currentDateTime = new Date();

		return entityManager.createNativeQuery(
				"UPDATE public.user_details SET full_name = :setfullname, email_id = :setemailid, updated_by = :setupdatedby, updated_on = :setupdatedon WHERE user_id = :givenuserid")
				.setParameter("setfullname", newFullName).setParameter("setemailid", newemailId)
				.setParameter("setupdatedby", userId).setParameter("setupdatedon", currentDateTime)

				.setParameter("givenuserid", userId).executeUpdate();
	}

	@Transactional
	public Integer updateUser(String encodedPassword, String newUpdatedBy, BigInteger userId) {
		entityManager.createNativeQuery("SET CONSTRAINTS ALL DEFERRED").executeUpdate();
		Date currentDateTime = new Date();

		return entityManager.createNativeQuery(
				"UPDATE public.user_details SET password = :setpassword, updated_by = :setupdatedby, updated_on = :setupdatedon WHERE user_id = :givenuserid")
				.setParameter("setpassword", encodedPassword).setParameter("setupdatedby", userId)
				.setParameter("setupdatedon", currentDateTime)

				.setParameter("givenuserid", userId).executeUpdate();
	}

	
	

}
