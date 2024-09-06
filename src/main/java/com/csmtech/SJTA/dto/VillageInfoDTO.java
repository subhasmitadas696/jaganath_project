package com.csmtech.SJTA.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VillageInfoDTO implements Serializable {

	/**
	 * @author rashmi.jena
	 */
	private static final long serialVersionUID = 8989142196719303747L;

	private String villageCode;
    private String villageName;
    private BigInteger totalKatha;
    private BigInteger totalPlot;
    private BigDecimal totalArea;
    private String extent;
}
