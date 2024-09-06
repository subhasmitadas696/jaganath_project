package com.csmtech.SJTA.service;

import com.csmtech.SJTA.entity.LandAppRegistratationEntity;

/**
 * @author prasanta.sethi
 */

public interface ForgotPasswordService {

	public LandAppRegistratationEntity findByMobileNo(String mobileNo);

	public Integer updateOtp(LandAppRegistratationEntity user);

	public Integer updatepassword(String newPassword, String givenmono);

}
