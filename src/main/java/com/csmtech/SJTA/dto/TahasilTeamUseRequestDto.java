package com.csmtech.SJTA.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TahasilTeamUseRequestDto {

	private String tahasilCode;
	private String password;
	String captchaId;
	Integer answer;
	String tahasilcodeselct;
	
	public TahasilTeamUseRequestDto(String tahasilCode, String password) {
		super();
		this.tahasilCode = tahasilCode;
		this.password = password;
	}
}
