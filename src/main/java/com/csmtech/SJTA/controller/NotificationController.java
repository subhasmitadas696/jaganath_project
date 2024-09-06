package com.csmtech.SJTA.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.csmtech.SJTA.dto.NotificationDTO;
import com.csmtech.SJTA.service.NotificationService;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin("*")
public class NotificationController {

	@Autowired
	private NotificationService notificationService;

	private static final Logger log = LoggerFactory.getLogger(NotificationController.class);
	private static final String STATUS_KEY = "status";
	private static final String MESSAGE_KEY = "message";

	@GetMapping("/getnotifications")
	public ResponseEntity<ApiResponse<List<NotificationDTO>>> getAllNotifications() {
		try {
			log.info("notification method started..");
			List<NotificationDTO> notificationDTOs = notificationService.getAllNotifications();
			if (notificationDTOs.isEmpty()) {
				return ResponseEntity.ok(new ApiResponse<>(HttpStatus.NO_CONTENT, "No notifications found.", null));
			}
			log.info("notification found");
			return ResponseEntity
					.ok(new ApiResponse<>(HttpStatus.OK, "Notifications fetched successfully.", notificationDTOs));
		} catch (Exception e) {
			log.error("An error occurred in the notification: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred.", null));
		}
	}

	@PostMapping("/viewnotifications")
	public ResponseEntity<Map<String, Object>> viewNotifications(@RequestBody NotificationDTO dto) {
	    Map<String, Object> response = new HashMap<>();
	    List<NotificationDTO> list = notificationService.getAllNotification(dto.getDescription(), dto.getStartDate(), dto.getEndDate());
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


	@PostMapping("/getonenotification")
	public ResponseEntity<ApiResponse<NotificationDTO>> getNotification(
			@RequestBody @Valid NotificationDTO notificationDTO) {
		NotificationDTO notificationDTODetails = notificationService
				.getNotificationById(notificationDTO.getNotificationId());
		return ResponseEntity
				.ok(new ApiResponse<>(HttpStatus.OK, "Notification fetched successfully.", notificationDTODetails));
	}

	@PostMapping("/add")
	public ResponseEntity<ApiResponse<@Valid NotificationDTO>> addNotification(
			@RequestBody @Valid NotificationDTO notificationDTO) {
		notificationService.addNotification(notificationDTO);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ApiResponse<>(HttpStatus.CREATED, "Notification added successfully.", notificationDTO));
	}

	@PostMapping("/update")
	public ResponseEntity<ApiResponse<@Valid NotificationDTO>> updateNotification(
			@RequestBody @Valid NotificationDTO notificationDTO) {
		notificationService.updateNotification(notificationDTO);
		return ResponseEntity
				.ok(new ApiResponse<>(HttpStatus.OK, "Notification updated successfully.", notificationDTO));
	}

	@PostMapping("/delete")
	public ResponseEntity<ApiResponse<@Valid NotificationDTO>> softDeleteNotification(
			@RequestBody @Valid NotificationDTO notificationDTO) {
		notificationService.softDeleteNotification(notificationDTO);
		return ResponseEntity
				.ok(new ApiResponse<>(HttpStatus.OK, "Notification soft-deleted successfully.", notificationDTO));
	}

	private static class ApiResponse<T> {
		private HttpStatus status;
		private String message;
		private T data;

		public ApiResponse(HttpStatus status, String message, T data) {
			this.status = status;
			this.message = message;
			this.data = data;
		}

		public HttpStatus getStatus() {
			return status;
		}

		public String getMessage() {
			return message;
		}

		public T getData() {
			return data;
		}

	}
}
