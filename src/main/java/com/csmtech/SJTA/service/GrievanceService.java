package com.csmtech.SJTA.service;

import org.json.JSONArray;
import org.json.JSONObject;

public interface GrievanceService {
	
	JSONObject save(String grievance);

	JSONObject getById(Integer Id);

	JSONObject getAll(String formParams);

	JSONObject deleteById(Integer id);
}