package com.csmtech.SJTA.entity;

import java.math.BigInteger;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Data
@Table(name = "land_application", schema = "public")
@Entity
public class Land_applicant {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "land_application_id")
	private Integer intId;

	@Column(name = "`applicant_name`")
	private String txtApplicantName;
	@Column(name = "`father_name`")
	private String txtFatherHusbandName;
	@Column(name = "`mobile_no`")
	private String txtMobileNo;
	@Column(name = "`email_address`")
	private String txtEmail;
	@Column(name = "`doc_type`")
	private Short selDocumentType;
	@Transient
	private String selDocumentTypeVal;
	@Column(name = "`doc_ref_no`")
	private String txtDocumentNo;
	@Column(name = "`docs_path`")
	private String fileUploadDocument;
	@Column(name = "`curr_state_id`")
	private Short selState;
	@Transient
	private String selStateVal;
	@Column(name = "`curr_district_id`")
	private Short selDistrict;
	@Transient
	private String selDistrictVal;
	@Column(name = "`curr_block_id`")
	private Integer selBlockULB;
	@Transient
	private String selBlockULBVal;
	@Column(name = "`curr_gp_id`")
	private Integer selGPWardNo;
	@Transient
	private String selGPWardNoVal;
	@Column(name = "`curr_village_id`")
	private Integer selVillageLocalAreaName;
	@Transient
	private String selVillageLocalAreaNameVal;
	@Column(name = "`curr_police_station`")
	private String txtPoliceStation;
	@Column(name = "`curr_post_office`")
	private String txtPostOffice;
	@Column(name = "`curr_street_no`")
	private String txtHabitationStreetNoLandmark;
	@Column(name = "`curr_house_no`")
	private String txtHouseNo;
	@Column(name = "`curr_pin_code`")
	private String txtPinCode;
	@Column(name = "`pre_state_id`")
	private Integer selState17;
	@Transient
	private String selState17Val;
	@Column(name = "`pre_district_id`")
	private Integer selDistrict18;
	@Transient
	private String selDistrict18Val;
	@Column(name = "`pre_block_id`")
	private Integer selBlockULB19;
	@Transient
	private String selBlockULB19Val;
	@Column(name = "`pre_gp_id`")
	private Integer selGPWARDNumber;
	@Transient
	private String selGPWARDNumberVal;
	@Column(name = "`pre_village_id`")
	private Integer selVillageLocalAreaName21;
	@Transient
	private String selVillageLocalAreaName21Val;
	// @Column(name = "inthistoryid")
	// private Integer intHistoryId;

	@Column(name = "created_by")
	private Integer intCreatedBy;

	// @Column(name = "intonlineserviceid")
	// private Integer intOnlineserviceId;

	@Column(name = "updated_by")
	private Integer intUpdatedBy;

	@Column(name = "created_on")
	@CreationTimestamp
	private Date dtmCreatedOn;

	@Column(name = "updated_on")
	@UpdateTimestamp
	private Date stmUpdatedOn;

    @Column(name = "deleted_flag",columnDefinition = "bit(1) default false")
	private boolean bitDeletedFlag;

	// @Column(name = "jsonopttxtdetails")
	// private String jsonopttxtdetails;

	@Column(name = "`pre_police_station`")
	private String txtPoliceStation22;
	@Column(name = "`pre_post_office`")
	private String txtPostOffice23;
	@Column(name = "`pre_street_no`")
	private String txtHabitationStreetNoLandmark24;
	@Column(name = "`pre_house_no`")
	private String txtHouseNo25;
	@Column(name = "`pre_pin_code`")
	private String txtPinCode26;

	@Column(name = "`doc_name`")
	private String txtDocName;

	@Column(name = "application_no")
	private String applicantNo;
	
	@Column(name = "`save_status`")
	private Short saveStatus = 1;
	
	
	@Column(name = "district_code")
	private String selDistrictName;
	@Transient
	private String selDistrictNameVal;
	
	@Column(name = "`tehsil_code`")
	private String selTehsilName;

	@Transient
	private String selTehsilNameVal;
	@Column(name = "`village_code`")
	private String selMouza;
	@Transient
	private String selMouzaVal;
	@Column(name = "`khatian_code`")
	private String selKhataNo;
	@Transient
	private String selKhataNoVal;
}