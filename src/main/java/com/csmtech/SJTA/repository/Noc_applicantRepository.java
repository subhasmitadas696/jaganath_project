package com.csmtech.SJTA.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.csmtech.SJTA.entity.Noc_applicant;

@Repository
public interface Noc_applicantRepository extends JpaRepository<Noc_applicant, Integer> {
	Noc_applicant findByIntIdAndBitDeletedFlag(Integer intId, boolean bitDeletedFlag);

	@Query("From Noc_applicant where deleted_flag=:bitDeletedFlag")
	List<Noc_applicant> findAllByBitDeletedFlag(Boolean bitDeletedFlag);
	
	
}