package com.csmtech.SJTA.service;
import org.json.JSONArray;
import org.json.JSONObject;
 

public interface Meeting_scheduleService {
JSONObject save(String meeting_schedule);
JSONObject getById(Integer Id);
JSONObject getAll(String formParams);
JSONObject deleteById(Integer id, Integer updateBy);
JSONObject updateFile(String meeting_schedule);
}