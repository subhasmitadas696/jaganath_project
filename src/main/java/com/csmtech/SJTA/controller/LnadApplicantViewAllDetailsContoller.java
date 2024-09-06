package com.csmtech.SJTA.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.csmtech.SJTA.dto.LandAppResponseStructureDTO;
import com.csmtech.SJTA.service.LnadApplicantViewAllDetailsService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/mainView")
@CrossOrigin("*")
@Slf4j
public class LnadApplicantViewAllDetailsContoller {

	@Autowired
	private LnadApplicantViewAllDetailsService service;

	@PostMapping("/getlandAppDetails")
	public ResponseEntity<?> getApplicantRecord(@RequestBody String formParams) {
		JSONObject json = new JSONObject(formParams);
		LandAppResponseStructureDTO respones = service.getCombinedDataForApplicant(json.getBigInteger("intId"));
		if (respones.getAppdto()!= null) {
			log.info(" :: getApplicantRecord() method are return the result ..!!");
			return ResponseEntity.ok(respones);
		} else {

			log.info(" :: getApplicantRecord() method are return the result (No Record Are Found) ..!!");
			JSONObject jsb = new JSONObject();
			jsb.put("status", 401);
			jsb.put("result", "No Record Foud");

			return ResponseEntity.ok(jsb.toString());
		}
	}
}
