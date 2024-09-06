package com.csmtech.SJTA.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KhatianPlotDTO implements Serializable {
	
	/**
	 * @author rashmi.jena
	 */
	private static final long serialVersionUID = 5068926817836867829L;
	
	private String khatianCode;
    private String khataNo;
    private String plotNo;
    private BigDecimal areaAcre;

}
