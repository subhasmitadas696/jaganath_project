package com.csmtech.SJTA.repository;

/**
 * @author prasanta.sethi
 */

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public class ForgetPasswordNativeRepository {

	@PersistenceContext
	EntityManager entityManager;

	@Transactional
    public Integer updatepassword(String newPassword, String mobileNo) {
        entityManager.createNativeQuery("SET CONSTRAINTS ALL DEFERRED").executeUpdate();
        entityManager.close();
        return entityManager.createNativeQuery("UPDATE public.user_details SET password = :newsetpassword WHERE mobile_no = :givenmono")
                .setParameter("newsetpassword", newPassword)
                .setParameter("givenmono", mobileNo)
                .executeUpdate();
    }
	
	@Transactional
	public Integer updateOtp(Long userId,String updateOtp) {
		
		String query=" update user_details "
				+ "    set otp=:updateOtp "
				+ "    where user_id=:userId ";
		
		return entityManager.createNativeQuery(query)
                .setParameter("updateOtp", updateOtp)
                .setParameter("userId", userId)
                .executeUpdate();
	}
}
