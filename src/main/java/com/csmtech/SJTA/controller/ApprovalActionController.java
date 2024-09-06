package com.csmtech.SJTA.controller;

import java.math.BigInteger;
import java.util.List;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csmtech.SJTA.dto.ApprovalActionResultDTO;
import com.csmtech.SJTA.dto.ApprovalActionUpdateDTO;
import com.csmtech.SJTA.service.ApprovalActionService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/approvalAction")
@CrossOrigin("*")
@Slf4j
public class ApprovalActionController {

	@Autowired
	private ApprovalActionService approvalService;

	@PostMapping("/actionsdata")
	public ResponseEntity<?> getAction(@RequestBody String formParams) {
		JSONObject json = new JSONObject(formParams);
		List<ApprovalActionResultDTO> respones = approvalService.findApprovalActionsForRoleId(json.getLong("roleId"));
		if (respones.isEmpty()) {
			log.info(":: no record found..!!");
			JSONObject jsb = new JSONObject();
			jsb.put("status", 404);
			jsb.put("result", "No Record Found");
			return ResponseEntity.ok(jsb.toString());
		}
		log.info(":: Record Are return sucessfully..!!");
		return ResponseEntity.ok(respones);
	}

	@PostMapping("/updateAprovalAction")
	public ResponseEntity<?> updateApprovalProcss(@RequestBody ApprovalActionUpdateDTO approvalDto) {
		Short result = approvalService.updateApprovalProcss(approvalDto);
		//message data
		String messagedata=approvalService.messageData(approvalDto.getNewApprovalActionId());
		JSONObject jsb = new JSONObject();
		jsb.put("status", 200);
		jsb.put("result", result);
		jsb.put("message", messagedata);
		return ResponseEntity.ok(jsb.toString());
	}
}
