package com.csmtech.SJTA.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.csmtech.SJTA.entity.LandAppRegistratationEntity;
import com.csmtech.SJTA.repository.ForgetPasswordNativeRepository;
import com.csmtech.SJTA.repository.ForgotPasswordRepository;
import com.csmtech.SJTA.service.ForgotPasswordService;
import com.csmtech.SJTA.util.OtpGenerateCommonUtil;

@Service
public class ForgotPasswordServiceImpl implements ForgotPasswordService {
	@Autowired
	private ForgotPasswordRepository forgotPasswordRepository;
	@Autowired
	private ForgetPasswordNativeRepository nativerepo;

	@Override
	public LandAppRegistratationEntity findByMobileNo(String mobileNo) {

		return forgotPasswordRepository.findBymobileno(mobileNo);
	}

	@Override
	public Integer updateOtp(LandAppRegistratationEntity user) {
		//return forgotPasswordRepository.save(user);
		String randomNumber = OtpGenerateCommonUtil.generateOTP();
		return nativerepo.updateOtp(user.getId(), randomNumber);
	}
	
	@Override
	public Integer updatepassword(String newPassword, String mobileNo) {
		return nativerepo.updatepassword(newPassword, mobileNo);
	}

}
