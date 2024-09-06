package com.csmtech.SJTA.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csmtech.SJTA.dto.AppliedLandApplicationDTO;
import com.csmtech.SJTA.dto.AppliedLandPaginationDTO;
import com.csmtech.SJTA.dto.LandAppResponseStructureDTO;
import com.csmtech.SJTA.dto.LandApplicantDTO;
import com.csmtech.SJTA.dto.LandPaginationDTO;
import com.csmtech.SJTA.dto.PaginationInRegisterDtoResponse;
import com.csmtech.SJTA.repository.AppliedLandApplicationNativeRepository;
import com.csmtech.SJTA.service.AppliedLandApplicationService;

import lombok.extern.slf4j.Slf4j;

/**
 * @Auth Prasanta Kumar Sethi
 */

@RestController
@RequestMapping("/land")
@CrossOrigin("*")
@Slf4j
public class AppliedLandApplicationController {

	private static final String STATUS_KEY = "status";
	private static final String MESSAGE_KEY = "message";

	@Autowired
	private AppliedLandApplicationService appliedLandApplicationService;

	@PostMapping("/appliedlanddetails")
	public ResponseEntity<AppliedLandPaginationDTO> getPaginationSearchData(
			@RequestBody PaginationInRegisterDtoResponse res) {
		try {
			log.info("inside AppliedLandApplicationController !!!");
			log.info("getPaginationSearchData started !!");
			// Integer countint = appliedLandApplicationService.getTotalApplicantCount(res);
			AppliedLandPaginationDTO getdtodata = appliedLandApplicationService
					.getLandApplicantDetailsPage(res.getCreatedBy(), res.getPageNumber(), res.getPageSize());
			log.info("AppliedLandApplicationController !!! executed successfully !!");
			// return ResponseEntity.ok(new AppliedLandPaginationDTO(getdtodata, countint));
			return ResponseEntity.ok(getdtodata);
		} catch (Exception e) {
			log.error("An error occurred in the pagination search: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

	@PostMapping("/cancelApplication")
	public ResponseEntity<String> cancelApplication(@RequestBody String formParams) {
		JSONObject json = new JSONObject(formParams);
		
		
		Integer cancelled = appliedLandApplicationService.cancelApplication(json.getBigInteger("intId"),json.getString("remark"));
		if(cancelled>0) {
			JSONObject jsb = new JSONObject();
			jsb.put("status", 200);
			jsb.put("result", "Record Update");
			return ResponseEntity.ok(jsb.toString());
		}
		JSONObject jsb = new JSONObject();
		jsb.put("status", 501);
		jsb.put("result", "No Record Are Update");
		return ResponseEntity.ok(jsb.toString());

	}
}
