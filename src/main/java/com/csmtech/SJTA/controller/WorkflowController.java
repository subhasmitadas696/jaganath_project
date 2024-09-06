package com.csmtech.SJTA.controller;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.csmtech.SJTA.service.WorkflowService;
import com.csmtech.SJTA.util.TokenCreaterAndMatcher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
@CrossOrigin(origins = "*")
public class WorkflowController {

	@Autowired
	private WorkflowService workflowService;

	@Autowired
	private TokenCreaterAndMatcher tokenCreater;

	@GetMapping(value = "/getallApprovalAction", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getallApprovalAction() {
		JSONObject response = new JSONObject();
		List<Map<String, Object>> getallOfficers = null;
		try {
			getallOfficers = workflowService.getallApprovalAction();
			response.put("status", 200);
			response.put("msg", "success");
			response.put("result", getallOfficers);
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("RESPONSE_DATA", Base64.getEncoder().encodeToString(response.toString().getBytes()));
		jsonObject.put("RESPONSE_TOKEN", TokenCreaterAndMatcher
				.getHmacMessage(Base64.getEncoder().encodeToString(response.toString().getBytes())));
		return ResponseEntity.ok(jsonObject.toString());

	}

	@GetMapping(value = "/getallOfficersApi", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getallOfficersApi() {
		JSONObject response = new JSONObject();

		List<Map<String, Object>> getallOfficers = null;
		try {
			getallOfficers = workflowService.getallOfficersApi();
			response.put("status", 200);
			response.put("msg", "success");
			response.put("result", getallOfficers);

			// String encodedResponseObject =
			// Base64.getEncoder().encodeToString(response.toString().getBytes());
			// jsonObject.put("RESPONSE_DATA", encodedResponseObject);
		} catch (Exception e) {
			e.printStackTrace();
		}

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("RESPONSE_DATA", Base64.getEncoder().encodeToString(response.toString().getBytes()));
		jsonObject.put("RESPONSE_TOKEN", TokenCreaterAndMatcher
				.getHmacMessage(Base64.getEncoder().encodeToString(response.toString().getBytes())));
		return ResponseEntity.ok(jsonObject.toString());
	}

	@PostMapping("/setWorkflow")
	public ResponseEntity<?> setWorkflow(@RequestBody String setWorkflow)
			throws JsonMappingException, JsonProcessingException {
		Integer errorFlag;
		JSONObject jsonObject = new JSONObject();
		String result = workflowService.setWorkflow(setWorkflow);
		if (result.equals("success")) {
			errorFlag = 0;
			// object.put("msg", "success");
		} else {
			errorFlag = 1;
		}

		jsonObject.put("RESPONSE_DATA", Base64.getEncoder().encodeToString(errorFlag.toString().getBytes()));
		jsonObject.put("RESPONSE_TOKEN", TokenCreaterAndMatcher
				.getHmacMessage(Base64.getEncoder().encodeToString(errorFlag.toString().getBytes())));
		return ResponseEntity.ok(jsonObject.toString());

	}

	@GetMapping(value = "/getFormName", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getFormName() {
		JSONObject response = new JSONObject();
		List<Map<String, Object>> getallOfficers = null;
		try {
			getallOfficers = workflowService.getFormName();
			response.put("status", 200);
			response.put("msg", "success");
			response.put("result", getallOfficers);
		} catch (Exception e) {
			e.printStackTrace();
			response.put("msg", "error");
			response.put("status", 400);
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("RESPONSE_DATA", Base64.getEncoder().encodeToString(response.toString().getBytes()));
		jsonObject.put("RESPONSE_TOKEN", TokenCreaterAndMatcher
				.getHmacMessage(Base64.getEncoder().encodeToString(response.toString().getBytes())));
		return ResponseEntity.ok(jsonObject.toString());

	}

	@PostMapping(value = "/fillWorkflow", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> fillWorkflow(@RequestParam("arrParam[serviceId]") String request,
			@RequestParam("arrParam[projectId]") String projectId,
			@RequestParam("arrParam[paymentType]") String paymentType,
			@RequestParam("arrParam[labelId]") String labelId, @RequestParam("arrParam[ctrlName]") String ctrlName,
			@RequestParam("arrParam[projectCategory]") String projectCategory,
			@RequestParam("arrParam[dynFilterDetails]") String dynFilterDetails) {

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("serviceId", request);
		jsonObject.put("projectId", projectId);
		jsonObject.put("paymentType", paymentType);
		jsonObject.put("labelId", labelId);
		jsonObject.put("ctrlName", ctrlName);
		jsonObject.put("projectCategory", projectCategory);
		jsonObject.put("vchDynFilter", dynFilterDetails);
		Map<String, Object> response = new HashMap<String, Object>();
		// Integer status = SQLInjection.sqlInjection(jsonObject.toString(), 0);
		// here check squrity in feature
		if (1 == 1) {
			JSONObject getallOfficers = null;
			try {
				getallOfficers = workflowService.fillWorkflow(request, dynFilterDetails);
				response.put("errorFlag", 0);
				response.put("msg", "success");
				if (getallOfficers.has("result")) {
					response.put("result", getallOfficers.getString("result"));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			response.put("status", 417);
			response.put("msg", "error");
		}
		return ResponseEntity.ok(response);
	}

}
