package com.csmtech.SJTA.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlotDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String plot_id;
	private String plot_name;
	private BigDecimal total_area;
	private BigDecimal purchase_area;
	

}
