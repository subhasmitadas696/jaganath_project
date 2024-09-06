package com.csmtech.SJTA.dto;

import java.math.BigInteger;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeetingScheduleDTO {

	private BigInteger meetingScheduleId;
	
	private String districtCode;
	
	private String tahasilCode;
	
	private String villageCode;
	
	private String khatianCode;
	
	private String plotCode;
	
	private Date meetingDate;
	
	private String meetingPurpose;
	
	private Short meetingLevelId;
	
	private BigInteger createdBy;
	
	private Date createdOn;
	
	private BigInteger updatedBy;
	
	private Date updatedOn;
	
	private Boolean status;
}
