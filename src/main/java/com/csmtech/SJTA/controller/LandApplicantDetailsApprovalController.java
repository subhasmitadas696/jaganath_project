package com.csmtech.SJTA.controller;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csmtech.SJTA.dto.LandApplicantDetailsUPDTO;
import com.csmtech.SJTA.service.LandApplicantDetailsApprovalService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/landAppDetails")
@CrossOrigin("*")
@Slf4j
public class LandApplicantDetailsApprovalController {

	@Autowired
	private LandApplicantDetailsApprovalService service;

	@PostMapping("/UnderProcessRecord")
	public ResponseEntity<?> getLandUpRecord(@RequestBody String formParams) {
		JSONObject json = new JSONObject(formParams);
		List<LandApplicantDetailsUPDTO> respones = service.getLandApplicantsWithDetails(json.getString("actionId"));
		if (respones.isEmpty()) {
			JSONObject jsb = new JSONObject();
			jsb.put("status", 404);
			jsb.put("result", "No Record Avaliable ..!!");
			log.info(":: No Record Are return ..!!");
			return ResponseEntity.ok(jsb.toString());
		} else
			log.info(":: Record Are return sucessfully..!!");
		return ResponseEntity.ok(respones);
	}
}
