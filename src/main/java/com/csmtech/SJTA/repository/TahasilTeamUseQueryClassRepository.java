package com.csmtech.SJTA.repository;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.csmtech.SJTA.dto.TahasilTeamUseRequestDto;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class TahasilTeamUseQueryClassRepository {
	
	@PersistenceContext
	@Autowired
	private EntityManager entity;
	
	@Transactional
	public List<Object[]> getTahasilsWithStatistics(String districtCode) {
        String nativeQuery = "SELECT * FROM (SELECT A.tahasil_code, A.tahasil_name, " +
            "(SELECT COUNT(village_code) FROM land_bank.village_master " +
            "WHERE tahasil_code = A.tahasil_code) AS total_mouza, " +
            "(SELECT COUNT(khata_no) FROM land_bank.khatian_information WHERE village_code IN " +
            "(SELECT village_code FROM land_bank.village_master WHERE tahasil_code = A.tahasil_code)) AS total_katha, " +
            "(SELECT COUNT(plot_code) FROM land_bank.plot_information WHERE khatian_code IN " +
            "(SELECT khatian_code FROM land_bank.khatian_information WHERE village_code IN " +
            "(SELECT village_code FROM land_bank.village_master WHERE tahasil_code = A.tahasil_code))) AS total_plot, " +
            "(SELECT SUM(area_acre) FROM land_bank.plot_information WHERE khatian_code IN " +
            "(SELECT khatian_code FROM land_bank.khatian_information WHERE village_code IN " +
            "(SELECT village_code FROM land_bank.village_master WHERE tahasil_code = A.tahasil_code))) AS total_area " +
            "FROM land_bank.tahasil_master A " +
            "WHERE A.district_code = :districtCode " +
            "ORDER BY A.tahasil_name ASC) X WHERE X.total_plot > 0";
        Query query = entity.createNativeQuery(nativeQuery)
            .setParameter("districtCode", districtCode);
        @SuppressWarnings("unchecked")
		List<Object[]> results = query.getResultList();
        
       return results;
    }
	

	
	public List<TahasilTeamUseRequestDto> getTahasilLoginDetails(String tahasilCode) {
        String nativeQuery = "SELECT tahasil_code, password FROM public.tahasil_login_details  "
        		+ "  where tahasil_code=:tahasilCode ";
        Query query = entity.createNativeQuery(nativeQuery).setParameter("tahasilCode", tahasilCode);

        @SuppressWarnings("unchecked")
        List<Object[]> resultList = query.getResultList();
        List<TahasilTeamUseRequestDto> loginDetailsList = mapToDTO(resultList);

        return loginDetailsList;
    }

	private List<TahasilTeamUseRequestDto> mapToDTO(List<Object[]> resultList) {
	    return resultList.stream()
	        .map(objects -> new TahasilTeamUseRequestDto((String) objects[0], (String) objects[1]))
	        .collect(Collectors.toList());
	}

//	public Integer CheckLogin(SparkTeamUseRequestDto dto) {
//		if (dto.getTahasilCode().equals("7008943470") && dto.getPassword().equals("Admin@123")) {
//			return 1;
//		} else {
//			return 0;
//		}
//	}
	
	//tahasil_code
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Object[]> getVillageInfoForTahasil(String tahasilCode) {
		String sqlQuery = "SELECT  "
        		+ "    A.village_code,  "
        		+ "    A.village_name,  "
        		+ "    (  "
        		+ "        SELECT COUNT(khata_no)  "
        		+ "        FROM land_bank.khatian_information  "
        		+ "        WHERE village_code = A.village_code  "
        		+ "    ) AS total_katha,  "
        		+ "    (  "
        		+ "        SELECT COUNT(plot_code)  "
        		+ "        FROM land_bank.plot_information  "
        		+ "        WHERE khatian_code IN (  "
        		+ "            SELECT khatian_code  "
        		+ "            FROM land_bank.khatian_information  "
        		+ "            WHERE village_code = A.village_code  "
        		+ "        )  "
        		+ "    ) AS total_plot,  "
        		+ "    (  "
        		+ "        SELECT SUM(area_acre)  "
        		+ "        FROM land_bank.plot_information  "
        		+ "        WHERE khatian_code IN (  "
        		+ "            SELECT khatian_code  "
        		+ "            FROM land_bank.khatian_information  "
        		+ "            WHERE village_code = A.village_code  "
        		+ "        )  "
        		+ "    ) AS total_area,  "
        		+ "	CAST(st_extent(ST_Transform(ss.geom, 3857)) AS character varying) as extent  "
        		+ "FROM  "
        		+ "    land_bank.village_master A  "
        		+ "    JOIN land_bank.gis_village_boundary ss ON (ss.vill_code = A.village_code)  "
        		+ "WHERE  "
        		+ "    A.tahasil_code =:tahasilCode  "
        		+ "GROUP BY  "
        		+ "    A.village_code,  "
        		+ "    A.village_name,  "
        		+ "    ss.geom  "
        		+ "ORDER BY  "
        		+ "    A.village_name ASC   "
        		+ "";
        
       

        Query query = entity.createNativeQuery(sqlQuery)
                .setParameter("tahasilCode", tahasilCode);
            List<Object[]> results = query.getResultList();
            
           return results;
    }
	
	//village Gis data return
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Object[]> getVillageInfoForTahasilGisData() {
		 String sqlQuery = "SELECT vill_code,"
		            + " vill_name ,"
		            + " CAST(st_extent(ST_Transform(geom, 3857)) AS character varying) as extent "
		            + " FROM land_bank.gis_village_boundary  "
		            + " GROUP BY vill_code, vill_name ORDER BY vill_name";
        System.out.println(sqlQuery);
 
        Query query = entity.createNativeQuery(sqlQuery);
            return query.getResultList();
    }
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getKhatianPlotsForVillage(String villageCode) {
        String sqlQuery = "SELECT A.khatian_code, A.khata_no, B.plot_no, B.area_acre " +
                "FROM land_bank.khatian_information A " +
                "INNER JOIN land_bank.plot_information B ON A.khatian_code = B.khatian_code " +
                "WHERE A.village_code = :villageCode";

        Query query = entity.createNativeQuery(sqlQuery)
                .setParameter("villageCode", villageCode);
            return query.getResultList();
       
    }
	
	//get dist data
	@SuppressWarnings("unchecked")
	public List<Object[]> getDistricts() {
		String nativeQuery = "SELECT district_code, district_name FROM land_bank.district_master";
		Query query = entity.createNativeQuery(nativeQuery);
		List<Object[]> results = query.getResultList();
		log.info("Sucess..Execute and return the result");	
		return results;
	}

}
