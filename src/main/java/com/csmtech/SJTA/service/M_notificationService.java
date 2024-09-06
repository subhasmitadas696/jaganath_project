package com.csmtech.SJTA.service;

import org.json.JSONArray;
import org.json.JSONObject;

public interface M_notificationService {
	JSONObject save(String m_notification);

	JSONObject getById(Integer Id);

	JSONArray getAll(String formParams);

	JSONObject deleteById(Integer id, Integer updatedby);

	Integer getTotalAppCount();
}