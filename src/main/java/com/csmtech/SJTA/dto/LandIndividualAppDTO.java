package com.csmtech.SJTA.dto;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LandIndividualAppDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String selDistrictName;
	private String selTehsilName;
	private String selMouza;
	private String selKhataNo;
	private String selPlotNo;
	private Double txtTotalRakba;
	private Double txtPurchaseRakba;
	//private Short selVarieties;
	private BigInteger intCreatedBy;
	private BigInteger intLandApplicantId;
	
	private List<PlotDetails> plot_details;

}
