package com.csmtech.SJTA.serviceImpl;

import java.math.BigInteger;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.csmtech.SJTA.dto.QueryDetailsDTO;
import com.csmtech.SJTA.repository.QueryRepository;
import com.csmtech.SJTA.service.QueryService;

/**
 * @Auth Prasanta Kumar Sethi
 */

@Service
public class QueryServiceImpl implements QueryService {

	@Autowired
	private QueryRepository queryRepository;

	@Override
	public String insertQuery(BigInteger id, String name, String mobileNo, String query) {

		return queryRepository.insertQuery(id, name, mobileNo, query);
	}

	@Override
	public List<QueryDetailsDTO> getQueryDetailsBySearch() {
		return queryRepository.getQueryDetails();
	}

	@Override
	public List<QueryDetailsDTO> getQueryDetailsById() {
		return queryRepository.getQueryDetails();
	}

}
