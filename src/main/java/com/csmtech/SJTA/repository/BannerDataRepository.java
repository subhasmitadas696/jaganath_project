package com.csmtech.SJTA.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class BannerDataRepository {

	@PersistenceContext
	private EntityManager entityManager;
	

	@SuppressWarnings("unchecked")
	public List<Object[]> getDistrictCodes() {
		
		 String query="SELECT '11' AS district_code,'321' As plot,'5' As mouza,'15' As tahasil  \r\n"
				+ "UNION \r\n"
				+ "SELECT '08' AS district_code ,'322' As plot,'6' As mouza,'18' As tahasil  \r\n"
				+ "UNION \r\n"
				+ "SELECT '12' AS district_code,'444' As plot,'8' As mouza,'20' As tahasil ";
		
		List<Object[]> resultList = entityManager.createNativeQuery(query).getResultList();
		return resultList;
	}

}
