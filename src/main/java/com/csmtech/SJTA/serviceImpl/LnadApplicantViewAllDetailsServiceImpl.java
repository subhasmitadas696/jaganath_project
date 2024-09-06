package com.csmtech.SJTA.serviceImpl;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csmtech.SJTA.dto.LandAppResponseStructureDTO;
import com.csmtech.SJTA.repository.LnadApplicantViewAllDetailsClassRepository;
import com.csmtech.SJTA.service.LnadApplicantViewAllDetailsService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LnadApplicantViewAllDetailsServiceImpl implements LnadApplicantViewAllDetailsService {

	@Autowired
	private LnadApplicantViewAllDetailsClassRepository repo;
	
	@Override
	public LandAppResponseStructureDTO getCombinedDataForApplicant(BigInteger applicantId) {
		log.info(" :: getCombinedDataForApplicant() method are return the result  ..!!");
		return repo.getCombinedDataForApplicant(applicantId);
	}

}
