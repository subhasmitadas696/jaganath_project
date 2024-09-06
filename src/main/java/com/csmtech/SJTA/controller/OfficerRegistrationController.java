package com.csmtech.SJTA.controller;

import com.csmtech.SJTA.dto.AddOfficerDTO;
import com.csmtech.SJTA.dto.LandPaginationDTO;
import com.csmtech.SJTA.dto.OfficerPaginationDTO;
import com.csmtech.SJTA.dto.PaginationInRegisterDtoResponse;
import com.csmtech.SJTA.dto.UserDetails;
import com.csmtech.SJTA.entity.Department;
import com.csmtech.SJTA.entity.MRoleEntity;
import com.csmtech.SJTA.entity.User;
import com.csmtech.SJTA.service.OfficerRegistrationService;
import com.csmtech.SJTA.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/officer")
@CrossOrigin("*")
public class OfficerRegistrationController {

	/**
	 * @author guru.prasad
	 */

	@Autowired
	private UserService userService;

	@Autowired
	private OfficerRegistrationService officerRegisterService;

	private static final Logger log = LoggerFactory.getLogger(OfficerRegistrationController.class);
	private static final String STATUS_KEY = "status";
	private static final String MESSAGE_KEY = "message";

	@GetMapping("/test")
	public String test() {
		return "testing..";
	}

	@PostMapping("/getalldept")
	public ResponseEntity<Map<String, Object>> getAllDept() {
		Map<String, Object> response = new HashMap<>();
		List<Department> list = officerRegisterService.getAllDept();
		if (list != null && !list.isEmpty()) {
			response.put(STATUS_KEY, HttpStatus.OK.value());
			response.put(MESSAGE_KEY, "DATA FOUND");
			response.put("result", list);
		} else {
			response.put(STATUS_KEY, HttpStatus.NOT_FOUND.value());
			response.put(MESSAGE_KEY, "NO DATA FOUND");
		}
		return ResponseEntity.ok(response);
	}

	@PostMapping("/getallrole")
	public ResponseEntity<Map<String, Object>> getAllRoleByDepartment(@RequestBody MRoleEntity role) {
		Map<String, Object> response = new HashMap<>();
		List<MRoleEntity> list = officerRegisterService.getAllRoleByDepartment(role.getDepartmentId());
		if (list != null && !list.isEmpty()) {
			response.put(STATUS_KEY, HttpStatus.OK.value());
			response.put(MESSAGE_KEY, "DATA FOUND");
			response.put("result", list);
		} else {
			response.put(STATUS_KEY, HttpStatus.NOT_FOUND.value());
			response.put(MESSAGE_KEY, "NO DATA FOUND");
		}
		return ResponseEntity.ok(response);
	}

	@PostMapping("/register")
	public ResponseEntity<Map<String, Object>> registerOfficer(@RequestBody UserDetails officerDTO,
			HttpServletRequest httpServletRequest) {
		Map<String, Object> response = new HashMap<>();

		System.out.println(officerDTO + "********");

		if (officerRegisterService.checkExistingRecord(officerDTO.getMobileNo()) == 0) {
			log.info("data checked");
			Integer registerOfficer = officerRegisterService.registerOfficer(officerDTO);
			if (registerOfficer == 1) {
				response.put(STATUS_KEY, HttpStatus.OK.value());
				response.put(MESSAGE_KEY, "Officer Registered Successfully");
			} else {
				response.put(STATUS_KEY, HttpStatus.NO_CONTENT.value());
				response.put(MESSAGE_KEY, "not saved");
			}
		} else {
			response.put(STATUS_KEY, HttpStatus.BAD_REQUEST.value());
			response.put(MESSAGE_KEY, "You have already registered.");
		}
		return ResponseEntity.ok(response);
	}

	@PostMapping("/viewallofficer")
	public ResponseEntity<Map<String, Object>> viewAllOfficer(@RequestBody PaginationInRegisterDtoResponse res) {
		Map<String, Object> response = new HashMap<>();

		try {
			Integer countint = officerRegisterService.getTotalOfficerCount();
			List<AddOfficerDTO> data = officerRegisterService.getOfficerInfo(res.getPageNumber(), res.getPageSize(),
					res.getFullName());

			response.put(STATUS_KEY, HttpStatus.OK.value());
			response.put(MESSAGE_KEY, "Data retrieved successfully");
			response.put("result", new OfficerPaginationDTO(data, countint));

			return ResponseEntity.ok(response);
		} catch (Exception e) {
			response.put(STATUS_KEY, HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.put(MESSAGE_KEY, "An error occurred while processing the request");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@PostMapping("/getonerecord")
	public ResponseEntity<Map<String, Object>> getDetails(@RequestBody UserDetails userDto,
			HttpServletRequest request) {
		log.info("get details started");
		Map<String, Object> response = new HashMap<>();
		try {
			User user = userService.findByuserId(userDto.getUserId());

			if (user != null) {
				response.put(STATUS_KEY, HttpStatus.OK.value());
				response.put(MESSAGE_KEY, "Officer found");
				response.put("User_Details", user);

			} else {
				response.put(STATUS_KEY, HttpStatus.NOT_FOUND.value());
				response.put(MESSAGE_KEY, "Officer not found");
			}
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			log.error("Error occurred while getting details: {}", e.getMessage());
			response.put(STATUS_KEY, HttpStatus.BAD_REQUEST.value());
			response.put(MESSAGE_KEY, "Invalid userid");
			return ResponseEntity.badRequest().body(response);
		}
	}

	@PostMapping("/updatedetails")
	public ResponseEntity<Map<String, Object>> updateDetails(@RequestBody UserDetails officerDto) {
		Map<String, Object> response = new HashMap<>();
		try {
			if (officerDto.getUserId() != null) {
				officerRegisterService.updateDetails(officerDto.getFullName(), officerDto.getMobileNo(),
						officerDto.getEmailId(), officerDto.getDepartmentId(), officerDto.getRoleId(),
						officerDto.getUpdatedBy(), officerDto.getUserId(), officerDto.getCreatedBy());

				response.put(STATUS_KEY, HttpStatus.OK.value());
				response.put(MESSAGE_KEY, "Officer details updated");
			} else {
				response.put(STATUS_KEY, HttpStatus.BAD_REQUEST.value());
				response.put(MESSAGE_KEY, "Officer details not updated");
			}
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			response.put(STATUS_KEY, HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.put(MESSAGE_KEY, "Server Error");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@PostMapping("/deleterecord")
	public ResponseEntity<Map<String, Object>> deleteRecord(@RequestBody UserDetails officerDto) {
		log.info("delete started");
		Map<String, Object> response = new HashMap<>();
		try {
			if (officerDto.getUserId() != null) {
				log.info("delete operation started");
				officerRegisterService.deleteRecord(officerDto.getCreatedBy(), officerDto.getUserId());
				log.info("deletion completed");
				response.put(STATUS_KEY, HttpStatus.OK.value());
				response.put(MESSAGE_KEY, "Officer deleted");
			} else {
				response.put(STATUS_KEY, HttpStatus.BAD_REQUEST.value());
				response.put(MESSAGE_KEY, "Officer not deleted");
			}
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			log.error("Error occurred while getting details: {}", e.getMessage());

			response.put(STATUS_KEY, HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.put(MESSAGE_KEY, "Server Error");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}
}
