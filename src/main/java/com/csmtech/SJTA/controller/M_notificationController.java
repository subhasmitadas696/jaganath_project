package com.csmtech.SJTA.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
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

import com.csmtech.SJTA.service.M_notificationService;
import com.csmtech.SJTA.util.CommonUtil;
import com.csmtech.SJTA.util.M_notificationValidationCheck;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
@CrossOrigin("*")
@RequestMapping("/manage-notification")
public class M_notificationController {
	@Autowired
	private M_notificationService m_notificationService;
	String path = "src/storage/manage-notification/";
	String data = "";
	
	@Value("${file.path}")
	private String filePathloc;

	@PostMapping("/addEdit")
	public ResponseEntity<?> create(@RequestBody String m_notification)
			throws JsonMappingException, JsonProcessingException {
		data = CommonUtil.inputStreamDecoder(m_notification);
		JSONObject resp = new JSONObject();
		if (M_notificationValidationCheck.BackendValidation(new JSONObject(data)) != null) {
			resp.put("status", 502);
			resp.put("errMsg", M_notificationValidationCheck.BackendValidation(new JSONObject(data)));
		} else {
			resp = m_notificationService.save(data);
		}
		return ResponseEntity.ok(resp.toString());
	}

	@PostMapping("/preview")
	public ResponseEntity<?> getById(@RequestBody String formParams) {
		data = CommonUtil.inputStreamDecoder(formParams);
		JSONObject json = new JSONObject(data);
		JSONObject entity = m_notificationService.getById(json.getInt("intId"));
			
		JSONObject jsb = new JSONObject();
		jsb.put("status", 200);
		jsb.put("result", entity);
		return ResponseEntity.ok(jsb.toString());
	}

	@PostMapping("/all")
	public ResponseEntity<?> getAll(@RequestBody String formParams) {
		JSONArray entity = m_notificationService.getAll(CommonUtil.inputStreamDecoder(formParams));
		Integer app_count = m_notificationService.getTotalAppCount();
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("status", 200);
		jsonobj.put("result", entity);
		jsonobj.put("total_app_count", app_count);
		return ResponseEntity.ok(jsonobj.toString());
	}

	@PostMapping("/delete")
	public ResponseEntity<?> delete(@RequestBody String formParams) {
		data = CommonUtil.inputStreamDecoder(formParams);
		JSONObject json = new JSONObject(data);
		JSONObject entity = m_notificationService.deleteById(json.getInt("intId"),json.getInt("intUpdatedBy"));
		return ResponseEntity.ok(entity.toString());
	}

	@GetMapping("/download/{name}")
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