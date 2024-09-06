package com.csmtech.SJTA.dto;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LandApplicantDTO {

	private BigInteger landApplicantId;
	private String applicantNo;
	private String applicantName;
	private String fatherName;
	private String mobileNo;
	private String emailId;
//	private Short docType;
	private String docRefNo;
	private String docsPath;
	private String totalArea;
	private String purchaseArea;
	private String blockName;
	private String gpName;
	private String plotName;
	private String stateName;
	private String districtName;
	private String tehsilName;
	private String mouzaName;
	private String khataNo;
	private String plotNo;
	private Date createdOn;
	private String docName;
	private String currPoliceStation;
	private String prePoliceStation;
	private String currPostOffice;
	private String prePostOffice;
	private String currStreetNo;
	private String preStreetNo;
	private String currPinCode;
	private String prePinCode;
	private String preHouseNo;
	private String currHouseNo;

	private boolean bitDeletedFlag;
//	private String documentName;
	private BigInteger countint;

	private List<DocumentDTO> documents;
	private List<PlotDTO> plots;
	
	
	
	//rajat changess
//	private BigInteger landApplicantId;
//	private String applicantNo;
//	private String applicantName;
//	private String mobileNo;
//	private String districtName;
//	private String tehsilName;
//	private String mouzaName;
//	private String khataNo;
	private String applicationStatus;
	private Date actionOn;
	private String remark;

}
