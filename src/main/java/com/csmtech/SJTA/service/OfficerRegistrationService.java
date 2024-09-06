package com.csmtech.SJTA.service;

import java.math.BigInteger;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.csmtech.SJTA.dto.AddOfficerDTO;
import com.csmtech.SJTA.dto.UserDetails;
import com.csmtech.SJTA.entity.Department;
import com.csmtech.SJTA.entity.MRoleEntity;
import com.csmtech.SJTA.entity.User;

public interface OfficerRegistrationService {
	Integer checkExistingRecord(String mobileNo);

	Integer registerOfficer(UserDetails officerDTO);

	List<MRoleEntity> getAll();

	List<User> viewOfficer();

	List<AddOfficerDTO> getOfficerInfo(Integer pageNumber, Integer pageSize, String fullName);

	Integer updateDetails(String newfullName, String newmobileNo, String newemailId, BigInteger departmentId,
			BigInteger roleId, BigInteger newupdatedBy, BigInteger createdBy, BigInteger userId);

	Integer deleteRecord(BigInteger createdBy, BigInteger userId);

	Integer getTotalOfficerCount();

	List<Department> getAllDept();

	List<MRoleEntity> getAllRoleByDepartment(Long departmentId);

}
