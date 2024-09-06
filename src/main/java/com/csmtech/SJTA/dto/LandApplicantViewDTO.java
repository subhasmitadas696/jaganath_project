package com.csmtech.SJTA.dto;

import java.math.BigDecimal;
import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LandApplicantViewDTO {
	BigInteger landApplicantId;

	String applicantNo;

	String applicantName;
	String fatherName;
	String mobileNo;
	String emailAddress;
	String docType;
	String docRefNo;
	String docsPath;
	String currStateId;
	String currDistrictId;
	String currBlockId;
	String currGpId;
	String currVillageId;
	String currPoliceStation;
	String currPostOffice;
	String currStreetNo;
	String currHouseNo;
	String currPinCode;

	String preStateId;
	String preDistrictId;
	String preBlockId;
	String preGpId;
	String preVillageId;
	String prePoliceStation;
	String prePostOffice;
	String preStreetNo;
	String preHouseNo;
	String prePinCode;
	String plotDistrictId;
	String plotTehsilId;
	String plotKhataNoId;
	String plotMouzaId;
	Short pendingRoleId;
	
	

	

}
