package com.csmtech.SJTA.controller;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csmtech.SJTA.controller.CaptchaStore.Captcha;
import com.csmtech.SJTA.dto.DistrictDTO;
import com.csmtech.SJTA.dto.KhatianPlotDTO;
import com.csmtech.SJTA.dto.TahasilTeamUseRequestDto;
import com.csmtech.SJTA.dto.TahasilDTO;
import com.csmtech.SJTA.dto.VillageInfoDTO;
import com.csmtech.SJTA.service.TahasilTeamUseService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/mainRecord")
@CrossOrigin("*")
@Slf4j
public class TahasilTeamUseController {

	@Autowired
	private TahasilTeamUseService service;

	@Value("${sjta.bcryptpassword.secretKey}")
	private String SECRET_KEY;

	@GetMapping("/distDropDownData")
	public ResponseEntity<?> getDistricts() {
		List<DistrictDTO> respones = service.getDistricts();
		if (respones.isEmpty()) {
			log.info(":: No record return");
			JSONObject jsb = new JSONObject();
			jsb.put("status", 404);
			jsb.put("result", "No record..!!");
			return ResponseEntity.ok(jsb.toString());
		} else
			log.info("Sucess..Execute and return the result");
		return ResponseEntity.ok(respones);
	}

	@PostMapping("/getThasilRecord")
	public ResponseEntity<?> getRecord(@RequestBody String formsParms) {
		JSONObject json = new JSONObject(formsParms);
		List<TahasilDTO> respones = service.getTahasilData(json.getString("distId"));
		if (respones.isEmpty()) {
			log.info(":: No record return");
			JSONObject jsb = new JSONObject();
			jsb.put("status", 404);
			jsb.put("result", "No record..!!");
			return ResponseEntity.ok(jsb.toString());
		}
		log.info("Sucess..Execute and return the result");
		JSONObject jsb = new JSONObject();
		jsb.put("status", 200);
		jsb.put("result", respones);
		return ResponseEntity.ok(jsb.toString());
		// return ResponseEntity.ok(respones);
	}

	@PostMapping("/tahasilLogin")
	public ResponseEntity<?> checkLoginSpark(@RequestBody TahasilTeamUseRequestDto dto) {
		String password = dto.getPassword();
		String passpasswordPass;
		System.out.println(dto.toString());
		// ----------------Captcha Validate Start------------------//

		boolean validateCaptcha;
		Integer captchaResult = CaptchaStoreLogin.get(dto.getCaptchaId());
		if (captchaResult != null && dto.getAnswer() == captchaResult) {
			CaptchaStoreLogin.remove(dto.getCaptchaId());
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
		List<TahasilTeamUseRequestDto> respones = service.checkLogin(dto.getTahasilCode());
		if (respones.isEmpty()) {
			log.info("Fail..Execute");
			JSONObject jsb = new JSONObject();
			jsb.put("status", 404);
			jsb.put("result", "Fail..");
			return ResponseEntity.ok(jsb.toString());
		}

		if (!respones.get(0).getTahasilCode().equalsIgnoreCase(dto.getTahasilCode())
				&& !respones.get(0).getTahasilCode().equalsIgnoreCase(dto.getTahasilcodeselct())) {
			log.info("Fail..Execute");
			JSONObject jsb = new JSONObject();
			jsb.put("status", 404);
			jsb.put("result", "Fail..");
			return ResponseEntity.ok(jsb.toString());
		}

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		boolean isMatch = encoder.matches(SECRET_KEY + password, respones.get(0).getPassword());
		if (!isMatch) {
			log.info("Fail..Execute");
			JSONObject jsb = new JSONObject();
			jsb.put("status", 404);
			jsb.put("result", "Fail..");
			return ResponseEntity.ok(jsb.toString());
		}

		if (respones != null && respones.size() > 0) {
			log.info("Sucess..Execute");
			JSONObject jsb = new JSONObject();
			jsb.put("status", 200);
			jsb.put("result", "Sucess..!");
			return ResponseEntity.ok(jsb.toString());
		}
		log.info("Fail..Execute");
		JSONObject jsb = new JSONObject();
		jsb.put("status", 404);
		jsb.put("result", "Fail..");
		return ResponseEntity.ok(jsb.toString());
	}

	@PostMapping("/getVillageRecord")
	public ResponseEntity<?> getRecordKatha(@RequestBody String formsParms) {
		JSONObject json = new JSONObject(formsParms);
		List<VillageInfoDTO> respones = service.getKathaRecord(json.getString("tahasilCode"));
		if (respones.isEmpty()) {
			log.info(":: No record return");
			JSONObject jsb = new JSONObject();
			jsb.put("status", 404);
			jsb.put("result", "No record..!!");
			return ResponseEntity.ok(jsb.toString());
		}
		log.info("Sucess..Execute and return the result");
		JSONObject jsb = new JSONObject();
		jsb.put("status", 200);
		jsb.put("result", respones);
		return ResponseEntity.ok(jsb.toString());

	}

	@PostMapping("/getVillagekathaRecordMore")
	public ResponseEntity<?> getRecordKathaMore(@RequestBody String formsParms) {
		JSONObject json = new JSONObject(formsParms);
		List<KhatianPlotDTO> respones = service.getKathaRecordMore(json.getString("villageCode"));

		if (respones.isEmpty()) {
			log.info(":: No record return");
			JSONObject jsb = new JSONObject();
			jsb.put("status", 404);
			jsb.put("result", "No record..!!");
			return ResponseEntity.ok(jsb.toString());
		}
		log.info("Sucess..Execute and return the result");
		JSONObject jsb = new JSONObject();
		jsb.put("status", 200);
		jsb.put("result", respones);

		return ResponseEntity.ok(jsb.toString());

	}

	// generate captch
	@GetMapping("/generate")
	public CaptchaLogin generateCaptcha() {
		log.info("Execute  generateCaptcha() Method ..!!");
		int number1 = new Random().nextInt(10);
		int number2 = new Random().nextInt(10);
		String operator = getRandomOperator();
		int result = performOperation(number1, number2, operator);
		String captchaText = number1 + " " + operator + " " + number2 + " = ?";
		String captchaId = UUID.randomUUID().toString();
		CaptchaStoreLogin.put(captchaId, result);
		return new CaptchaLogin(captchaId, captchaText);
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

// -------------match captcha
class CaptchaStoreLogin {
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
}

class CaptchaLogin {
	private String id;
	private String text;

	public CaptchaLogin(String id, String text) {
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
