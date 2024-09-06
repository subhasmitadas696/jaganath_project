package com.csmtech.SJTA.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DistrictCode {
	
	private String districtCode;
	private String plot;
	private String mouza;
	private String tahasil;

}
