package com.csmtech.SJTA.service;

import java.util.List;

import com.csmtech.SJTA.dto.ModuleMenuDataDTO;

public interface MenuBindingService {

	public List<ModuleMenuDataDTO> getModuleAndMenuByUserId(long userId);

}
