package com.csmtech.SJTA.service;

import java.math.BigInteger;
import java.util.List;

import com.csmtech.SJTA.dto.ApplicantNumberAndMobileDTO;
import com.csmtech.SJTA.dto.LandAppResponseStructureDTO;
import com.csmtech.SJTA.dto.LandApplicantDTO;
import com.csmtech.SJTA.dto.LandPaginationDTO;
import com.csmtech.SJTA.entity.Land_applicant;

/*
 * @Auth  GuruPrasad
 */

public interface LandApplicantService {

	

	LandPaginationDTO getSearchLandApplicantDetailsPage(BigInteger roleId, String applicantName,
			Integer pageNumber, Integer pageSize, String applicantUniqueId, String plotKathaNO, String pageType);

	LandAppResponseStructureDTO findAllDetailsBylandApplicantId(BigInteger landApplicantId);

	public Integer updateApplicantName(BigInteger applicantId);

	public List<ApplicantNumberAndMobileDTO> fetchApplicantInfoById(BigInteger i);

}
