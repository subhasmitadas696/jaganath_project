package com.csmtech.SJTA.dto;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;

import com.csmtech.SJTA.repository.PaginationInRegisterClassRepository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationInRegisterDTO {
	

	 BigInteger userId ;
     String userName ;
     String fullName ;
     String mobileNo ;
     String emailId ;
     Character userType ;
     BigInteger createdBy ;
     BigInteger countint;
     
}
