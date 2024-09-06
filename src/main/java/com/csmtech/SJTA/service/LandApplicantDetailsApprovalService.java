package com.csmtech.SJTA.service;

import java.util.List;

import com.csmtech.SJTA.dto.LandApplicantDetailsUPDTO;

public interface LandApplicantDetailsApprovalService {
	
	public List<LandApplicantDetailsUPDTO> getLandApplicantsWithDetails(String actionId);

	
}
