package com.csmtech.SJTA.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csmtech.SJTA.dto.LandAppResponseStructureDTO;
import com.csmtech.SJTA.dto.LandApplicantDTO;
import com.csmtech.SJTA.dto.LandPaginationDTO;
import com.csmtech.SJTA.dto.PaginationInRegisterDtoResponse;
import com.csmtech.SJTA.service.LandApplicantService;

import lombok.extern.slf4j.Slf4j;

/*
 * @Auth  GuruPrasad
 */

@RestController
@RequestMapping("/api/landservices")
@CrossOrigin("*")
@Slf4j
public class ViewLandApplicationController {

	@Autowired
	private LandApplicantService landApplicantService;

	private static final Logger log = LoggerFactory.getLogger(ViewLandApplicationController.class);
	private static final String STATUS_KEY = "status";
	private static final String MESSAGE_KEY = "message";


	@PostMapping("/searchpagination")
	public ResponseEntity<?> getPaginationSearchData(@RequestBody PaginationInRegisterDtoResponse res) {
		System.out.println(res.toString());
		LandPaginationDTO getdtodata = landApplicantService.getSearchLandApplicantDetailsPage(res.getRoleId(),
				res.getApplicantName(), res.getPageNumber(), res.getPageSize(), res.getSerarchUniqueId(),
				res.getSerachPlotNo(), res.getPageType());
		//Integer countint = landApplicantService.getTotalApplicantCount();
		if (getdtodata==null) {
			log.info(":: getPaginationSearchData() execution Sucess No Record Found..!!");
			JSONObject response = new JSONObject();
			response.put("status", 404);
			response.put("result", "No Record Found");
			return ResponseEntity.ok(response.toString());
		} else
			log.info(":: getPaginationSearchData() execution Sucess ..!!");
		return ResponseEntity.ok(getdtodata);
	}

	@PostMapping("/viewmore")
	public ResponseEntity<Map<String, Object>> getDetails(@RequestBody LandApplicantDTO landDto,
			HttpServletRequest request) {
		log.info("get land details started");
		Map<String, Object> response = new HashMap<>();
		try {
			LandAppResponseStructureDTO land = landApplicantService
					.findAllDetailsBylandApplicantId(landDto.getLandApplicantId());

			if (land != null) {
				response.put(STATUS_KEY, HttpStatus.OK.value());
				response.put(MESSAGE_KEY, "Land Details found");
				response.put("Land_Details", land);

			} else {
				response.put(STATUS_KEY, HttpStatus.NOT_FOUND.value());
				response.put(MESSAGE_KEY, "Land Details not found");
			}
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			log.error("Error occurred while getting details: {}", e.getMessage());
			response.put(STATUS_KEY, HttpStatus.BAD_REQUEST.value());
			response.put(MESSAGE_KEY, "Invalid landId");
			return ResponseEntity.badRequest().body(response);
		}
	}

}
