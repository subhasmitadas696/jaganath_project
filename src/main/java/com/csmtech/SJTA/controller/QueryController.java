package com.csmtech.SJTA.controller;

/**
 * @author prasanta.sethi
 */

import java.util.List;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.csmtech.SJTA.dto.QueryDetailsDTO;
import com.csmtech.SJTA.service.QueryService;

import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin("*")
@Slf4j
public class QueryController {
	private final QueryService queryService;

	@Autowired
	public QueryController(QueryService queryService) {
		this.queryService = queryService;
	}

	@PostMapping("/raisequery")
	public ResponseEntity<String> insertQuery(@RequestBody QueryDetailsDTO queryDetails) {
		JSONObject response = new JSONObject();
		queryService.insertQuery(queryDetails.getQueryId(), queryDetails.getName(), queryDetails.getMobileno(),
				queryDetails.getQuery());
		log.info("insertQuery.. Executed Successfully");
		response.put("status", 200);
		response.put("message", "Query Inserted Successfully !");
		return ResponseEntity.ok(response.toString());
	}

	@PostMapping("/viewquery")
	public ResponseEntity<?> getQueryDetailsBySearch(@RequestBody String param) {
		JSONObject response = new JSONObject();
		List<QueryDetailsDTO> queryDetailsList = queryService.getQueryDetailsById();
		log.info("queryDetailsList i.e queries found successfully !! ");
		response.put("status", 200);
		response.put("result", queryDetailsList);
		response.put("count", queryDetailsList.size());
		log.info("queryDetailsList returned successfully !!");
		return ResponseEntity.ok(response.toString());
	}
}
