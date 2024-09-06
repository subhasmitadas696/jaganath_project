package com.csmtech.SJTA.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.csmtech.SJTA.dto.MenuDTO;
import com.csmtech.SJTA.dto.ModuleMenuDTO;
import com.csmtech.SJTA.dto.RoleDTO;
import com.csmtech.SJTA.dto.SetPermissionRespones;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class ManageRoleAndPermissionReposiory {

	@Autowired
	private EntityManager entity;

	Long currentModuleId = null;
	ModuleMenuDTO currentModuleMenuDTO = null;

	@Transactional
	public List<RoleDTO> getRoles() {
		String query = "SELECT role_id, role_name FROM m_role";
		List<Object[]> resultList = entity.createNativeQuery(query).getResultList();

		List<RoleDTO> roleList = new ArrayList<>();
		for (Object[] result : resultList) {
			RoleDTO dto = new RoleDTO();
			dto.setRoleId(((Number) result[0]).longValue());
			dto.setRoleName((String) result[1]);
			roleList.add(dto);
		}

		return roleList;
	}

	@Transactional
	public List<ModuleMenuDTO> getModulesWithMenus(Integer roleId) {
		String nativeQuery = "SELECT module.module_id, module.module_name, menu.menu_id, menu.menu_name, "
				+ "nullif(sprw.menu_id, 0) as selected_menu " + "FROM public.m_module AS module "
				+ "INNER JOIN public.m_menu AS menu ON module.module_id = menu.module_id "
				+ "LEFT JOIN set_permission_role_wise sprw ON module.module_id = sprw.module_id "
				+ "AND menu.menu_id = sprw.menu_id AND sprw.status = b'0' AND sprw.role_id = :roleId "
				+ "WHERE module.status = b'0' AND menu.status = b'0' " + "ORDER BY module.sl_no, menu.sl_no";

		@SuppressWarnings("unchecked")
		List<Object[]> resultList = entity.createNativeQuery(nativeQuery).setParameter("roleId", roleId)
				.getResultList();

		List<ModuleMenuDTO> moduleMenuDTOs = new ArrayList<>();
		Long currentModuleId = null;
		ModuleMenuDTO currentModuleMenuDTO = null;

		for (Object[] result : resultList) {
			Long moduleId = ((Number) result[0]).longValue();
			String moduleName = (String) result[1];
			Long menuId = result[2] != null ? ((Number) result[2]).longValue() : null;
			String menuName = (String) result[3];
			Long selectedMenu = result[4] != null ? ((Number) result[4]).longValue() : null;

			if (currentModuleId == null || !currentModuleId.equals(moduleId)) {
				// New module encountered, create a new ModuleMenuDTO
				currentModuleMenuDTO = new ModuleMenuDTO();
				currentModuleMenuDTO.setModuleId(moduleId);
				currentModuleMenuDTO.setModuleName(moduleName);
				currentModuleMenuDTO.setMenus(new ArrayList<>());
				moduleMenuDTOs.add(currentModuleMenuDTO);
				currentModuleId = moduleId;
			}

			// Create a new MenuDTO and set its properties
			MenuDTO menuDTO = new MenuDTO();
			menuDTO.setMenuId(menuId);
			menuDTO.setMenuName(menuName);
			menuDTO.setSelectedMenu(selectedMenu);

			currentModuleMenuDTO.getMenus().add(menuDTO);
		}

		return moduleMenuDTOs;
	}

	// insert batch record
	@Transactional
	public Integer batchInsertOrUpdateSetPermissionTestR(List<SetPermissionRespones> dataToInsertOrUpdate) {
	    log.info("inside save repository of set permission");
	    if (dataToInsertOrUpdate == null || dataToInsertOrUpdate.isEmpty()) {
	        // If there is no data to insert or update, return early
	        return 0;
	    }

	    StringBuilder query = new StringBuilder(
	            "INSERT INTO public.set_permission_role_wise (permission_role_wise_id, role_id, module_id, menu_id, created_by, status) ");

	    query.append("VALUES ");
	    
	    int index = 0;
	    for (SetPermissionRespones data : dataToInsertOrUpdate) {
	        if (index > 0) {
	            query.append(", ");
	        }
	        query.append("(nextval('set_permission_role_wise_permission_role_wise_id_seq'), ")
	             .append(data.getRoleId()).append(", ").append(data.getModuleId()).append(", ")
	             .append(data.getMenuId())
	             .append(", (SELECT currval('set_permission_role_wise_permission_role_wise_id_seq')), B'0')");

	        index++;
	    }

	    return entity.createNativeQuery(query.toString()).executeUpdate();
	}



//	@Transactional
//	public Integer batchInsertOrUpdateSetPermissionTestR(List<SetPermissionTestRespones> dataToInsertOrUpdate) {
//		if (dataToInsertOrUpdate == null || dataToInsertOrUpdate.isEmpty()) {
//			 // If there is no data to insert or update, return early
//		}
//
//		StringBuilder query = new StringBuilder(
//				"INSERT INTO public.set_permission_test_r (role_id, module_id, menu_id) VALUES ");
//
//		int index = 0;
//		for (SetPermissionTestRespones data : dataToInsertOrUpdate) {
//			if (index > 0) {
//				query.append(", ");
//			}
//			query.append("(").append(data.getRoleId()).append(", ").append(data.getModuleId()).append(", ")
//					.append(data.getMenuId()).append(")");
//
//			index++;
//		}
//
//		query.append(" ON CONFLICT (role_id, module_id, menu_id) DO NOTHING");
//
//		return entity.createNativeQuery(query.toString()).executeUpdate();
//	}

	// update the status
	@Transactional
	public Integer batchUpdateSetPermissionTestR(List<SetPermissionRespones> dataToUpdate) {
		if (dataToUpdate == null || dataToUpdate.isEmpty()) {
			// If there is no data to update, return early
			return 0;
		}

		StringBuilder query = new StringBuilder(
				" UPDATE public.set_permission_role_wise SET status = B'1' WHERE (role_id, module_id, menu_id) IN ( ");

		int index = 0;
		for (SetPermissionRespones data : dataToUpdate) {
			if (index > 0) {
				query.append(" , ");
			}
			query.append("( ").append(data.getRoleId()).append(" , ").append(data.getModuleId()).append(" , ")
					.append(data.getMenuId()).append(" )");

			index++;
		}

		query.append(")");

		return entity.createNativeQuery(query.toString()).executeUpdate();
	}

}
