/**
 * @author prasanta.sethi
 */
package com.csmtech.SJTA.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.csmtech.SJTA.dto.StatisticsDTO;
import com.csmtech.SJTA.service.LandAreaStatisticsService;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/statistics")
@CrossOrigin("*")
@Slf4j
public class LandAreaStatisticsController {
	@Autowired
	private LandAreaStatisticsService landAreaStatisticsService;

	@GetMapping("/getlandstatistics")
	public ResponseEntity<?> getStatisticsDetails() {
		log.info("getlandstatistics started....!!");
		JSONObject response = new JSONObject();
		response.put("status", 200);
		response.put("result", new JSONObject(landAreaStatisticsService.fetchStatisticsInfo()));
		return ResponseEntity.ok(response.toString());
	}

}
