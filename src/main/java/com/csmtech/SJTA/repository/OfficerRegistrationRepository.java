package com.csmtech.SJTA.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.csmtech.SJTA.entity.MRoleEntity;
import com.csmtech.SJTA.entity.User;

public interface OfficerRegistrationRepository extends JpaRepository<MRoleEntity, Long> {

	@Query(value = "select 	full_name,mobile_no,email_id,department,designation from user_details where user_type='O' and status='false' ", nativeQuery = true)
	List<User> viewOfficer();

}
