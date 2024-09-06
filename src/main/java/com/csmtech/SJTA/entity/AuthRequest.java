package com.csmtech.SJTA.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class AuthRequest {
//
//    private String mobileno;
//    private String password;
//    
//    
//}

import java.util.Random;
import java.util.Scanner;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
	private String mobileno;
	private String password;
	Integer answer;
	String captchaId;

}
