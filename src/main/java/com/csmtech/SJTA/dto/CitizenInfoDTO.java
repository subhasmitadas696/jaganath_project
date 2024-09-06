package com.csmtech.SJTA.dto;

import java.math.BigInteger;

import lombok.AllArgsConstructor;
/**
 * @Auth Prasanta Kumar Sethi
 */

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CitizenInfoDTO {
	private BigInteger userId;
	private Character userType;
	private String fullName;
	private String mobileNo;
	private String emailId;
}
