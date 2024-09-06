package com.csmtech.SJTA.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import com.csmtech.SJTA.entity.Grievance;

@Repository
public interface GrievanceRepository extends JpaRepository<Grievance, Integer> {
	
	Grievance findByIntIdAndBitDeletedFlag(Integer intId, boolean bitDeletedFlag);

	@Query("From Grievance where bitDeletedFlag=:bitDeletedFlag")
	List<Grievance> findAllByBitDeletedFlag(Boolean bitDeletedFlag, Pageable pageRequest);

	Integer countByBitDeletedFlag(Boolean bitDeletedFlag);
}