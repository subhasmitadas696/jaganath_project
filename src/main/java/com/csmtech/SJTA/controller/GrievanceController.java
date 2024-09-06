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

import com.csmtech.SJTA.service.GrievanceService;
import com.csmtech.SJTA.util.CommonUtil;
import com.csmtech.SJTA.util.GrievanceValidationCheck;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;


@RestController
@CrossOrigin("*")
@RequestMapping("/grievance-module")
public class GrievanceController {
	
	@Autowired
	private GrievanceService grievanceService;
	String data = "";
	private static final Logger logger = LoggerFactory.getLogger(GrievanceController.class);
	JSONObject resp = new JSONObject();
	@Value("${file.path}")
	private String finalUploadPath;

	@PostMapping("/grievance/addEdit")
	public ResponseEntity<?> create(@RequestBody String grievance)
			throws JsonMappingException, JsonProcessingException {
		logger.info("Inside create method of GrievanceController");
		JSONObject requestObj = new JSONObject(grievance);
		if (CommonUtil.hashRequestMatch(requestObj.getString("REQUEST_DATA"), requestObj.getString("REQUEST_TOKEN"))) {
			data = CommonUtil.inputStreamDecoder(grievance);
			String validationMsg = GrievanceValidationCheck.BackendValidation(new JSONObject(data));
			if (validationMsg != null) {
				resp.put("status", 502);
				resp.put("errMsg", validationMsg);
				logger.warn("Inside create method of GrievanceController Validation Error");
			} else {
				resp = grievanceService.save(data);
			}
		} else {
			resp.put("msg", "error");
			resp.put("status", 417);
		}
		return ResponseEntity.ok(CommonUtil.inputStreamEncoder(resp.toString()).toString());
	}

	@PostMapping("/grievance/preview")
	public ResponseEntity<?> getById(@RequestBody String formParams) {
		logger.info("Inside getById method of GrievanceController");
		JSONObject requestObj = new JSONObject(formParams);
		if (CommonUtil.hashRequestMatch(requestObj.getString("REQUEST_DATA"), requestObj.getString("REQUEST_TOKEN"))) {
			data = CommonUtil.inputStreamDecoder(formParams);
			JSONObject json = new JSONObject(data);
			JSONObject entity = grievanceService.getById(json.getInt("intId"));
			resp.put("status", 200);
			resp.put("result", entity);
		} else {
			resp.put("msg", "error");
			resp.put("status", 417);
		}
		return ResponseEntity.ok(CommonUtil.inputStreamEncoder(resp.toString()).toString());
	}

	@PostMapping("/grievance/all")
	public ResponseEntity<?> getAll(@RequestBody String formParams) {
		logger.info("Inside getAll method of GrievanceController");
		JSONObject requestObj = new JSONObject(formParams);
		if (CommonUtil.hashRequestMatch(requestObj.getString("REQUEST_DATA"), requestObj.getString("REQUEST_TOKEN"))) {
			resp = grievanceService.getAll(CommonUtil.inputStreamDecoder(formParams));
		} else {
			resp.put("msg", "error");
			resp.put("status", 417);
		}
		return ResponseEntity.ok(CommonUtil.inputStreamEncoder(resp.toString()).toString());
	}

	@PostMapping("/grievance/delete")
	public ResponseEntity<?> delete(@RequestBody String formParams) {
		logger.info("Inside delete method of GrievanceController");
		JSONObject requestObj = new JSONObject(formParams);
		if (CommonUtil.hashRequestMatch(requestObj.getString("REQUEST_DATA"), requestObj.getString("REQUEST_TOKEN"))) {
			data = CommonUtil.inputStreamDecoder(formParams);
			JSONObject json = new JSONObject(data);
			resp = grievanceService.deleteById(json.getInt("intId"));
		} else {
			resp.put("msg", "error");
			resp.put("status", 417);
		}
		return ResponseEntity.ok(CommonUtil.inputStreamEncoder(resp.toString()).toString());
	}

	@GetMapping("/grievance/download/{name}")
	public ResponseEntity<Resource> download(@PathVariable("name") String name) throws IOException {
		logger.info("Inside download method of GrievanceController");
		File file = new File(finalUploadPath + "grievance/" + name);
		Path path = Paths.get(file.getAbsolutePath());
		ByteArrayResource byteArrayResource = new ByteArrayResource(Files.readAllBytes(path));
		return ResponseEntity.ok().headers(headers(name)).contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/octet-stream")).body(byteArrayResource);
	}

	private HttpHeaders headers(String name) {
		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + name);
		header.add("Cache-Control", "no-cache, no-store, must-revalidate");
		header.add("Pragma", "no-cache");
		header.add("Expires", "0");
		return header;
	}
}