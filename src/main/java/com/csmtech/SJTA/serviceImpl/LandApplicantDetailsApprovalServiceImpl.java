package com.csmtech.SJTA.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csmtech.SJTA.dto.LandApplicantDetailsUPDTO;
import com.csmtech.SJTA.repository.LandApplicantDetailsApprovalStageRepository;
import com.csmtech.SJTA.service.LandApplicantDetailsApprovalService;

@Service
public class LandApplicantDetailsApprovalServiceImpl implements LandApplicantDetailsApprovalService {

	@Autowired
	private LandApplicantDetailsApprovalStageRepository repo;

	@Override
	public List<LandApplicantDetailsUPDTO> getLandApplicantsWithDetails(String actionId) {
		// TODO Auto-generated method stub
		return null;
	}


}
