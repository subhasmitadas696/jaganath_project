package com.csmtech.SJTA.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalActionUpdateDTO {

	Integer newPendingAtRoleId;
	Integer newApplicationStatusId;
	Integer newUpdatedBy;
	boolean newStatus;
	Integer newApprovalActionId;
	String newRemark;
	Integer newApprovalLevel;
	Integer landApplicantId;
	Integer roleId;
}
