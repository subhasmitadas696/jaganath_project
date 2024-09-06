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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.csmtech.SJTA.dto.ModuleMenuDataDTO;
import com.csmtech.SJTA.service.MenuBindingService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/menumainurl")
@Slf4j
@CrossOrigin("*")
public class MenuBindingController {

	@Autowired
	private MenuBindingService service;
	
	String data = "";
	
	@PostMapping("/databinding")
	public ResponseEntity<List<ModuleMenuDataDTO>> getMenuData(@RequestBody String formParams) {
		data = formParams;
		JSONObject json = new JSONObject(data);	
		log.info(":: getMenuData() Execution are start !!");
		List<ModuleMenuDataDTO> respones = service.getModuleAndMenuByUserId(json.getInt("userId"));
		if (respones.isEmpty()) {
			log.info(":: getMenuData()  return no record found");
			return ResponseEntity.ok(respones);
		}
		log.info(":: getMenuData()  return the result");
		return ResponseEntity.ok(respones);
	}

}
