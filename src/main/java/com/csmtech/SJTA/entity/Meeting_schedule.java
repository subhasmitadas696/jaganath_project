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
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@Table(name = "meeting_schedule", schema = "application")
@Entity
public class Meeting_schedule {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "meeting_schedule_id")
	private Integer intId;

	@Column(name = "district_code")
	private String selDistrict48;
	@Transient
	private String selDistrict48Val;
	@Column(name = "tahasil_code")
	private String selTahasil49;
	@Transient
	private String selTahasil49Val;
	@Column(name = "village_code")
	private String selVillage50;
	@Transient
	private String selVillage50Val;
	@Column(name = "khatian_code")
	private String selKhatian51;
	@Transient
	private String selKhatian51Val;
	@Column(name = "plot_code")
	private String selPlot52;
	@Transient
	private String selPlot52Val;
	@Column(name = "meeting_date")
	private Date txtMeetingDate53;
	@Column(name = "meeting_level_id")
	private Short selMeetingLevel55;
	@Transient
	private String selMeetingLevel55Val;
	@Column(name = "meeting_purpose")
	private String txtrMeetingPurpose54;
//	@Column(name = "inthistoryid")
//	private Integer intHistoryId;

	@Column(name = "created_by")
	private Integer intCreatedBy;
	
	@Column(name = "upload_mom")
	private String fileUploadDocument;

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

	@Column(name = "status")
	private Boolean bitDeletedFlag;

//	@Column(name = "jsonopttxtdetails")
//	private String jsonOptTxtDetails;
//
//	@Column(name = "intinsertstatus")
//	private Integer intInsertStatus = 1;

}