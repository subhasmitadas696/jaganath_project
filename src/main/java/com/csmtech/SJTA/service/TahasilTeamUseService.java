package com.csmtech.SJTA.service;

import java.util.List;

import com.csmtech.SJTA.dto.DistrictDTO;
import com.csmtech.SJTA.dto.KhatianPlotDTO;
import com.csmtech.SJTA.dto.TahasilTeamUseRequestDto;
import com.csmtech.SJTA.dto.TahasilDTO;
import com.csmtech.SJTA.dto.VillageInfoDTO;

public interface TahasilTeamUseService {
	
	public List<DistrictDTO> getDistricts();
	
	public List<TahasilDTO> getTahasilData(String distId);
	
	public List<TahasilTeamUseRequestDto> checkLogin(String tahasilCode);

	public  List<VillageInfoDTO> getKathaRecord(String tahasilcode);
	
	public List<KhatianPlotDTO> getKathaRecordMore(String villageCode);

}
