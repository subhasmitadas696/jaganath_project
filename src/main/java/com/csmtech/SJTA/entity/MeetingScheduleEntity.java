package com.csmtech.SJTA.entity;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "meeting_schedule",schema = "application")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeetingScheduleEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "meeting_schedule_id")
	private BigInteger meetingScheduleId;
	
	@Column(name = "district_code")
	private String districtCode;
	
	@Column(name = "tahasil_code")
	private String tahasilCode;
	
	@Column(name = "village_code")
	private String villageCode;
	
	@Column(name = "khatian_code")
	private String khatianCode;
	
	@Column(name = "plot_code")
	private String plotCode;
	
	@Column(name = "meeting_date")
	private Date meetingDate;
	
	@Column(name = "meeting_purpose")
	private String meetingPurpose;
	
	@Column(name = "meeting_level_id")
	private Short meetingLevelId;
	
	@Column(name = "created_by")
	private BigInteger createdBy;
	
	@CreationTimestamp
	@Column(name = "created_on")
	private Date createdOn;
	
	@Column(name = "updated_by")
	private BigInteger updatedBy;
	
	@Column(name = "updated_on")
	private Date updatedOn;
	
	@Column(name = "status")
	private Boolean status;
}
