/**
 * 
 */
package com.csmtech.SJTA.service;

import java.math.BigInteger;

import com.csmtech.SJTA.dto.AppliedLandPaginationDTO;
import com.csmtech.SJTA.dto.LandAppResponseStructureDTO;

/**
 * 
 */
public interface AppliedLandApplicationService {

	AppliedLandPaginationDTO getLandApplicantDetailsPage(Integer createdBy, Integer pageNumber, Integer pageSize);

	LandAppResponseStructureDTO findAllDetailsBylandApplicantId(BigInteger landApplicantId);

	Integer cancelApplication(BigInteger landApplicantId, String remark);

}
