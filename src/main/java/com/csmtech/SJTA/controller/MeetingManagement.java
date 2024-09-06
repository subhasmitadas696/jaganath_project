/**
 * 
 */
package com.csmtech.SJTA.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csmtech.SJTA.dto.MeetingScheduleDTO;
import com.csmtech.SJTA.dto.UserDetails;
import com.csmtech.SJTA.entity.DistrictMaster;
import com.csmtech.SJTA.entity.KhatianInformation;
import com.csmtech.SJTA.entity.MeetingLevel;
import com.csmtech.SJTA.entity.PlotInformation;
import com.csmtech.SJTA.entity.TahasilMaster;
import com.csmtech.SJTA.entity.VillageMaster;
import com.csmtech.SJTA.service.MeetingService;

/**
 * @author guru.prasad
 */
@RestController
@RequestMapping("/api/meeting")
@CrossOrigin("*")
public class MeetingManagement {

	@Autowired
	private MeetingService meetingService;

	private static final Logger log = LoggerFactory.getLogger(MeetingManagement.class);
	private static final String STATUS_KEY = "status";
	private static final String MESSAGE_KEY = "message";

	
	@PostMapping("/getalldistrict")
	public ResponseEntity<Map<String, Object>> getAllDistrict() {
		Map<String, Object> response = new HashMap<>();
		List<DistrictMaster> list = meetingService.getAllDistrict();
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
	
	@PostMapping("/getalltahasil")
	public ResponseEntity<Map<String, Object>> getAllTahasil(@RequestBody TahasilMaster tahasil) {
		Map<String, Object> response = new HashMap<>();
		List<TahasilMaster> list = meetingService.getAllTahasil(tahasil.getDistrictCode());
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
	
	@PostMapping("/getallvillage")
	public ResponseEntity<Map<String, Object>> getAllVillage(@RequestBody VillageMaster village) {
		Map<String, Object> response = new HashMap<>();
		List<VillageMaster> list = meetingService.getAllVillage(village.getTahasilCode());
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
	
	@PostMapping("/getallkhatian")
	public ResponseEntity<Map<String, Object>> getAllKhatian(@RequestBody KhatianInformation khatian) {
		Map<String, Object> response = new HashMap<>();
		List<KhatianInformation> list = meetingService.getAllKhatian(khatian.getVillageCode());
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
	
	@PostMapping("/getallplot")
	public ResponseEntity<Map<String, Object>> getAllPlot(@RequestBody PlotInformation plot) {
		Map<String, Object> response = new HashMap<>();
		List<PlotInformation> list = meetingService.getAllPlot(plot.getKhatianCode());
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
	
	@PostMapping("/getallmeetinglevel")
	public ResponseEntity<Map<String, Object>> getAllMeetingLevel() {
		Map<String, Object> response = new HashMap<>();
		List<MeetingLevel> list = meetingService.getAllMeetingLevel();
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
	
	@PostMapping("/savemeeting")
	public ResponseEntity<Map<String, Object>> registerOfficer(@RequestBody MeetingScheduleDTO meetingDTO,
			HttpServletRequest httpServletRequest) {
		Map<String, Object> response = new HashMap<>();

		System.out.println(meetingDTO + "********");

			log.info("data checked");
			Integer registerMeeting = meetingService.registerMeeting(meetingDTO);
			if (registerMeeting == 1) {
				response.put(STATUS_KEY, HttpStatus.OK.value());
				response.put(MESSAGE_KEY, "Meeting created Successfully");
			} else {
				response.put(STATUS_KEY, HttpStatus.NO_CONTENT.value());
				response.put(MESSAGE_KEY, "not saved");
			}
		return ResponseEntity.ok(response);
	}
}
