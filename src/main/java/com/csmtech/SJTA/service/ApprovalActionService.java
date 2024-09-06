package com.csmtech.SJTA.service;

import java.math.BigInteger;
import java.util.List;

import com.csmtech.SJTA.dto.ApprovalActionResultDTO;
import com.csmtech.SJTA.dto.ApprovalActionUpdateDTO;

public interface ApprovalActionService {
	
	public List<ApprovalActionResultDTO> findApprovalActionsForRoleId(Long roleId);
	
	public Short updateApprovalProcss(ApprovalActionUpdateDTO approvalDto);
	
	public String messageData(Integer approvalActionId );

}
