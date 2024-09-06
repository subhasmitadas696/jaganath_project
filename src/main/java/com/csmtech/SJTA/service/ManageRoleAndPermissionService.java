package com.csmtech.SJTA.service;

import java.util.List;

import com.csmtech.SJTA.dto.ModuleMenuDTO;
import com.csmtech.SJTA.dto.RoleDTO;
import com.csmtech.SJTA.dto.SetPermissionRespones;
import com.csmtech.SJTA.entity.Department;
import com.csmtech.SJTA.entity.MRoleEntity;

public interface ManageRoleAndPermissionService {
	
	public List<RoleDTO> getRoles();
	
	public List<ModuleMenuDTO> getModulesWithMenus(Integer roleId);
	
	public Integer batchInsertOrUpdateSetPermissionTestR(List<SetPermissionRespones> dataToInsertOrUpdate);
	
	public Integer batchUpdateSetPermissionTestR(List<SetPermissionRespones> dataToUpdate);

	public List<Department> getAllDept();

	public List<MRoleEntity> getAllRoleByDepartment(Long departmentId);

}
