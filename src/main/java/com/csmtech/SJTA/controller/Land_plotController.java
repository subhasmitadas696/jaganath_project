package com.csmtech.SJTA.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import java.io.File;
import java.io.IOException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.json.JSONArray;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RestController;

import com.csmtech.SJTA.dto.LandIndividualAppDTO;
import com.csmtech.SJTA.repository.LandPlotDetailsRepository;
import com.csmtech.SJTA.service.Land_plotService;

import com.csmtech.SJTA.util.CommonUtil;
import com.csmtech.SJTA.util.Land_plotValidationCheck;

@RestController
@CrossOrigin("*")
@RequestMapping("/land-purchase-application")
public class Land_plotController {
	@Autowired
	private Land_plotService land_plotService;
	String path = "src/storage/plot-details/";
	String data = "";
	
	@Autowired
	private LandPlotDetailsRepository repo;

//	@PostMapping("/plot-details/addEdit")
//	public ResponseEntity<?> create(@RequestBody String land_plot)
//			throws JsonMappingException, JsonProcessingException {
//		// data = CommonUtil.inputStreamDecoder(land_plot);
//		data = land_plot;
//		JSONObject resp = new JSONObject();
//		if (Land_plotValidationCheck.BackendValidation(new JSONObject(data)) != null) {
//			resp.put("status", 502);
//			resp.put("errMsg", Land_plotValidationCheck.BackendValidation(new JSONObject(data)));
//		} else {
//			resp = land_plotService.save(data);
//		}
//		return ResponseEntity.ok(resp.toString());
//	}

	@PostMapping("/plot-details/addEdit")
	public ResponseEntity<?> create(@RequestBody LandIndividualAppDTO dto) {
		Integer countplotupdate = repo.updateApplicantRecord(dto);
		Integer countinsertolot = repo.batchUpdateDeleteNocPlots(dto);

		if (countinsertolot > 1 || countplotupdate > 1) {
			JSONObject jsb = new JSONObject();
			jsb.put("status", 200);
			jsb.put("result", "Record update and insert success");
			return ResponseEntity.ok(jsb.toString());
		} else {
			JSONObject jsb = new JSONObject();
			jsb.put("status", 200);
			jsb.put("result", " update or insert fail");
			return ResponseEntity.ok(jsb.toString());
		}

	}

//	@PostMapping("/plot-details/preview")
//	public ResponseEntity<?> getByLandApplicantId(@RequestBody String formParams) {
//		// data = CommonUtil.inputStreamDecoder(formParams);
//		data = formParams;
//		JSONObject json = new JSONObject(data);
//		JSONObject entity = land_plotService.getByIntLandApplicantId(json.getInt("intId"));
//		JSONObject jsb = new JSONObject();
//		jsb.put("status", 200);
//		jsb.put("result", entity);
//		return ResponseEntity.ok(jsb.toString());
//	}

	@PostMapping("/plot-details/preview")
	public ResponseEntity<?> getByLandApplicantId(@RequestBody String formParams) {
		JSONObject json = new JSONObject(formParams);
		System.out.println(json.getInt("intId"));
		LandIndividualAppDTO result = repo.getLandIndividualAppDTOByLandApplicantId(json.getInt("intId"));
		

		if (result != null) {
			JSONObject jo = new JSONObject();
			jo.put("status", 200);
			jo.put("result", new JSONObject(result));
			return ResponseEntity.ok(jo.toString());
		} else {
			JSONObject jsb = new JSONObject();
			jsb.put("status", 500);
			jsb.put("result", "No Record Found");
			return ResponseEntity.ok(jsb.toString());
		}
	}

	@PostMapping("/plot-details/all")
	public ResponseEntity<?> getAll(@RequestBody String formParams) {
		JSONArray entity = land_plotService.getAll(CommonUtil.inputStreamDecoder(formParams));
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("status", 200);
		jsonobj.put("result", entity);
		return ResponseEntity.ok(jsonobj.toString());
	}

	@PostMapping("/plot-details/delete")
	public ResponseEntity<?> delete(@RequestBody String formParams) {
		data = CommonUtil.inputStreamDecoder(formParams);
		JSONObject json = new JSONObject(data);
		JSONObject entity = land_plotService.deleteByIntLandApplicantId(json.getInt("intLandApplicantId"));
		return ResponseEntity.ok(entity.toString());
	}

	@GetMapping("/plot-details/download/{name}")
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
}