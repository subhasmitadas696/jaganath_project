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
import com.csmtech.SJTA.service.Noc_documentsService;

import com.csmtech.SJTA.util.CommonUtil;
import com.csmtech.SJTA.util.Noc_documentsValidationCheck;

@RestController
@CrossOrigin("*")
@RequestMapping("/noc")
public class Noc_documentsController {
	@Autowired
	private Noc_documentsService noc_documentsService;
	String path = "src/storage/upload-documents/";
	String data = "";

	@PostMapping("/upload-documents/addEdit")
	public ResponseEntity<?> create(@RequestBody String noc_documents)
			throws JsonMappingException, JsonProcessingException {
		data = CommonUtil.inputStreamDecoder(noc_documents);
		JSONObject resp = new JSONObject();
		if (Noc_documentsValidationCheck.BackendValidation(new JSONObject(data)) != null) {
			resp.put("status", 502);
			resp.put("errMsg", Noc_documentsValidationCheck.BackendValidation(new JSONObject(data)));
		} else {
			resp = noc_documentsService.save(data);
		}
		return ResponseEntity.ok(resp.toString());
	}

	@PostMapping("/upload-documents/preview")
	public ResponseEntity<?> getById(@RequestBody String formParams) {
		data = CommonUtil.inputStreamDecoder(formParams);
		JSONObject json = new JSONObject(data);
		JSONObject entity = noc_documentsService.getById(json.getInt("intId"));
		JSONObject jsb = new JSONObject();
		jsb.put("status", 200);
		jsb.put("result", entity);
		return ResponseEntity.ok(jsb.toString());
	}

	@PostMapping("/upload-documents/all")
	public ResponseEntity<?> getAll(@RequestBody String formParams) {
		JSONArray entity = noc_documentsService.getAll(CommonUtil.inputStreamDecoder(formParams));
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("status", 200);
		jsonobj.put("result", entity);
		return ResponseEntity.ok(jsonobj.toString());
	}

	@PostMapping("/upload-documents/delete")
	public ResponseEntity<?> delete(@RequestBody String formParams) {
		data = CommonUtil.inputStreamDecoder(formParams);
		JSONObject json = new JSONObject(data);
		JSONObject entity = noc_documentsService.deleteById(json.getInt("intId"));
		return ResponseEntity.ok(entity.toString());
	}

	@GetMapping("/upload-documents/download/{name}")
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