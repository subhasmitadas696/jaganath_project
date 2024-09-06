package com.csmtech.SJTA.controller;

/**
 * @Auth Prasanta Kumar Sethi
 */

import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.csmtech.SJTA.controller.ForgotPasswordController.CaptchaStoreForgotPassword.CaptchaForgotPassword;
import com.csmtech.SJTA.dto.ForgotPasswordCaptchaFinalRequest;
import com.csmtech.SJTA.dto.ForgotPasswordRequest;
import com.csmtech.SJTA.entity.LandAppRegistratationEntity;
import com.csmtech.SJTA.service.ForgotPasswordService;
import com.csmtech.SJTA.util.OtpGenerateCommonUtil;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/api")
@CrossOrigin("*")
@Slf4j
public class ForgotPasswordController {
	@Autowired
	private ForgotPasswordService forgotPasswordService;
	JSONObject response = new JSONObject();

	@PostMapping("/sendotp")
	public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest request) {
		try {
			LandAppRegistratationEntity user = forgotPasswordService.findByMobileNo(request.getMobileNo());
			if (user.getMobileno().equals(request.getMobileNo())) {
				//String randomNumber = OtpGenerateCommonUtil.generateOTP();
				//user.setOtp(randomNumber);
				forgotPasswordService.updateOtp(user);
				response.put("message", "OTP Sent Successfully");
				response.put("status", 200);
				log.info("inside forgotPassword() in controller");
			}
		} catch (Exception e) {
			response.put("message", "No User Found");
			response.put("status", 401);
		}
		return ResponseEntity.ok(response.toString());
	}

	@PostMapping("/matchotp")
	public ResponseEntity<String> matchOtp(@RequestBody ForgotPasswordRequest request) {
		log.info("inside matchOtp() in controller");
		try {
			LandAppRegistratationEntity user = forgotPasswordService.findByMobileNo(request.getMobileNo());
			if (request.getOtp().equals(user.getOtp())) {
				response.put("message", "OTP matched");
				response.put("status", 200);
			} else {
				response.put("message", "Error Validating OTP");
				response.put("status", 401);
			}

		} catch (Exception e) {
			response.put("message", "Something went wrong");
			response.put("status", 403);
		}
		return ResponseEntity.ok(response.toString());

	}

	@PostMapping("/setnewpassword")
	public ResponseEntity<?> setnewpassword(@RequestBody ForgotPasswordCaptchaFinalRequest request) {
		log.info("inside setnewpassword() in controller");
		String SECRET_KEY = "mySecretKey";
		boolean validateCaptcha;
		Integer captchaResult = CaptchaStoreForgotPassword.get(request.getCaptchaId());
		System.out.println(captchaResult);
		if (captchaResult != null && request.getAnswer() == captchaResult) {
			CaptchaStoreForgotPassword.remove(request.getCaptchaId());
			validateCaptcha = true;
		} else {
			validateCaptcha = false;
		}

		if (!validateCaptcha) {
			JSONObject response = new JSONObject();
			response.put("status", 223);
			response.put("message", "Invalid captcha");
			return ResponseEntity.ok(response.toString());
		}
		try {
			if (request.getNewPassword().equals(request.getConfirmPassword())) {
				BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
				String encodedPassword = encoder.encode(SECRET_KEY + request.getNewPassword());
				forgotPasswordService.updatepassword(encodedPassword, request.getMobileNo());
				response.put("message", "Password Updated Successfully");
				response.put("status", 200);
			} else {
				response.put("message", "Error Updating Password");
				response.put("status", 401);
			}
		} catch (Exception e) {
			response.put("message", "Something went wrong");
			response.put("status", 403);
		}
		return ResponseEntity.ok(response.toString());

	}

	@GetMapping("/generatecaptcha")
	public CaptchaForgotPassword generateCaptcha() {
		log.info("inside generateCaptcha() in controller");
		log.info("Captcha Generated !!");
		Integer firstNumber = new Random().nextInt(10);
		Integer secondNumber = new Random().nextInt(10);

		String operator = getRandomOperatorForgotPassword();
		Integer result = performOperationForgotPassword(firstNumber, secondNumber, operator);
		String captchaText1 = firstNumber + " " + operator + " " + secondNumber + " = ?";
		String captchaId1 = UUID.randomUUID().toString();
		CaptchaStoreForgotPassword.put(captchaId1, result);
		return new CaptchaForgotPassword(captchaId1, captchaText1);
	}

	private String getRandomOperatorForgotPassword() {
		log.info("inside getRandomOperatorForgotPassword() in controller");
		String[] operators = { "+", "*" };
		Integer randomIndex = new Random().nextInt(operators.length);
		return operators[randomIndex];
	}

	private Integer performOperationForgotPassword(int forFirstNumber, int forSecondNumber, String forOperator) {
		log.info("inside performOperationForgotPassword() in controller");
		switch (forOperator) {
		case "+":
			return forFirstNumber + forSecondNumber;
		case "*":
			return forFirstNumber * forSecondNumber;

		default:
			throw new IllegalArgumentException("Invalid operator: " + forOperator);
		}
	}

	static class CaptchaStoreForgotPassword {
		private static Map<String, Integer> captchaMap = new ConcurrentHashMap<>();
		public static void put(String captchaId, int result) {
			captchaMap.put(captchaId, result);
		}

		public static Integer get(String captchaId1) {
			return captchaMap.get(captchaId1);
		}

		public static void remove(String captchaId1) {
			captchaMap.remove(captchaId1);
		}

		public static class CaptchaForgotPassword {
			private String id;
			private String text;

			public CaptchaForgotPassword(String id, String text) {
				this.id = id;
				this.text = text;
			}

			public String getId() {
				return id;
			}

			public void setId(String id) {
				this.id = id;
			}

			public String getText() {
				return text;
			}

			public void setText(String text) {
				this.text = text;
			}
		}
	}
}
