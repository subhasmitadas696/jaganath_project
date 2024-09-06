package com.csmtech.SJTA.repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.csmtech.SJTA.dto.MenuDataDto;
import com.csmtech.SJTA.dto.ModuleMenuDataDTO;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class MenuBindingRepository {

	@Autowired
	@PersistenceContext
	private EntityManager entityManager;

	  @Transactional
	    public List<ModuleMenuDataDTO> getModuleAndMenuByUserId(long userId) {
	        String subQuery = " (SELECT X.module_id, Y.module_name, NULL AS menu_id, NULL AS menu_name, NULL AS url_mapping_name FROM ("
	                        + "  SELECT DISTINCT module_id FROM set_permission_role_wise "
	                        + "  WHERE status = B'0' AND role_id IN "
	                        + "  (SELECT role_id FROM user_role WHERE user_id = :userId)  "
	                        + "  )"
	                        + "  X" 
	                        + " INNER JOIN m_module Y ON X.module_id = Y.module_id" 
	                        + " UNION ALL  "
	                        + " SELECT A.module_id, NULL AS module_name, A.menu_id, B.menu_name, B.url_mapping_name FROM set_permission_role_wise A "
	                        + " INNER JOIN m_menu B ON A.menu_id = B.menu_id" 
	                        + " WHERE A.module_id IN ("
	                        + " SELECT DISTINCT module_id FROM set_permission_role_wise"
	                        + " WHERE status = B'0' AND role_id IN (SELECT role_id FROM user_role WHERE user_id = :userId)"
	                        + " ) "
	                        + " AND A.status = B'0'" + " AND A.role_id IN (SELECT role_id FROM user_role WHERE user_id = :userId)"
	                        + " )";

	        String query = " SELECT module_id, module_name, menu_id, menu_name, url_mapping_name"
	                     + " FROM " + subQuery + " AS union_result";

	        try {
	            @SuppressWarnings("unchecked")
				List<Object[]> rows = entityManager.createNativeQuery(query)
	                                               .setParameter("userId", userId)
	                                               .getResultList();

	            Map<Short, ModuleMenuDataDTO> moduleMap = new HashMap<>();
	            List<ModuleMenuDataDTO> result = new ArrayList<>();

	            log.info(" :: getModuleAndMenuByUserId Start putting data !!");
	            for (Object[] row : rows) {
	                Short moduleId = (Short) row[0];
	                String moduleName = (String) row[1];
	                Short menuId = (Short) row[2];
	                String menuName = (String) row[3];
	                String urlMappingName = (String) row[4];

	                ModuleMenuDataDTO moduleData = moduleMap.get(moduleId);
	                if (moduleData == null) {
	                    moduleData = new ModuleMenuDataDTO(moduleId, moduleName);
	                    moduleMap.put(moduleId, moduleData);
	                    result.add(moduleData);
	                }

	                if (menuId != null && menuName != null) {
	                    MenuDataDto menuData = new MenuDataDto(menuId, menuName, urlMappingName);
	                    moduleData.addMenu(menuData);
	                }
	            }
	            log.info("::  getModuleAndMenuByUserId  Retturn the result !! ");

	            return result;
	        } catch (NoResultException e) {
	            // Handle the case when no results are found return the empty array
	            return new ArrayList<>();
	        }
	    }
}
