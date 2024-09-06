package com.csmtech.SJTA.dto;

import java.io.Serializable;
import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddOfficerDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BigInteger userId;
	private String fullName;
	private String mobileNo;
	private String emailId;
	private Integer departmentId;
	private String departmentName;
	private Integer roleId;
	private String roleName;
	private BigInteger countint;

}
