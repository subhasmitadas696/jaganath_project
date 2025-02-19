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

@Data
@Table(name = "quarry_land_schedule")
@Entity
public class Quarry_land_schedule {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "quarry_land_sch_id")
	private Integer intId;

	@Column(name = "`quarry_agreement_no`")
	private String txtQuarryAgreementNo;
	@Column(name = "`ditrict_id`")
	private Integer selDistrict;
	@Transient
	private String selDistrictVal;
	@Column(name = "`tehsil_id`")
	private Integer selTehsil;
	@Transient
	private String selTehsilVal;
	@Column(name = "`mouza_id`")
	private Integer selMouza;
	@Transient
	private String selMouzaVal;
	@Column(name = "`khata_no_id`")
	private Integer selKhataNo;
	@Transient
	private String selKhataNoVal;
	@Column(name = "`plot_no_id`")
	private Integer selPlotNo;
	@Transient
	private String selPlotNoVal;
	@Column(name = "`area`")
	private String txtAreainAcre;
	@Column(name = "`environment_clearance_doc`")
	private String fileEnvironmentClearance;
	@Column(name = "`consent_establish_doc`")
	private String fileConsenttoEstablish;
	@Column(name = "`cte_doc`")
	private String fileCTEApprovalfromPollutionControlBoard;
	@Column(name = "`cto_doc`")
	private String fileCTOConsenttoOperate;
	@Column(name = "`agreement_copy_doc`")
	private String fileAgreementCopy;
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

	@Column(name = "deleted_flag")
	private Boolean bitDeletedFlag = false;

	// @Column(name = "jsonopttxtdetails")
	// private String jsonopttxtdetails;

}