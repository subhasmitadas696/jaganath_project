package com.csmtech.SJTA.repository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.csmtech.SJTA.dto.QueryDetailsDTO;

import lombok.extern.slf4j.Slf4j;

/**
 * @Auth Prasanta Kumar Sethi
 */

@Repository
@Slf4j
public class QueryRepository {

	@Autowired
	private EntityManager entityManager;

	@Transactional
	public String insertQuery(BigInteger id, String name, String mobileNo, String query) {
		// Assuming the table name is "query_table"
		String nativeQuery = "INSERT INTO raise_query (name, mobile_no, query, status) "
				+ "VALUES (:name, :mobileNo, :query, '0')";
		entityManager.createNativeQuery(nativeQuery)
//                .setParameter("id", id.longValue())
				.setParameter("name", name).setParameter("mobileNo", mobileNo).setParameter("query", query)
				.executeUpdate();
		entityManager.close();
		return nativeQuery;
	}

	public List<QueryDetailsDTO> getQueryDetails() {
		String nativeQuery = "SELECT name, mobile_no,query FROM raise_query where status='0' ";

		List<Object[]> results = entityManager.createNativeQuery(nativeQuery).getResultList();

		List<QueryDetailsDTO> queryDetailsList = new ArrayList<>();
		for (Object[] result : results) {
			QueryDetailsDTO queryDetails = new QueryDetailsDTO();
			queryDetails.setName((String) result[0]);
			queryDetails.setMobileno((String) result[1]);
			queryDetails.setQuery((String) result[2]);
			queryDetailsList.add(queryDetails);
		}

		return queryDetailsList;
	}

	public List<QueryDetailsDTO> getQueryDetailsSearch() {
		String nativeQuery = "SELECT name, mobile_no,query FROM raise_query where status='0' ";

		List<Object[]> results = entityManager.createNativeQuery(nativeQuery).getResultList();

		List<QueryDetailsDTO> queryDetailsList = new ArrayList<>();
		for (Object[] result : results) {
			QueryDetailsDTO queryDetails = new QueryDetailsDTO();
			queryDetails.setName((String) result[0]);
			queryDetails.setMobileno((String) result[1]);
			queryDetails.setQuery((String) result[2]);
			queryDetailsList.add(queryDetails);
		}

		return queryDetailsList;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<QueryDetailsDTO> getPendingQueriesSearch(String name) {
		String sqlQuery = "SELECT name, mobile_no, query FROM raise_query "
				+ "WHERE status = '0' AND name ILIKE :name  OR mobile_no ILIKE :mobileNO";

		List<Object[]> results = entityManager.createNativeQuery(sqlQuery).setParameter("name", "%" + name + "%")
				.setParameter("mobileNO", "%" + name + "%").getResultList();

		List<QueryDetailsDTO> queryDetailsList = new ArrayList<>();
		for (Object[] result : results) {
			QueryDetailsDTO queryDetails = new QueryDetailsDTO();
//			queryDetails.setQueryId((BigInteger) result[0]);
			queryDetails.setName((String) result[0]);
			queryDetails.setMobileno((String) result[1]);
			queryDetails.setQuery((String) result[2]);
			queryDetailsList.add(queryDetails);
		}

		return queryDetailsList;
	}

}
