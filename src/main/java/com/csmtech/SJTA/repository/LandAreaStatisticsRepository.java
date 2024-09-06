package com.csmtech.SJTA.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.csmtech.SJTA.dto.StatisticsDTO;

@Repository
public class LandAreaStatisticsRepository {
	@Autowired
	private EntityManager entityManager;

	public StatisticsDTO fetchStatisticsInfo() {
		String nativeQuery = "SELECT 12233 as plot_area, '12' as district, '87' as tahsil, 3000 as village";
		List<Object[]> result = entityManager.createNativeQuery(nativeQuery).getResultList();

		if (result.isEmpty()) {
			return null; // Handle the case where there is no result.
		}

		Object[] row = result.get(0);
		StatisticsDTO statisticsDTO = new StatisticsDTO();
		statisticsDTO.setPlotArea((Integer) row[0]);
		statisticsDTO.setDistrict((String) row[1]);
		statisticsDTO.setTahsil((String) row[2]);
		statisticsDTO.setVillage((Integer) row[3]);
		entityManager.close();
		return statisticsDTO;
	}
}
