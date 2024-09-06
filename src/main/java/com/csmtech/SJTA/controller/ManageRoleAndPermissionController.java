package com.csmtech.SJTA.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.csmtech.SJTA.dto.ModuleMenuDTO;
import com.csmtech.SJTA.dto.RoleDTO;
import com.csmtech.SJTA.dto.SetPermissionRespones;
import com.csmtech.SJTA.entity.Department;
import com.csmtech.SJTA.entity.MRoleEntity;
import com.csmtech.SJTA.service.ManageRoleAndPermissionService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/mainpermission")
@CrossOrigin("*")
@Slf4j
public class ManageRoleAndPermissionController {
	
	private static final Logger log = LoggerFactory.getLogger(ManageRoleAndPermissionController.class);
	private static final String STATUS_KEY = "status";
	private static final String MESSAGE_KEY = "message";


	@Autowired
	private ManageRoleAndPermissionService service;
	
	
	@PostMapping("/getalldept")
	public ResponseEntity<Map<String, Object>> getAllDept() {
		Map<String, Object> response = new HashMap<>();
		List<Department> list = service.getAllDept();
		if (list != null && !list.isEmpty()) {
			response.put(STATUS_KEY, HttpStatus.OK.value());
			response.put(MESSAGE_KEY, "DATA FOUND");
			response.put("result", list);
		} else {
			response.put(STATUS_KEY, HttpStatus.NOT_FOUND.value());
			response.put(MESSAGE_KEY, "NO DATA FOUND");
		}
		return ResponseEntity.ok(response);
	}

	@PostMapping("/getallrole")
	public ResponseEntity<Map<String, Object>> getAllRoleByDepartment(@RequestBody RoleDTO role) {
		Map<String, Object> response = new HashMap<>();
		List<MRoleEntity> list = service.getAllRoleByDepartment(role.getDepartmentId());
		if (list != null && !list.isEmpty()) {
			response.put(STATUS_KEY, HttpStatus.OK.value());
			response.put(MESSAGE_KEY, "DATA FOUND");
			response.put("result", list);
		} else {
			response.put(STATUS_KEY, HttpStatus.NOT_FOUND.value());
			response.put(MESSAGE_KEY, "NO DATA FOUND");
		}
		return ResponseEntity.ok(response);
	}


	@GetMapping("/getrole")
	public ResponseEntity<List<RoleDTO>> getRoles() {
		return ResponseEntity.ok(service.getRoles());
	}

	@GetMapping("/getmodulewisemenu")
	public ResponseEntity<List<ModuleMenuDTO>> getModulesWithMenus(@RequestParam("rollId") Integer rollId) {
		return ResponseEntity.ok(service.getModulesWithMenus(rollId));
	}

	@PostMapping("/saveBatchrecord")
	public ResponseEntity<?> saveRollModuleMenuDetails(
			@RequestBody List<SetPermissionRespones> dataToInsertOrUpdate) {
		System.out.println(dataToInsertOrUpdate.toString());
		try {
			log.info("save method started");
			Integer respones = service.batchInsertOrUpdateSetPermissionTestR(dataToInsertOrUpdate);
			log.info("save method ended");
			if (respones > 0) {
				JSONObject response = new JSONObject();
				response.put("status", 200);
				response.put("result", respones);
				return ResponseEntity.ok(response.toString());
			} else {
				System.out.println(dataToInsertOrUpdate.toString());
				return ResponseEntity.ok("Not Save.");

			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while saving data.");
		}
	}

	@PostMapping("/updateBatchrecord")
	public ResponseEntity<?> batchUpdateSetPermissionTestR(
			@RequestBody List<SetPermissionRespones> dataToInsertOrUpdate) {
		try {
			Integer respones = service.batchUpdateSetPermissionTestR(dataToInsertOrUpdate);
			if (respones > 0) {
				JSONObject response = new JSONObject();
				response.put("status", 200);
				response.put("result", respones);
				return ResponseEntity.ok(response.toString());
				// return ResponseEntity.ok("Data Save Sucess..!");
			}
			System.out.println(dataToInsertOrUpdate.toString());
			return ResponseEntity.ok("Not Update.");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while saving data.");
		}
	}
}
