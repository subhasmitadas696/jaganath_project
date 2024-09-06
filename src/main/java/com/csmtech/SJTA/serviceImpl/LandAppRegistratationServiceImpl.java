package com.csmtech.SJTA.serviceImpl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.csmtech.SJTA.dto.LandAppRegistratationDTO;
import com.csmtech.SJTA.entity.RoleInfo;
import com.csmtech.SJTA.repository.LandAppRegistratationClassRepository;
import com.csmtech.SJTA.repository.LandAppRegistratationRepository;
import com.csmtech.SJTA.service.LandAppRegistratationService;
import com.csmtech.SJTA.util.MailSendUtil;
import com.csmtech.SJTA.util.OtpGenerateCommonUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LandAppRegistratationServiceImpl implements LandAppRegistratationService {

	@SuppressWarnings("unused")
	@Autowired
	private LandAppRegistratationRepository landrepo;

	@Autowired
	private LandAppRegistratationClassRepository landclassrepo;

	@Autowired
	private MailSendUtil mailutil;

	// use the key
	@Value("${sjta.bcryptpassword.secretKey}")
	private String SECRET_KEY;

	@Override
	public Integer saveUserData(LandAppRegistratationDTO registerdto) {

		// main cfgs
		Integer amilDeliverCount;
		String subject = " SJTA  Puri Odisha  ..!! ";
		String content = " <!DOCTYPE html>  " + " <html>  " + "<head>  "
				+ "    <title> Welcome to Shree Jagnath Land Management Information</title>   </head> " + "    <body>  "
				+ "    <h2> Hello  " + registerdto.getFullName() + "  ,</h2>  "
				+ "    <p> Thank you for registering with Shree Jagnath Land Management Information. We are excited to have you on board! </p> "
				+ "    <p> You can now access our platform and explore various land management features. </p> "
				+ "    <p> If you have any questions or need assistance, feel free to reach out to our support team.</p>  "
				+ "    <p> Best regards,</p>     " + "    <p> The Shree Jagnath Land Management Team</p>  "
				+ "    </body> " + "</html>  ";

		if (registerdto.getEmailId() == "") {
			log.info("Use Are Not Given The Mail So Mail Service Not Work ");
		} else {
			amilDeliverCount = mailutil.sendEmail(registerdto.getEmailId(), content, subject,"","");
			log.info("Mail Send Sucess " + registerdto.getEmailId());
		}

		// user_name,password, full_name, mobile_no, email_id, otp, user_type,
		// created_by, created_on
		String username = registerdto.getMobileno();
		String password = registerdto.getPassword();
		String fullName = registerdto.getFullName();
		String mobileNo = registerdto.getMobileno();
		String email = registerdto.getEmailId();
		String otp = OtpGenerateCommonUtil.generateOTP();

		// encoder the password
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedValue = encoder.encode(SECRET_KEY + password);

		// get The Data Of roll_tab
		List<RoleInfo> roleInfoList = landclassrepo.getRoleInfoList("CITIZEN");
		String userType = "";
		String roleId = "";

		if (!roleInfoList.isEmpty()) {
			RoleInfo roleInfo = roleInfoList.get(0);

			userType = "C";
			roleId = String.valueOf(roleInfo.getRoleId());
		}

		return landclassrepo.insertUserWithRole(username, encodedValue, fullName, mobileNo, email, otp, userType,
				roleId);

	}

	// vaerified user mobile first
	@Override
	public Integer saveRegisterUserMobileNoOrOtp(String mobileNO) {
		String otp = OtpGenerateCommonUtil.generateOTP();
		return landclassrepo.insertMobileAndOTP(mobileNO, otp);
	}

	@Override
	public Integer UpdateRegisterUserMobileNoOrOtp(String mobileNO) {
		String otp = OtpGenerateCommonUtil.generateOTP();
		return landclassrepo.updateMobileNoOrOtp(mobileNO, otp);
	}

	@Override
	public String getOTPByMobileNo(String mobileno) {
		return landclassrepo.getOTPByMobileNo(mobileno);
	}

}
