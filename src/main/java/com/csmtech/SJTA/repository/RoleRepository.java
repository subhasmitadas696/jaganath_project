package com.csmtech.SJTA.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.csmtech.SJTA.entity.MRoleEntity;

public interface RoleRepository extends JpaRepository<MRoleEntity, Long> {

	List<MRoleEntity> findAllByDepartmentId(Long departmentId);

}
