package com.csmtech.SJTA.service;

import com.csmtech.SJTA.dto.LandAppRegistratationDTO;

/**
 * @Auth Rashmi Ranjan Jena
 */

public interface LandAppRegistratationService {

	public Integer saveUserData(LandAppRegistratationDTO registerdto);
	
	public Integer saveRegisterUserMobileNoOrOtp(String mobileNO);
	
	public Integer UpdateRegisterUserMobileNoOrOtp(String mobileNO);
	
	public String getOTPByMobileNo(String mobileno);
	
	

}
