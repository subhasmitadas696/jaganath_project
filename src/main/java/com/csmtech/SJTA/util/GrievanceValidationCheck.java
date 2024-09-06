package com.csmtech.SJTA.util;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GrievanceValidationCheck {
	private static final Logger logger = LoggerFactory.getLogger(GrievanceValidationCheck.class);

	public static String BackendValidation(JSONObject obj) {
		logger.info("Inside BackendValidation method of GrievanceValidationCheck");
		String errMsg = null;
		Integer errorStatus = 0;
		if (errorStatus == 0 && CommonValidator.maxLengthCheck((String) obj.get("txtName"), 45)) {
			errorStatus = 1;
			errMsg = "Name maxmimum length should be 45";
		}
		if (errorStatus == 0 && CommonValidator.maxLengthCheck((String) obj.get("txtFatherName"), 45)) {
			errorStatus = 1;
			errMsg = "Father Name maxmimum length should be 45";
		}
		if (errorStatus == 0 && CommonValidator.maxLengthCheck((String) obj.get("txtOtherInformation"), 120)) {
			errorStatus = 1;
			errMsg = "Other Information maxmimum length should be 120";
		}
		if (errorStatus == 0 && CommonValidator.minLengthCheck((String) obj.get("txtMobileNo"), 10)) {
			errorStatus = 1;
			errMsg = "Mobile No  minimum length should be 10";
		}
		if (errorStatus == 0 && CommonValidator.maxLengthCheck((String) obj.get("txtMobileNo"), 10)) {
			errorStatus = 1;
			errMsg = "Mobile No maxmimum length should be 10";
		}
		if (errorStatus == 0 && CommonValidator.isNumericKey(obj.get("txtMobileNo").toString())) {
			errorStatus = 1;
			errMsg = "Mobile No should be Numeric!";
		}
		if (errorStatus == 0 && CommonValidator.BlankCheckRdoDropChk(obj.get("selDistrict13").toString())) {
			errorStatus = 1;
			errMsg = "District  should not be empty !";
		}
		if (errorStatus == 0 && CommonValidator.BlankCheckRdoDropChk(obj.get("selTahasil").toString())) {
			errorStatus = 1;
			errMsg = "Tahasil  should not be empty !";
		}
		if (errorStatus == 0 && CommonValidator.BlankCheckRdoDropChk(obj.get("selVillage15").toString())) {
			errorStatus = 1;
			errMsg = "Village  should not be empty !";
		}
		if (errorStatus == 0 && CommonValidator.BlankCheckRdoDropChk(obj.get("selKhataNo").toString())) {
			errorStatus = 1;
			errMsg = "Khata No.  should not be empty !";
		}
		if (errorStatus == 0 && CommonValidator.BlankCheckRdoDropChk(obj.get("selPlotNo").toString())) {
			errorStatus = 1;
			errMsg = "Plot No.  should not be empty !";
		}
		if (errorStatus == 0 && CommonValidator.isEmpty(obj.get("txtTotalAreainacre").toString())) {
			errorStatus = 1;
			errMsg = "Total Area (in acre) should not  be empty!";
		}
		if (errorStatus == 0 && CommonValidator.BlankCheckRdoDropChk(obj.get("selModeofOccupation").toString())) {
			errorStatus = 1;
			errMsg = "Mode of Occupation  should not be empty !";
		}
		if (errorStatus == 0 && CommonValidator.maxLengthCheck((String) obj.get("txtOccupationDetails"), 45)) {
			errorStatus = 1;
			errMsg = "Occupation Details maxmimum length should be 45";
		}
		if (errorStatus == 0 && CommonValidator.maxLengthCheck((String) obj.get("txtLandmark"), 120)) {
			errorStatus = 1;
			errMsg = "Landmark maxmimum length should be 120";
		}
		if (errorStatus == 0 && CommonValidator.isEmpty(obj.get("fileFileUpload").toString())) {
			errorStatus = 1;
			errMsg = "File Upload file should not be empty !";
		}
		if (errorStatus == 0 && CommonValidator.validateFile(obj.get("fileFileUpload").toString())) {
			errorStatus = 1;
			errMsg = "Invalid File Type !";
		}
		if (errorStatus == 0 && CommonValidator.isEmpty(obj.get("txtrTextarea").toString())) {
			errorStatus = 1;
			errMsg = "Textarea should not  be empty!";
		}
		if (errorStatus == 0 && CommonValidator.maxLengthCheck((String) obj.get("txtrTextarea"), 500)) {
			errorStatus = 1;
			errMsg = "Textarea maxmimum length should be 500";
		}
		return errMsg;
	}
}