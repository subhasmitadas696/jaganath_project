/**
 * 
 */
package com.csmtech.SJTA.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.csmtech.SJTA.dto.StatisticsDTO;
import com.csmtech.SJTA.repository.LandAreaStatisticsRepository;
import com.csmtech.SJTA.service.LandAreaStatisticsService;

/**
 * 
 */
@Service
public class LandAreaStatisticsServiceImpl implements LandAreaStatisticsService {

	@Autowired
	private LandAreaStatisticsRepository landAreaStatisticsRepository;

	public StatisticsDTO fetchStatisticsInfo() {

		return landAreaStatisticsRepository.fetchStatisticsInfo();

	}

}
