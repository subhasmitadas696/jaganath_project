package com.csmtech.SJTA.entity;

import javax.persistence.Entity;
import javax.persistence.Column;
import java.util.List;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.sql.Date;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.math.BigDecimal;

@Data
@Table(name = "grievance",schema = "public")
@Entity
public class Grievance {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "grievance_id")
	private Integer intId;

	@Column(name = "`month_id`")
	private Integer selMonthofUnauthorizedOccupation;
	@Transient
	private String selMonthofUnauthorizedOccupationVal;
	@Column(name = "`name`")
	private String txtName;
	@Column(name = "`father_name`")
	private String txtFatherName;
	@Column(name = "`district_id`")
	private Integer selDistrict;
	@Transient
	private String selDistrictVal;
	@Column(name = "`block_id`")
	private Integer selBlock;
	@Transient
	private String selBlockVal;
	@Column(name = "`gp_id`")
	private Integer selGP;
	@Transient
	private String selGPVal;
	@Column(name = "`village_id`")
	private Integer selVillage;
	@Transient
	private String selVillageVal;
	@Column(name = "`other_information`")
	private String txtOtherInformation;
	@Column(name = "`caste_name`")
	private String selCaste;
	@Transient
	private String selCasteVal;
	@Column(name = "`mobile_no`")
	private String txtMobileNo;
	@Column(name = "`disclose_details`")
	private Short chkDiscloseyourdetails;
	@Transient
	private String chkDiscloseyourdetailsVal;
	@Column(name = "`land_district_code`")
	private String selDistrict13;
	@Transient
	private String selDistrict13Val;
	@Column(name = "`land_tahasil_code`")
	private String selTahasil;
	@Transient
	private String selTahasilVal;
	@Column(name = "`village_code`")
	private String selVillage15;
	@Transient
	private String selVillage15Val;
	@Column(name = "`khatian_code`")
	private String selKhataNo;
	@Transient
	private String selKhataNoVal;
	@Column(name = "`plot_code`")
	private String selPlotNo;
	@Transient
	private String selPlotNoVal;
	@Column(name = "`area_acre`")
	private BigDecimal txtTotalAreainacre;
	@Column(name = "`extent_occupied`")
	private BigDecimal txtExtentOccupied;
	@Column(name = "`mode_of_occupation`")
	private Short selModeofOccupation;
	@Transient
	private String selModeofOccupationVal;
	@Column(name = "`other_occupation`")
	private String txtOccupationDetails;
	@Column(name = "`landmark`")
	private String txtLandmark;
	@Column(name = "`upload_file`")
	private String fileFileUpload;
	@Column(name = "`remarks`")
	private String txtrTextarea;
//	@Column(name = "inthistoryid")
//	private Integer intHistoryId;

	@Column(name = "created_by")
	private Integer intCreatedBy;

//	@Column(name = "intonlineserviceid")
//	private Integer intOnlineserviceId;

	@Column(name = "updated_by")
	private Integer intUpdatedBy;

	@Column(name = "created_on")
	@CreationTimestamp
	private Date dtmCreatedOn;

	@Column(name = "updated_on")
	@UpdateTimestamp
	private Date stmUpdatedOn;

	@Column(name = "deleted_flag", insertable = false, updatable = false)
	private Boolean bitDeletedFlag = false;

//	@Column(name = "jsonopttxtdetails")
//	private String jsonOptTxtDetails;
//
//	@Column(name = "intinsertstatus")
//	private Integer intInsertStatus = 1;

}