package com.csmtech.SJTA.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csmtech.SJTA.dto.DistrictCode;
import com.csmtech.SJTA.service.BannerDataService;

@RestController
@RequestMapping("/api/banner")
@CrossOrigin("*")
public class BannerDataController {

	@Autowired
	private BannerDataService bannerDataService;

	private static final Logger log = LoggerFactory.getLogger(BannerDataController.class);
	private static final String STATUS_KEY = "status";
	private static final String MESSAGE_KEY = "message";

	@GetMapping("/districtcodes")
	public ResponseEntity<Map<String, Object>> getDistrictCodes() {
		Map<String, Object> response = new HashMap<>();
		log.info("district code fetch started");
		List<DistrictCode> districtCodes = bannerDataService.getDistrictCodes();
		log.info("district code fetched...");
		JSONObject res = new JSONObject();
		if (districtCodes != null && !districtCodes.isEmpty()) {
			response.put(STATUS_KEY, HttpStatus.OK.value());
			response.put(MESSAGE_KEY, "DATA FOUND");
			response.put("result", districtCodes);
		} else {
			response.put(STATUS_KEY, HttpStatus.OK.value());
			response.put(MESSAGE_KEY, "NO DATA FOUND");
		}
		return ResponseEntity.ok(response);
	}
}
