package com.csmtech.SJTA.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForgotPasswordRequest {
	String mobileNo;
	String otp;
	String newPassword;
	String confirmPassword;
	

}
