package com.csmtech.SJTA.service;

import java.util.List;
import com.csmtech.SJTA.dto.CitizenInfoDTO;
import com.csmtech.SJTA.dto.UserDetailsDTO;
import com.csmtech.SJTA.entity.LandAppRegistratationEntity;

/**
 * @Auth Prasanta Kumar Sethi
 */
public interface ShowCitizenInfoService {

	 List<CitizenInfoDTO> getCitizenInfo();


	
	public List<UserDetailsDTO> searchUsers(String searchKeyword);



	Integer getTotalApplicantCount();



	List<UserDetailsDTO> getSearchUserDetailsPage(String searchKeyword, Integer pageNumber, Integer pageSize);



	List<UserDetailsDTO> getUserDetailsPage(Integer pageNumber, Integer pageSize);
}
