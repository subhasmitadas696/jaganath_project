package com.csmtech.SJTA.repository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.csmtech.SJTA.entity.MRoleEntity;
import com.csmtech.SJTA.entity.RoleInfo;

@Repository
public class LandAppRegistratationClassRepository {

	@Autowired
	@PersistenceContext
	private EntityManager entityManager;

//	@Transactional
//	public Integer insertUserDetails(String userName, String password, String fullName, String mobileNo, String emailId, String otp, String userType) {
//	    String query = "INSERT INTO public.user_details (user_name, password, full_name, mobile_no, email_id, otp, user_type, created_by, created_on) " +
//	                   "VALUES (?, ?, ?, ?, ?, ?, ?, currval(pg_get_serial_sequence('public.user_details', 'user_id')), CURRENT_TIMESTAMP)";
//	    return entityManager.createNativeQuery(query)
//	        .setParameter(1, userName)
//	        .setParameter(2, password)
//	        .setParameter(3, fullName)
//	        .setParameter(4, mobileNo)
//	        .setParameter(5, emailId)
//	        .setParameter(6, otp)
//	        .setParameter(7, userType)
//	        .executeUpdate();
//	}

	@Transactional
	public Integer insertUserWithRole(String userName, String password, String fullName, String mobileNo,
			String emailId, String otp, String userType, String roleId) {
		String query = "    WITH inserted_user AS ( "
				+ "    INSERT INTO public.user_details (user_name, password, full_name, mobile_no, email_id, otp, user_type, created_by, created_on) "
				+ "    VALUES (?, ?, ?, ?, ?, ?, ?, currval(pg_get_serial_sequence('public.user_details', 'user_id')), CURRENT_TIMESTAMP) "
				+ "    RETURNING user_id " + "    ) "
				+ "    INSERT INTO public.user_role (user_id, role_id, created_by, created_on) "
				+ "    SELECT user_id, 2 , user_id, CURRENT_TIMESTAMP " + "    FROM inserted_user";

		return entityManager.createNativeQuery(query)
				.setParameter(1, userName)
				.setParameter(2, password)
				.setParameter(3, fullName)
				.setParameter(4, mobileNo)
				.setParameter(5, emailId)
				.setParameter(6, otp)
				.setParameter(7, userType)
				// second table add the roll_id
				// .setParameter(8, Long.parseLong(roleId))
				.executeUpdate();
	}

	@Transactional
	public List<RoleInfo> getRoleInfoList(String roleName) {
	    String query = "SELECT role_id, role_name FROM public.m_role where role_name=:roleName ";
	    @SuppressWarnings("unchecked")
		List<Object[]> resultList = entityManager.createNativeQuery(query)
	            .setParameter("roleName", roleName)
	            .getResultList();
	    return transformResultList(resultList);
	}
	private List<RoleInfo> transformResultList(List<Object[]> resultList) {
	    List<RoleInfo> roleInfoList = new ArrayList<>();
	    for (Object[] row : resultList) {
	        Short roleId = (Short) row[0];
	        String roleName = (String) row[1];

	        RoleInfo roleInfo = new RoleInfo();
	        roleInfo.setRoleId(roleId);
	        roleInfo.setRoleName(roleName);

	        roleInfoList.add(roleInfo);
	    }
	    return roleInfoList;
	}
	
	@Transactional
	public int getUserCountByMobileAndEmail(String mobileNo) {
	    String query = "SELECT count(*) FROM user_details WHERE mobile_no = :mobileNo AND status='0' ";

	    BigInteger count = (BigInteger) entityManager.createNativeQuery(query)
	            .setParameter("mobileNo", mobileNo)
	            .getSingleResult();

	    return count.intValue();
	}
	
	//Insert The Data In The Register otp check table
	 @Transactional
	    public Integer insertMobileAndOTP(String mobileNo,String otp) {
	        String query = "INSERT INTO public.registration_mobile_no_varification (mobile_no, otp) "
	                     + "VALUES (:mobileno, :otp)";

	        return entityManager.createNativeQuery(query)
	                    .setParameter("mobileno", mobileNo)
	                    .setParameter("otp", otp)
	                    .executeUpdate();
	    }
	
	//update otp 
	 @Transactional
	    public Integer updateMobileNoOrOtp(String mobileNo, String otp) {
	        String query = " UPDATE public.registration_mobile_no_varification "
	                     + " SET otp = :otp "
	                     + " WHERE mobile_no = :mobileno ";

	        return entityManager.createNativeQuery(query)
	                                      .setParameter("mobileno", mobileNo)
	                                      .setParameter("otp", otp)
	                                      .executeUpdate();
	    }
	
	 
	 @Transactional
		public Integer getUserCountByMobileAndEmailFirstRegisterTab(String mobileNo) {
		    String query = "SELECT count(*) FROM user_details WHERE status = '0' and mobile_no = :mobileNo ";
		    BigInteger count = (BigInteger) entityManager.createNativeQuery(query)
		            .setParameter("mobileNo", mobileNo)
		            .getSingleResult();
		    return count.intValue();
		}
	 
	 @Transactional
		public Integer getUserCountByMobileAndEmailFirstRegisterTabTemp(String mobileNo) {
		    String query = "SELECT count(*) FROM registration_mobile_no_varification WHERE mobile_no = :mobileNo ";
		    BigInteger count = (BigInteger) entityManager.createNativeQuery(query)
		            .setParameter("mobileNo", mobileNo)
		            .getSingleResult();
		    return count.intValue();
		}
	 
	 @Transactional
	    public String getOTPByMobileNo(String mobileNo) {
	        String query = "SELECT otp FROM public.registration_mobile_no_varification "
	                     + "WHERE mobile_no = :mobileno";

	        try {
	            return (String) entityManager.createNativeQuery(query)
	                                              .setParameter("mobileno", mobileNo)
	                                              .getSingleResult();
	        } catch (NoResultException e) {
	            return "Mobile No Not Found";
	        }
	    }

}
