package com.csmtech.SJTA.service;

import java.math.BigInteger;

import com.csmtech.SJTA.dto.LandAppResponseStructureDTO;

public interface LnadApplicantViewAllDetailsService {

	public LandAppResponseStructureDTO getCombinedDataForApplicant(BigInteger applicantId);

}
