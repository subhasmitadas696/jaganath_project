package com.csmtech.SJTA.dto;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * @Auth  GuruPrasad
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigInteger userId;
	private String userName;
	private String fullName;
	private String mobileNo;
	private String emailId;
	private BigInteger updatedBy;
	private Date updatedOn;
	private String password;
	private BigInteger departmentId;
	private BigInteger roleId;
	private BigInteger createdBy;
//	private String otp;
//	private String userType;
//	private Date createdOn;
//	private boolean status;

}
