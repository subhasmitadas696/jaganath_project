package com.csmtech.SJTA.controller;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csmtech.SJTA.dto.ChangePasswordRequest;
import com.csmtech.SJTA.dto.UserDetails;
import com.csmtech.SJTA.entity.User;
import com.csmtech.SJTA.service.UserService;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class DashboardController {

	/**
	 * @author guru.prasad
	 */

	private static final Logger log = LoggerFactory.getLogger(DashboardController.class);
	private static final String STATUS_KEY = "status";
	private static final String MESSAGE_KEY = "message";

	@Value("${sjta.bcryptpassword.secretKey}")
	private String secretkey;

	@Autowired
	private UserService userService;

	@GetMapping("/test")
	public String getdata() {
		return "hello";
	}

	@PostMapping("/changepassword")
	public ResponseEntity<Map<String, Object>> changePassword(@Valid @RequestBody ChangePasswordRequest request,
			HttpServletRequest httpServletRequest, HttpSession session) {

		log.info("change password method start.");

		Map<String, Object> response = new HashMap<>();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		try {
			User user = userService.findByuserIdFalse(request.getUserId());

			System.out.println(user + "**********");
			if (!encoder.matches(secretkey + request.getCurrentPassword(), user.getPassword())) {
				response.put(STATUS_KEY, HttpStatus.UNAUTHORIZED.value());
				response.put(MESSAGE_KEY, "Current password is incorrect.");
			} else if (request.getCurrentPassword().equals(request.getNewPassword())) {
				response.put(STATUS_KEY, HttpStatus.FORBIDDEN.value());
				response.put(MESSAGE_KEY, "New Password should be different from the old password.");
			} else if (!request.getNewPassword().equals(request.getConfirmPassword())) {
				response.put(STATUS_KEY, HttpStatus.BAD_REQUEST.value());
				response.put(MESSAGE_KEY, "Confirm Password does not match with new password.");
			} else {
				String encodedPassword = encoder.encode(secretkey + request.getNewPassword());
				log.info("update password started");
				userService.updateUser(encodedPassword, request.getUpdatedBy(), request.getUserId());

				log.info("password updatation end");
				response.put(STATUS_KEY, HttpStatus.OK.value());
				response.put(MESSAGE_KEY, "Password changed successfully.");
			}

			return ResponseEntity
					.status((int) response.getOrDefault(STATUS_KEY, HttpStatus.INTERNAL_SERVER_ERROR.value()))
					.body(response);
		} catch (Exception e) {
			log.error("Error occurred while changing password: {}", e.getMessage());
			response.put(STATUS_KEY, HttpStatus.BAD_REQUEST.value());
			response.put(MESSAGE_KEY, "Invalid username");
			return ResponseEntity.badRequest().body(response);
		}
	}

	@GetMapping("/current-user")
	public BigInteger getCurrentUser(HttpServletRequest httpServletRequest) {
		String username = httpServletRequest.getUserPrincipal().getName();
		return userService.findByUserName(username).getUserId();
	}

	@PostMapping("/getdetails")
	public ResponseEntity<Map<String, Object>> getDetails(@RequestBody UserDetails userDto,
			HttpServletRequest request) {
		log.info("get details started");
		Map<String, Object> response = new HashMap<>();
		try {
			User user = userService.findByuserIdFalse(userDto.getUserId());

			if (user.getUserId() != null) {
				response.put(STATUS_KEY, HttpStatus.OK.value());
				response.put(MESSAGE_KEY, "User found");
				response.put("Full_Name", user.getFullName());
				response.put("Mobile_No", user.getMobileNo());
				response.put("Email_Id", user.getEmailId());

			} else {
				response.put(STATUS_KEY, HttpStatus.NOT_FOUND);
				response.put(MESSAGE_KEY, "user not found");

			}
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			log.error("Error occurred while getting details: {}", e.getMessage());
			response.put(STATUS_KEY, HttpStatus.BAD_REQUEST.value());
			response.put(MESSAGE_KEY, "Invalid userid");
			return ResponseEntity.badRequest().body(response);
		}
	}

	@PutMapping("/updatedetails")
	public ResponseEntity<Map<String, Object>> updateDetails(@RequestBody UserDetails userDto) {
		log.info("update start");
		Map<String, Object> response = new HashMap<>();
		try {

			if (userDto.getUserId() != null) {
				log.info("updated start");
				userService.updateDetails(userDto.getFullName(), userDto.getEmailId(), userDto.getUpdatedBy(),
						userDto.getUserId());
				log.info("updated end");

				response.put(STATUS_KEY, HttpStatus.OK.value());
				response.put(MESSAGE_KEY, "User details updated");
			} else {
				response.put(STATUS_KEY, HttpStatus.BAD_REQUEST.value());
				response.put(MESSAGE_KEY, "User details not updated");
			}
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			log.error("Error occurred while updating details: {}", e.getMessage());
			response.put(STATUS_KEY, HttpStatus.BAD_REQUEST.value());
			response.put(MESSAGE_KEY, "Server Error");
			return ResponseEntity.badRequest().body(response);
		}
	}
}
