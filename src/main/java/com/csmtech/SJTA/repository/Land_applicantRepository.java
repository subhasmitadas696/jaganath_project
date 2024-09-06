package com.csmtech.SJTA.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.csmtech.SJTA.entity.Land_applicant;

@Repository
public interface Land_applicantRepository extends JpaRepository<Land_applicant, Integer> {
	@Query(value = "select * From land_application where deleted_flag ='0' and land_application_id =:bigInsteger",nativeQuery = true)
	Land_applicant findByIntId(Integer bigInsteger);

	//Land_applicant findByintId(Integer intId);
	
	@Query(value= "select * From land_application where deleted_flag='0' ",nativeQuery = true)
	List<Land_applicant> findAllByBitDeletedFlag();

	
	

	
}
