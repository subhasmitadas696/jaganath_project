package com.csmtech.SJTA.controller;

import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.csmtech.SJTA.controller.CaptchaStoreUserRegister.CaptchaUserRegister;
import com.csmtech.SJTA.dto.LandAppRegistratationDTO;
import com.csmtech.SJTA.dto.LandRegisterMobileNoOrOtpDTO;
import com.csmtech.SJTA.dto.LandRegisterMobileNoOrOtpVerifiedDTO;
import com.csmtech.SJTA.repository.LandAppRegistratationClassRepository;
import com.csmtech.SJTA.service.LandAppRegistratationService;
import com.csmtech.SJTA.util.MailSendUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/userregister")
@CrossOrigin("*")
@Slf4j
public class LandAppRegistratationController {

	@Autowired
	private LandAppRegistratationService service;

	@Autowired
	private LandAppRegistratationClassRepository repo;

	@PostMapping("/saveuser")
	public ResponseEntity<?> saveUserRecord(@RequestBody LandAppRegistratationDTO dto) {

		// captcha validation
		System.out.println(dto.toString());
		boolean validateCaptcha;
		Integer captchaResult = CaptchaStoreUserRegister.get(dto.getCaptchaId());
		System.out.println(captchaResult);
		System.out.println(dto.getAnswer());
		if (captchaResult != null && dto.getAnswer() == captchaResult) {
			CaptchaStoreUserRegister.remove(dto.getCaptchaId());
			validateCaptcha = true;
		} else {
			validateCaptcha = false;
		}

		if (!validateCaptcha) {
			JSONObject response = new JSONObject();
			response.put("status", 223);
			response.put("result", "Invalid captcha");
			return ResponseEntity.ok(response.toString());
		}

		Integer existCheckRecord = repo.getUserCountByMobileAndEmail(dto.getMobileno());
		Integer dataSave = null;
		Boolean checkCorrectPassword = dto.getPassword().equals(dto.getConformPassword());

		log.info("Start The saveUserRecord() method execution ");
		if (existCheckRecord == 0) {
			if (Boolean.TRUE.equals(checkCorrectPassword)) {
				dataSave = service.saveUserData(dto);
				if (dataSave == 1) {
					//
					log.info("  Data Saved Sucess.. ");
					JSONObject response = new JSONObject();
					response.put("status", 200);
					response.put("result", "Data save Sucess..!!");
					return ResponseEntity.ok(response.toString());
				} else {
					log.info("  Data Not  Saved .. ");
					JSONObject response = new JSONObject();
					response.put("status", 500);
					response.put("result", "Data Not Saves..!");
					return ResponseEntity.ok(response.toString());
				}
			} else {
				log.info("  Password Not Match  .. ");
				JSONObject response = new JSONObject();
				response.put("status", 110);
				response.put("result", "Password Not Match");
				return ResponseEntity.ok(response.toString());
			}

		} else {
			log.info("  Data Already Present Duplicate Entry .. ");
			JSONObject response = new JSONObject();
			response.put("status", 111);
			response.put("result", "Data Exist");
			return ResponseEntity.ok(response.toString());
		}

	}

	@SuppressWarnings("rawtypes")
	@PostMapping("/savemobileno")
	public ResponseEntity insertMobileNoInFirstRegister(@RequestBody LandRegisterMobileNoOrOtpDTO dto) {
		Integer counFirstRegisterMobileNo = repo.getUserCountByMobileAndEmailFirstRegisterTab(dto.getMobileno());
		if (counFirstRegisterMobileNo == 0) {
			Integer countTempData = repo.getUserCountByMobileAndEmailFirstRegisterTabTemp(dto.getMobileno());
			Integer save1;
			if (countTempData > 0) {
				save1 = service.UpdateRegisterUserMobileNoOrOtp(dto.getMobileno());
			} else {
				save1 = service.saveRegisterUserMobileNoOrOtp(dto.getMobileno());
			}

			JSONObject response = new JSONObject();
			response.put("status", 200);
			response.put("Respones", save1);
			response.put("result", "Verify Sucess ..");
			return ResponseEntity.ok(response.toString());
		} else {
			JSONObject response = new JSONObject();
			response.put("status", 500);
			response.put("result", "Mobile No Already Present Duplicate Entry ..");
			return ResponseEntity.ok(response.toString());
		}

	}

	@SuppressWarnings("rawtypes")
	@PostMapping("/updatemobilenos")
	public ResponseEntity updateMobileNoInFirstRegister(@RequestBody LandRegisterMobileNoOrOtpDTO dto) {
		Integer counFirstRegisterMobileNo = repo.getUserCountByMobileAndEmailFirstRegisterTabTemp(dto.getMobileno());
		if (counFirstRegisterMobileNo == 1) {
			Integer res2 = service.UpdateRegisterUserMobileNoOrOtp(dto.getMobileno());
			JSONObject response = new JSONObject();
			response.put("status", 200);
			response.put("result", "Otp Save Sucess " + res2);
			return ResponseEntity.ok(response.toString());
		} else {
			JSONObject response = new JSONObject();
			response.put("status", 500);
			response.put("result", "Mobile No Not Exist ");
			return ResponseEntity.ok(response.toString());
		}
	}

	@PostMapping("/varifiedotp")
	public ResponseEntity getOtpVerified(@RequestBody LandRegisterMobileNoOrOtpVerifiedDTO dto) {
		String respones = service.getOTPByMobileNo(dto.getMobileno());
		if (dto.getOtp().equals(respones)) {
			JSONObject response = new JSONObject();
			response.put("status", 200);
			response.put("result", "Otp Validate Sucess ");
			return ResponseEntity.ok(response.toString());
		} else {
			JSONObject response = new JSONObject();
			response.put("status", 500);
			response.put("result", "Otp Not Match ");
			return ResponseEntity.ok(response.toString());
		}
	}

	/////////////////////////// Generate Captcha And Captcha
	/////////////////////////// ///////////////////////////////////////////////////
	@GetMapping("/generatecaptcha")
	public CaptchaUserRegister generateCaptcha() {
		log.info("Execute  generateCaptcha() Method ..!!");
		int number1 = new Random().nextInt(10);
		int number2 = new Random().nextInt(10);
		String operator = getRandomOperator();
		int result = performOperation(number1, number2, operator);
		String captchaText = number1 + " " + operator + " " + number2 + " = ?";
		String captchaId = UUID.randomUUID().toString();
		CaptchaStoreUserRegister.put(captchaId, result);
		return new CaptchaUserRegister(captchaId, captchaText);
	}

	private String getRandomOperator() {
		String[] operators = { "+", "*" };
		int randomIndex = new Random().nextInt(operators.length);
		return operators[randomIndex];
	}

	private int performOperation(int number1, int number2, String operator) {
		switch (operator) {
		case "+":
			return number1 + number2;
		case "*":
			return number1 * number2;

		default:
			throw new IllegalArgumentException("Invalid operator: " + operator);
		}
	}

}

//use the separate class hear
class CaptchaStoreUserRegister {
	private static Map<String, Integer> captchaMap = new ConcurrentHashMap<>();

	public static void put(String captchaId, int result) {
		captchaMap.put(captchaId, result);
	}

	public static Integer get(String captchaId) {
		return captchaMap.get(captchaId);
	}

	public static void remove(String captchaId) {
		captchaMap.remove(captchaId);
	}

	public static class CaptchaUserRegister {
		private String id;
		private String text;

		public CaptchaUserRegister(String id, String text) {
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
