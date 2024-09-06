package com.csmtech.SJTA.dto;

import java.math.BigDecimal;
import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LandPlotViewDTO {

	String plotNoId;
	BigDecimal totalArea;
	BigDecimal purchaseArea;
	String varietiesId;
	BigDecimal purchasearea;

}
