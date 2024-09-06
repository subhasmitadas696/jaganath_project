/**
 * @author prasanta.sethi
 */
package com.csmtech.SJTA.controller;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.csmtech.SJTA.dto.BlockUserDTO;
import com.csmtech.SJTA.repository.BlockUserRepository;
import com.csmtech.SJTA.service.BlockUserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin("*")
@Slf4j
public class BlockUserController {

	@Autowired
	private BlockUserRepository blockUserRepository;

	@PostMapping("/block-user")
	public ResponseEntity<String> blockUser(@RequestBody BlockUserDTO blockUserDTO) {
		JSONObject response = new JSONObject();

		try {
			// Check if the user with the given userId exists
			Long existingUserId = blockUserRepository.checkUserExists(blockUserDTO.getUserId());
			if (existingUserId == null) {
				response.put("status", "error");
				response.put("message", "User not found with provided userId");
				return ResponseEntity.badRequest().body(response.toString());
			}

			Boolean status = blockUserDTO.getUserBlockStatus();

			if (!status == false) {
				blockUserRepository.updateUserBlockStatusAndRemarks(blockUserDTO.getUserId(),
						blockUserDTO.getUserBlockStatus(), blockUserDTO.getBlockRemarks());

				response.put("status", "fail");
				response.put("message", "User blocked successfully");
			} else {
				blockUserRepository.updateUserBlockStatusAndRemarks(blockUserDTO.getUserId(),
						blockUserDTO.getUserBlockStatus(), blockUserDTO.getBlockRemarks());
				response.put("status", "success");
				response.put("message", "User unblocked successfully");
			}
		} catch (Exception e) {
			response.put("status", "error");
			response.put("message", "Error blocking/unblocking user: " + e.getMessage());

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response.toString());
		}
		return ResponseEntity.ok(response.toString());
	}
//	 @PostMapping("/users/status")
//	    public ResponseEntity<Map<String, Boolean>> getUserStatus(@RequestBody Map<String, Long> requestBody) {
//	        Long userId = requestBody.get("userId");
//	        Boolean isBlocked = blockUserService.isUserBlocked(userId);
//
//	        Map<String, Boolean> response = new HashMap<>();
//	        response.put("isBlocked", isBlocked);
//
//	        return ResponseEntity.ok(response);
//	    }

}
