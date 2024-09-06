/**
 * @author prasanta.sethi
 */
package com.csmtech.SJTA.service;

import java.math.BigInteger;
import java.util.List;

import com.csmtech.SJTA.dto.QueryDetailsDTO;

public interface QueryService {

	public String insertQuery(BigInteger id, String name, String mobileNo, String query);

	public List<QueryDetailsDTO> getQueryDetailsById();

	List<QueryDetailsDTO> getQueryDetailsBySearch();
}
