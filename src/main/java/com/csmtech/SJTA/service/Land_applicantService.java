package com.csmtech.SJTA.service;

import java.math.BigInteger;

import org.json.JSONArray;
import org.json.JSONObject;

import com.csmtech.SJTA.entity.Land_applicant;

public interface Land_applicantService {
	JSONObject userSave(String land_applicant);

//	JSONObject getById(Integer Id);

	JSONArray getAll(String formParams);


	//JSONObject deleteById(Integer id);

	//Land_applicant findByintId(Integer intId);

	JSONObject deleteById(Integer id);

//	JSONObject deleteById(BigInteger id);

	JSONObject getById(Integer id);
	

	JSONObject savedata(Integer landappid, Integer userId);

	//JSONObject savedatalog(String land_approvallog);

}