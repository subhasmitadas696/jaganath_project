package com.csmtech.SJTA.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.csmtech.SJTA.dto.CitizenInfoDTO;
import com.csmtech.SJTA.entity.LandAppRegistratationEntity;

/**
 * @author prasanta.sethi
 */

public interface ShowCitizenDetailsRepository extends JpaRepository<LandAppRegistratationEntity, Long> {

	@Query(nativeQuery = true, value = "SELECT full_name, mobile_no FROM user_details "
			+ "WHERE mobile_no LIKE %:mobileNo%")
	List<CitizenInfoDTO> findByMobilenoAndStatusFalse(@Param("mobileNo") String mobileNo);

	
	@Query(value = "SELECT user_id,user_type,full_name, mobile_no, email_id " + "FROM user_details "
			+ "WHERE (LOWER(mobile_no) LIKE LOWER(CONCAT('%', :searchKeyword, '%')) OR "
			+ "LOWER(full_name) LIKE LOWER(CONCAT('%', :searchKeyword, '%')))", nativeQuery = true)
	List<Object[]> searchByMobileNoContainingIgnoreCaseOrFullNameContainingIgnoreCase(String searchKeyword);


	
}
