package com.csmtech.SJTA.dto;

import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * @Auth  Guru Prasad
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequest {
	private BigInteger userId;
//	private String mobileNo;
	private String currentPassword;
	private String newPassword;
	private String confirmPassword;
	private String updatedBy;

}
