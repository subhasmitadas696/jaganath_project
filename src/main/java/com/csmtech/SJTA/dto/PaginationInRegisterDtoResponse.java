package com.csmtech.SJTA.dto;

import java.io.Serializable;
import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationInRegisterDtoResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// pageNumber :: what is the page number like 1,2,3,4,5 NEXT,Previous
	// pageSize :: how much record we want in a single page like:: 5,10,20,30
	BigInteger roleId;
	Integer createdBy;
	String applicantName;
	String fullName;
	Integer pageNumber;
	Integer pageSize;
	String pageType;
	String searchapplicantName;
	String mobileNo;
	String serarchUniqueId;
	String serachPlotNo;
}
