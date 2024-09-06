package com.csmtech.SJTA.dto;

import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsDTO {
	
	private BigInteger userId;
	private Character userType;
	private String fullName;
	private String mobileNo;
	private String emailId;
	private BigInteger countint;

}
