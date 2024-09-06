package com.csmtech.SJTA.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csmtech.SJTA.dto.ModuleMenuDataDTO;
import com.csmtech.SJTA.repository.MenuBindingRepository;
import com.csmtech.SJTA.service.MenuBindingService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MenuBindingServiceImpl implements MenuBindingService {

	@Autowired
	private MenuBindingRepository menurepo;

	@Override
	public List<ModuleMenuDataDTO> getModuleAndMenuByUserId(long userId) {
		log.info(" ::  getModuleAndMenuByUserId() Execution Are Start And End !! ");
		return menurepo.getModuleAndMenuByUserId(userId);
	}

}
