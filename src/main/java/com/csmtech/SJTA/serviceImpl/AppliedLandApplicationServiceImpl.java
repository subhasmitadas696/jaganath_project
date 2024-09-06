/**
 * 
 */
package com.csmtech.SJTA.serviceImpl;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csmtech.SJTA.dto.AppliedLandApplicationDTO;
import com.csmtech.SJTA.dto.AppliedLandPaginationDTO;
import com.csmtech.SJTA.dto.LandAppResponseStructureDTO;
import com.csmtech.SJTA.repository.AppliedLandApplicationNativeRepository;
import com.csmtech.SJTA.service.AppliedLandApplicationService;

/**
 * 
 */
@Service
public class AppliedLandApplicationServiceImpl implements AppliedLandApplicationService {

	@Autowired
	private AppliedLandApplicationNativeRepository appliedLandApplicationRepo;

	@Override
	public AppliedLandPaginationDTO getLandApplicantDetailsPage(Integer createdBy, Integer pageNumber,
			Integer pageSize) {
		List<AppliedLandApplicationDTO> respones = appliedLandApplicationRepo
				.getAppliedLandApplicantDetailsPage(createdBy, pageNumber, pageSize);
		Integer count = appliedLandApplicationRepo.getTotalLandApplicantCount(createdBy);
		return new AppliedLandPaginationDTO(respones, count);
	}

	@Override
	public LandAppResponseStructureDTO findAllDetailsBylandApplicantId(BigInteger landApplicantId) {
		return null;
	}

	// change ranjan
	@Override
	public Integer cancelApplication(BigInteger landApplicantId, String remark) {
		Integer getcount = appliedLandApplicationRepo.cancelLandApplication(landApplicantId, remark);
		return getcount;
	}

}
