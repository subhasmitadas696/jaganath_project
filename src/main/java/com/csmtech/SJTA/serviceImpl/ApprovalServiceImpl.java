package com.csmtech.SJTA.serviceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csmtech.SJTA.dto.ApprovalDTO;
import com.csmtech.SJTA.entity.ApprovalActionEntity;
import com.csmtech.SJTA.entity.ApprovalConfigurationEntity;
import com.csmtech.SJTA.entity.RoleEntity;
import com.csmtech.SJTA.repository.ApprovalActionsRepository;
import com.csmtech.SJTA.repository.ApprovalConfigurationRepository;
import com.csmtech.SJTA.repository.RolesRepository;
import com.csmtech.SJTA.service.ApprovalService;

@Service
public class ApprovalServiceImpl implements ApprovalService {

	@Autowired
	private ApprovalConfigurationRepository configRepository;

	@Autowired
	private RolesRepository rolesRepository;

	@Autowired
	private ApprovalActionsRepository actionsRepository;

	@Override
	public List<ApprovalDTO> getApprovalData() {
		List<ApprovalConfigurationEntity> configList = configRepository.findAll();
//		System.out.println(configList + "***");
		List<ApprovalDTO> result = new ArrayList<>();

		for (ApprovalConfigurationEntity config : configList) {
			List<Short> actionIds = Arrays.stream(config.getApprovalActionIds().split(",")).map(Short::valueOf)
					.collect(Collectors.toList());
//			System.out.println(actionIds + "#############");
			List<ApprovalActionEntity> actions = actionsRepository.findAllById(actionIds);
//			System.out.println(actions + "**************");

			for (ApprovalActionEntity action : actions) {
				ApprovalDTO dto = new ApprovalDTO();
				dto.setApprovalType(config.getApprovalType());
//				System.out.println(config.getRoleId() + "********");
				dto.setRoleName(rolesRepository.findById(config.getRoleId()).orElse(new RoleEntity()).getRoleName());
				dto.setApprovalAction(action.getApprovalAction());
				result.add(dto);
			}
		}

		return result;
	}
}
