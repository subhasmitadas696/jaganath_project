package com.csmtech.SJTA.serviceImpl;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csmtech.SJTA.dto.ApprovalActionResultDTO;
import com.csmtech.SJTA.dto.ApprovalActionUpdateDTO;
import com.csmtech.SJTA.dto.LandApplicantDetailsUPDTO;
import com.csmtech.SJTA.repository.ApprovalActionRepository;
import com.csmtech.SJTA.repository.LandApplicantDetailsApprovalStageRepository;
import com.csmtech.SJTA.service.ApprovalActionService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ApprovalActionServiceImpl implements ApprovalActionService {

	@Autowired
	private ApprovalActionRepository approvalrepo;

	@Autowired
	private LandApplicantDetailsApprovalStageRepository repo;

	@Override
	public List<ApprovalActionResultDTO> findApprovalActionsForRoleId(Long roleId) {
		log.info(":: findApprovalActionsForRoleId Are execute sucessfully..!!");
		return approvalrepo.findApprovalActionsForRoleId(roleId);
	}

	@SuppressWarnings("unused")
	@Override
	public Short updateApprovalProcss(ApprovalActionUpdateDTO approvalDto) {
System.out.println(approvalDto.toString());
		if (approvalDto.getNewApprovalActionId() == 1) {
			log.info(":: updateApprovalProcss Start execution-1 ..!!");

			// just use Forward To Next Authority
			Short result = approvalrepo.findApprovalLevelForRoleIdAndType(approvalDto.getRoleId(),
					"Land Application");
			Integer resulofpendingat = null;
			if (result != null) {
				Short resultpending = result;
				//BigInteger finalres = resultpending.add(BigInteger.ONE);
				Integer finalres = resultpending+1;
				resulofpendingat = approvalrepo.findRoleIdByApprovalLevel(finalres);
			}

			Short newApplicationStatusId = approvalrepo
					.findApplicationStatusId(approvalDto.getNewApprovalActionId(), approvalDto.getRoleId());
			if (newApplicationStatusId == null) {
				return null;
			}
			Integer updateRecord = approvalrepo.updateLandApplicationApproval(approvalDto.getLandApplicantId(),
					resulofpendingat, newApplicationStatusId, approvalDto.getNewUpdatedBy(), true,
					approvalDto.getNewApprovalActionId(), approvalDto.getNewRemark(), result);
			log.info(":: updateApprovalProcss end  execution-1 ..!!");
			return newApplicationStatusId;

		} else if (approvalDto.getNewApprovalActionId() == 2) {
			log.info(":: updateApprovalProcss Start execution-2 ..!!");

			// just use rejected
			Short result = approvalrepo.findApprovalLevelForRoleIdAndType(approvalDto.getRoleId(),
					"Land Application");
			Integer resulofpendingat = null;
			Integer pendingAt = 0;
			Integer finalres=null;
			if (result != null ) {
				Short resultpending = result;
				// finalres = resultpending.add(BigInteger.ONE);
				
				 finalres = resultpending+1;
				
			}
			if(resulofpendingat!=null) {
				resulofpendingat = approvalrepo.findRoleIdByApprovalLevel(finalres);
			}
			Short newApplicationStatusId = approvalrepo
					.findApplicationStatusId(approvalDto.getNewApprovalActionId(), approvalDto.getRoleId());
			if (newApplicationStatusId == null) {
				return null;
			}
			Integer updateRecord = approvalrepo.updateLandApplicationApproval(approvalDto.getLandApplicantId(),
					pendingAt, newApplicationStatusId, approvalDto.getNewUpdatedBy(), true,
					approvalDto.getNewApprovalActionId(), approvalDto.getNewRemark(), result);
			log.info(":: updateApprovalProcss end  execution-2 ..!!");
			return newApplicationStatusId;
		} else if (approvalDto.getNewApprovalActionId() == 3) {
			log.info(":: updateApprovalProcss Start execution-3 ..!!");

			// just use Forward To Revert To Citizen
			Short result = approvalrepo.findApprovalLevelForRoleIdAndType(approvalDto.getRoleId(),
					"Land Application");
			Integer resulofpendingat = null;
//			Integer pendingAt = BigInteger.TWO;
			Integer pendingAt = 2;
			if (result != null) {
				Short resultpending = result;
				//BigInteger finalres = resultpending.add(BigInteger.ONE);
				Integer finalres = resultpending+1;
				resulofpendingat = approvalrepo.findRoleIdByApprovalLevel(finalres);
			}
			Short newApplicationStatusId = approvalrepo
					.findApplicationStatusId(approvalDto.getNewApprovalActionId(), approvalDto.getRoleId());
			if (newApplicationStatusId == null) {
				return null;
			}
			Integer updateRecord = approvalrepo.updateLandApplicationApproval(approvalDto.getLandApplicantId(),
					pendingAt, newApplicationStatusId, approvalDto.getNewUpdatedBy(), true,
					approvalDto.getNewApprovalActionId(), approvalDto.getNewRemark(), result);
			System.out.println(updateRecord);
			log.info(":: updateApprovalProcss end  execution-3 ..!!");
			return newApplicationStatusId;
		} else if (approvalDto.getNewApprovalActionId() == 4) {
			log.info(":: updateApprovalProcss Start execution-4 ..!!");

//			// just use Forward To Revert To Citizen
//						BigInteger result = approvalrepo.findApprovalLevelForRoleIdAndType(approvalDto.getRoleId(),
//								"Land Application");
//						BigInteger resulofpendingat = null;
//						BigInteger pendingAt=BigInteger.TWO;
//						if (result != null) {
//							BigInteger resultpending = result;
//							BigInteger finalres = resultpending.add(BigInteger.ONE);
//							resulofpendingat = approvalrepo.findRoleIdByApprovalLevel(finalres);
//						}
//
//						System.out.println(result);
//						System.out.println("BB " + resulofpendingat);
//
//						BigInteger newApplicationStatusId = approvalrepo
//								.findApplicationStatusId(approvalDto.getNewApprovalActionId(), approvalDto.getRoleId());
//						if (newApplicationStatusId == null) {
//							return null;
//						}
//						Integer updateRecord = approvalrepo.updateLandApplicationApproval(approvalDto.getLandApplicantId(),
//								pendingAt, 
//								newApplicationStatusId, 
//								approvalDto.getNewUpdatedBy(),
//								true,
//								approvalDto.getNewApprovalActionId(),
//								approvalDto.getNewRemark(),
//								result);
//						System.out.println(updateRecord);
			log.info(":: updateApprovalProcss end  execution-4 ..!!");
			// return newApplicationStatusId;
			return null;
		} else if (approvalDto.getNewApprovalActionId() == 5) {
			log.info(":: updateApprovalProcss Start execution-5 ..!!");

			// just use Forward To Revert To Citizen
			Short result = approvalrepo.findApprovalLevelForRoleIdAndType(approvalDto.getRoleId(),
					"Land Application");
			Integer resulofpendingat = null;
			Short approvalLevleMin = (short) (result-1);
			if (result != null) {
				Short resultpending = result;
				//BigInteger finalres = resultpending.subtract(BigInteger.ONE);
				Integer finalres = resultpending-1;
				resulofpendingat = approvalrepo.findRoleIdByApprovalLevel(finalres);
				//approvalLevleMin = result.subtract(BigInteger.ONE);
				//approvalLevleMin = result-1;
			}
			Short newApplicationStatusId = approvalrepo
					.findApplicationStatusId(approvalDto.getNewApprovalActionId(), approvalDto.getRoleId());
			if (newApplicationStatusId == null && approvalLevleMin == null) {
				return null;
			}
			if (resulofpendingat == null && approvalLevleMin == null) {
//				resulofpendingat = BigInteger.ONE;
//				approvalLevleMin = BigInteger.ONE;
				resulofpendingat = 1;
				approvalLevleMin = 1;
			}
			Integer updateRecord = approvalrepo.updateLandApplicationApproval(approvalDto.getLandApplicantId(),
					resulofpendingat, newApplicationStatusId, approvalDto.getNewUpdatedBy(), true,
					approvalDto.getNewApprovalActionId(), approvalDto.getNewRemark(), approvalLevleMin);
			log.info(":: updateApprovalProcss end  execution-5 ..!!");
			return newApplicationStatusId;
		} else if (approvalDto.getNewApprovalActionId() == 6) {
			log.info(":: updateApprovalProcss Start execution-6 ..!!");

			Short result = approvalrepo.findApprovalLevelForRoleIdAndType(approvalDto.getRoleId(),
					"Land Application");
			//Integer resulofpendingat = BigInteger.ZERO;
			Integer resulofpendingat = 0;
			if (result != null) {
				Short resultpending = result;
				//BigInteger finalres = resultpending.add(BigInteger.ONE);
				Integer finalres = resultpending+1;
			}
			Short newApplicationStatusId = approvalrepo
					.findApplicationStatusId(approvalDto.getNewApprovalActionId(), approvalDto.getRoleId());
			if (newApplicationStatusId == null) {
				return null;
			}
			Integer updateRecord = approvalrepo.updateLandApplicationApproval(approvalDto.getLandApplicantId(),
					resulofpendingat, newApplicationStatusId, approvalDto.getNewUpdatedBy(), true,
					approvalDto.getNewApprovalActionId(), approvalDto.getNewRemark(), result);
			log.info(":: updateApprovalProcss end  execution- ..!!");
			return newApplicationStatusId;
		}
		return null;

	}
	
	
	//get the user mssage result
	public String messageData(Integer approvalActionId ) {
		if(approvalActionId==1) {
			return " Forward to next authority sucessfully";
		}
		else if(approvalActionId==2) {
			return " Application Reject sucessfully";
		}	
		else if(approvalActionId==3) {
			return "Application revert to citizen sucessfully";
		}
		else if(approvalActionId==4) {
			return "Application Query To Citizen sucessfully";
		}
		else if(approvalActionId==5) {
			return "Application Revert To Previous Authority sucessfully";
		}
		else if(approvalActionId==6) {
			return "Application Approve sucessfully";
		}
		else {
			return "";
		}
	}

}
