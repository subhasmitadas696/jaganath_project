package com.csmtech.SJTA.controller;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csmtech.SJTA.controller.CaptchaStore.Captcha;
import com.csmtech.SJTA.entity.AuthRequest;
import com.csmtech.SJTA.entity.LandAppRegistratationEntity;
import com.csmtech.SJTA.repository.LandAppRegistratationRepository;
import com.csmtech.SJTA.repository.LandApplicantLoginClassRepository;
import com.csmtech.SJTA.util.JwtUtil;
import com.csmtech.SJTA.util.MathCaptcha;
import lombok.extern.slf4j.Slf4j;

/**
 * @Auth Rashmi Ranjan Jena
 */

@RestController
@RequestMapping("/landAppregistratation")
@CrossOrigin("*")
@Slf4j
public class LandAppLoginController {

	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private MathCaptcha mathCaptcha;

	@Autowired
	private LandAppRegistratationRepository repo;
	
	@Autowired
	private LandApplicantLoginClassRepository loginrepo;
	

	@Value("${sjta.bcryptpassword.secretKey}")
	private String SECRET_KEY;

	// Test To I Create The welcome Method
	@SuppressWarnings("rawtypes")
	@GetMapping("/authreq1")
	public ResponseEntity welcome() {

		// Retrieve the authenticated user-name
		@SuppressWarnings("unused")
		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		JSONObject response = new JSONObject();
		response.put("status", 200);
		response.put("result", "Welcome");
		return ResponseEntity.ok(response.toString());

	}

	// method will work generate Token and login
	@SuppressWarnings({ "unused", "rawtypes" })
	@PostMapping("/registratationtookan")
	public ResponseEntity generateToken(@RequestBody AuthRequest authRequest) throws Exception {

		// ----------------Captcha Validate Start------------------//

		boolean validateCaptcha;
		Integer captchaResult = CaptchaStore.get(authRequest.getCaptchaId());
		if (captchaResult != null && authRequest.getAnswer() == captchaResult) {
			CaptchaStore.remove(authRequest.getCaptchaId());
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

		// ---------------Captcha Validate End-------------------//

		log.info(" ::generateToken method are start");

		String matchPassword = null;
		LandAppRegistratationEntity getalldata = repo.findBymobileno(authRequest.getMobileno());
		String password = authRequest.getPassword();
		
		//hear the check user are active or not
		Integer count=repo.findBymobilenoBlockOrNot(authRequest.getMobileno());
		if(count==0) {
			 JSONObject response = new JSONObject();
				response.put("status", 444);
				response.put("result", "User block contact Admin");
				return ResponseEntity.ok(response.toString());
		}
		
		
		//Retrieve the roll form the user
		List<Long> rollid=loginrepo.findRoleIdsByUserId(getalldata.getId());
		

		// Check password
		 if (getalldata == null) {
			 JSONObject response = new JSONObject();
				response.put("status", 432);
				response.put("result", "Invalid username/password");
				return ResponseEntity.ok(response.toString());
		    }
		 System.out.println(getalldata.toString());

		 
		String providedPassword = authRequest.getPassword();
		String storedPassword = getalldata.getPassword();

		if (providedPassword == null && storedPassword == null && !storedPassword.equals(providedPassword)) {
			JSONObject response = new JSONObject();
			response.put("status", 500);
			response.put("result", "Invalid username/password");
			return ResponseEntity.ok(response.toString());
		}

		String hashedPassword = getalldata.getPassword();

		log.info(" ::BCryptPasswordEncoder start the matching ");
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String generatedBcryptHexeash = encoder.encode(SECRET_KEY + password);

		boolean isMatch = encoder.matches(SECRET_KEY + password, hashedPassword);
		if (isMatch) {
			matchPassword = getalldata.getPassword();
		} else {
			matchPassword = "Invalid Password";
		}
		log.info(" :: isMatch is validate sucesfully");
		try {

			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getMobileno(), matchPassword));

		} catch (Exception ex) {
			JSONObject response = new JSONObject();
			response.put("status", 500);
			response.put("result", "Invalid username/password");

			return ResponseEntity.ok(response.toString());

		}

		log.info(" :: Execution end tookan return");
		JSONObject response = new JSONObject();
		response.put("status", 200);
		response.put("fullName", getalldata.getFullName());
		response.put("userId", getalldata.getId());
		response.put("userType", getalldata.getUserType());
		response.put("userRollId", rollid.get(0));
		response.put("result", jwtUtil.generateToken(authRequest.getMobileno()));

		return ResponseEntity.ok(response.toString());
	}

	@GetMapping("/generate")
	public Captcha generateCaptcha() {
		log.info("Execute  generateCaptcha() Method ..!!");
		int number1 = new Random().nextInt(10);
		int number2 = new Random().nextInt(10);
		String operator = getRandomOperator();
		int result = performOperation(number1, number2, operator);
		String captchaText = number1 + " " + operator + " " + number2 + " = ?";
		String captchaId = UUID.randomUUID().toString();
		CaptchaStore.put(captchaId, result);
		return new Captcha(captchaId, captchaText);
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

//The Captcha Are Used To The Login Only Do Not Use The api In Other area (U want to use Copy The Code And Use ur End)
//Because the ConcurrentHashMap will create the memory and remove the memory for the login page time

//---------------------------------------------------------------------------------------//---------------------------------------------//
class CaptchaStore {
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

	public static class Captcha {
		private String id;
		private String text;

		public Captcha(String id, String text) {
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
	// ---------------------------------------------------------------------------------------//---------------------------------------------//

}
