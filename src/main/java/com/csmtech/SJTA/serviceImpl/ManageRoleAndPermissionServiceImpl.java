package com.csmtech.SJTA.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csmtech.SJTA.dto.ModuleMenuDTO;
import com.csmtech.SJTA.dto.RoleDTO;
import com.csmtech.SJTA.dto.SetPermissionRespones;
import com.csmtech.SJTA.entity.Department;
import com.csmtech.SJTA.entity.MRoleEntity;
import com.csmtech.SJTA.repository.DepartmentRepository;
import com.csmtech.SJTA.repository.ManageRoleAndPermissionReposiory;
import com.csmtech.SJTA.repository.RoleRepository;
import com.csmtech.SJTA.service.ManageRoleAndPermissionService;

@Service
public class ManageRoleAndPermissionServiceImpl implements ManageRoleAndPermissionService {

	@Autowired
	private ManageRoleAndPermissionReposiory repo;

	@Autowired
	private DepartmentRepository deptRepo;

	@Autowired
	private RoleRepository roleRepo;

	@Override
	public List<RoleDTO> getRoles() {
		return repo.getRoles();
	}

	@Override
	public List<ModuleMenuDTO> getModulesWithMenus(Integer roleId) {
		return repo.getModulesWithMenus(roleId);
	}

	@Override
	public Integer batchInsertOrUpdateSetPermissionTestR(List<SetPermissionRespones> dataToInsertOrUpdate) {
		return repo.batchInsertOrUpdateSetPermissionTestR(dataToInsertOrUpdate);
	}

	@Override
	public Integer batchUpdateSetPermissionTestR(List<SetPermissionRespones> dataToUpdate) {
		return repo.batchUpdateSetPermissionTestR(dataToUpdate);
	}

	@Override
	public List<Department> getAllDept() {
		return deptRepo.findAll();
	}

	@Override
	public List<MRoleEntity> getAllRoleByDepartment(Long departmentId) {
		return roleRepo.findAllByDepartmentId(departmentId);
	}

}
