package com.csmtech.SJTA.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.csmtech.SJTA.dto.ApplicantNumberAndMobileDTO;
import com.csmtech.SJTA.repository.LandApprovalRepository;
import com.csmtech.SJTA.service.LandApplicantService;
import com.csmtech.SJTA.service.Land_applicantService;
import com.csmtech.SJTA.serviceImpl.CommonPdfServiceImpl;
import com.csmtech.SJTA.util.CommonUtil;
import com.csmtech.SJTA.util.Land_applicantValidationCheck;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin("*")
@RequestMapping("/land-purchase-application")
@Slf4j
public class Land_applicantController {
	@Autowired
	private Land_applicantService land_applicantService;
	String path = "src/storage/individual-details/";
	String data = "";

	@Autowired
	LandApprovalRepository repo;
	@Autowired
	private LandApplicantService service;

	@Autowired
	private CommonPdfServiceImpl servicepdf;

	@PostMapping("/individual-details/addEdit")
	public ResponseEntity<?> create(@RequestBody String land_applicant)
			throws JsonMappingException, JsonProcessingException {
		// data = CommonUtil.inputStreamDecoder(land_applicant);
		data = land_applicant;
		JSONObject resp = new JSONObject();
		if (Land_applicantValidationCheck.BackendValidation(new JSONObject(data)) != null) {
			resp.put("status", 502);
			resp.put("errMsg", Land_applicantValidationCheck.BackendValidation(new JSONObject(data)));
		} else {
			resp = land_applicantService.userSave(data);
		}
		return ResponseEntity.ok(resp.toString());
	}

	@PostMapping("/individual-details/preview")
	public ResponseEntity<?> getById(@RequestBody String formParams) {
		// data = CommonUtil.inputStreamDecoder(formParams);
		data = formParams;
		JSONObject json = new JSONObject(data);
		JSONObject entity = land_applicantService.getById(json.getInt("intId"));
		JSONObject jsb = new JSONObject();
		jsb.put("status", 200);
		jsb.put("result", entity);
		return ResponseEntity.ok(jsb.toString());
	}

	@PostMapping("/individual-details/all")
	public ResponseEntity<?> getAll(@RequestBody String formParams) {
		JSONArray entity = land_applicantService.getAll(CommonUtil.inputStreamDecoder(formParams));
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("status", 200);
		jsonobj.put("result", entity);
		return ResponseEntity.ok(jsonobj.toString());
	}

	@PostMapping("/individual-details/delete")
	public ResponseEntity<?> delete(@RequestBody String formParams) {
		data = CommonUtil.inputStreamDecoder(formParams);
		JSONObject json = new JSONObject(data);
//		JSONObject entity = land_applicantService.deleteById(json.getInt("intId"));
		return ResponseEntity.ok("");
	}

	@GetMapping("/individual-details/download/{name}")
	public ResponseEntity<Resource> download(@PathVariable("name") String name) throws IOException {
		System.out.println(name);
		File file = new File(path + name);
		System.out.println("the file is:" + file);
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

	@PostMapping("/updateApplicant")
	public ResponseEntity<?> updateApplicantNumberDetails(@RequestBody String formParams) {
		JSONObject json = new JSONObject(formParams);
		Integer result = service.updateApplicantName(json.getBigInteger("intId"));
		JSONObject resp = land_applicantService.savedata(json.getInt("intId"), json.getInt("userId"));

		JSONObject jsonobj = new JSONObject();
		jsonobj.put("status", 200);
		jsonobj.put("message", "Success");

		return ResponseEntity.ok(jsonobj.toString());
	}

	// return the applicanr or mobile no
	@PostMapping("/getAppnoorMobile")
	public ResponseEntity<?> fetchApplicantInfoById(@RequestBody String formParams) {
		JSONObject jsonobj = new JSONObject();
		JSONObject json = new JSONObject(formParams);
		List<ApplicantNumberAndMobileDTO> respones = service.fetchApplicantInfoById(json.getBigInteger("intId"));
		if (respones.isEmpty()) {
			jsonobj.put("status", 404);
			jsonobj.put("result", "No Result Are Found");
		}
		jsonobj.put("status", 200);
		jsonobj.put("result", respones);

		return ResponseEntity.ok(jsonobj.toString());
	}

	@GetMapping(value = "/generatePdfApplicant",produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<?> generatePdf() {
		return ResponseEntity.ok(servicepdf.generatePdfSample(BigInteger.valueOf(118)));
	}

}