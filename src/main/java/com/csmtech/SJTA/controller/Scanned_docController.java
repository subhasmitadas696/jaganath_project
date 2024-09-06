package com.csmtech.SJTA.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csmtech.SJTA.service.Scanned_docService;
import com.csmtech.SJTA.util.CommonUtil;
import com.csmtech.SJTA.util.Scanned_docValidationCheck;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
@CrossOrigin("*")
@RequestMapping("/upload-scanned-document")
public class Scanned_docController {
	@Autowired
	private Scanned_docService scanned_docService;
	String data = "";
	private static final Logger logger = LoggerFactory.getLogger(Scanned_docController.class);
	JSONObject resp = new JSONObject();
	@Value("${file.path}")
	private String finalUploadPath;

	@PostMapping("/addEdit")
	public ResponseEntity<?> create(@RequestBody String scanned_doc)
			throws JsonMappingException, JsonProcessingException {
		logger.info("Inside create method of Scanned_docController");
		JSONObject requestObj = new JSONObject(scanned_doc);
		if (CommonUtil.hashRequestMatch(requestObj.getString("REQUEST_DATA"), requestObj.getString("REQUEST_TOKEN"))) {
			data = CommonUtil.inputStreamDecoder(scanned_doc);
			String validationMsg = Scanned_docValidationCheck.BackendValidation(new JSONObject(data));
			if (validationMsg != null) {
				resp.put("status", 502);
				resp.put("errMsg", validationMsg);
				logger.warn("Inside create method of Scanned_docController Validation Error");
			} else {
				resp = scanned_docService.save(data);
			}
		} else {
			resp.put("msg", "error");
			resp.put("status", 417);
		}
		return ResponseEntity.ok(CommonUtil.inputStreamEncoder(resp.toString()).toString());
	}
	
	@PostMapping("/updateMetadata")
	public ResponseEntity<?> updateMetadata(@RequestBody String scanned_doc)
			throws JsonMappingException, JsonProcessingException {
		logger.info("Inside create method of Scanned_docController");
		JSONObject requestObj = new JSONObject(scanned_doc);
		if (CommonUtil.hashRequestMatch(requestObj.getString("REQUEST_DATA"), requestObj.getString("REQUEST_TOKEN"))) {
			data = CommonUtil.inputStreamDecoder(scanned_doc);
//			String validationMsg = Scanned_docValidationCheck.BackendValidation(new JSONObject(data));
//			if (validationMsg != null) {
//				resp.put("status", 502);
//				resp.put("errMsg", validationMsg);
//				logger.warn("Inside create method of Scanned_docController Validation Error");
//			} else {
				resp = scanned_docService.saveMetadata(data);
//			}
		} else {
			resp.put("msg", "error");
			resp.put("status", 417);
		}
		return ResponseEntity.ok(CommonUtil.inputStreamEncoder(resp.toString()).toString());
	}

	@PostMapping("/preview")
	public ResponseEntity<?> getById(@RequestBody String formParams) {
		logger.info("Inside getById method of Scanned_docController");
		JSONObject requestObj = new JSONObject(formParams);
		if (CommonUtil.hashRequestMatch(requestObj.getString("REQUEST_DATA"), requestObj.getString("REQUEST_TOKEN"))) {
			data = CommonUtil.inputStreamDecoder(formParams);
			JSONObject json = new JSONObject(data);
			JSONObject entity = scanned_docService.getById(json.getInt("intId"));
			resp.put("status", 200);
			resp.put("result", entity);
		} else {
			resp.put("msg", "error");
			resp.put("status", 417);
		}
		return ResponseEntity.ok(CommonUtil.inputStreamEncoder(resp.toString()).toString());
	}

	@PostMapping("/all")
	public ResponseEntity<?> getAll(@RequestBody String formParams) {
		//-------------------------
		
		
		//---------------------------
		
		logger.info("Inside getAll method of Scanned_docController");
		JSONObject requestObj = new JSONObject(formParams);
		if (CommonUtil.hashRequestMatch(requestObj.getString("REQUEST_DATA"), requestObj.getString("REQUEST_TOKEN"))) {
			resp = scanned_docService.getAll(CommonUtil.inputStreamDecoder(formParams));
		} else {
			resp.put("msg", "error");
			resp.put("status", 417);
		}
		return ResponseEntity.ok(CommonUtil.inputStreamEncoder(resp.toString()).toString());
	}

	@PostMapping("/delete")
	public ResponseEntity<?> delete(@RequestBody String formParams) {
		logger.info("Inside delete method of Scanned_docController");
		JSONObject requestObj = new JSONObject(formParams);
		if (CommonUtil.hashRequestMatch(requestObj.getString("REQUEST_DATA"), requestObj.getString("REQUEST_TOKEN"))) {
			data = CommonUtil.inputStreamDecoder(formParams);
			JSONObject json = new JSONObject(data);
			resp = scanned_docService.deleteById(json.getInt("intId"));
		} else {
			resp.put("msg", "error");
			resp.put("status", 417);
		}
		return ResponseEntity.ok(CommonUtil.inputStreamEncoder(resp.toString()).toString());
	}
	
	@PostMapping("/allData")
	public ResponseEntity<?> getAllData(@RequestBody String formParams) {
		logger.info("Inside getAll method of Scanned_docController");
		JSONObject requestObj = new JSONObject(formParams);
		if (CommonUtil.hashRequestMatch(requestObj.getString("REQUEST_DATA"), requestObj.getString("REQUEST_TOKEN"))) {
			resp = scanned_docService.getAllData(CommonUtil.inputStreamDecoder(formParams));
		} else {
			resp.put("msg", "error");
			resp.put("status", 417);
		}
		return ResponseEntity.ok(CommonUtil.inputStreamEncoder(resp.toString()).toString());
	}

}