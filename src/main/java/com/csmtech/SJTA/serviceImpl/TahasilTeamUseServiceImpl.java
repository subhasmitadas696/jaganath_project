package com.csmtech.SJTA.serviceImpl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.csmtech.SJTA.dto.DistrictDTO;
import com.csmtech.SJTA.dto.KhatianPlotDTO;
import com.csmtech.SJTA.dto.TahasilDTO;
import com.csmtech.SJTA.dto.TahasilTeamUseRequestDto;
import com.csmtech.SJTA.dto.VillageInfoDTO;
import com.csmtech.SJTA.repository.TahasilTeamUseQueryClassRepository;
import com.csmtech.SJTA.service.TahasilTeamUseService;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class TahasilTeamUseServiceImpl implements TahasilTeamUseService {

	@Value("${sjta.bcryptpassword.secretKey}")
	private String SECRET_KEY;

	@Autowired
	private TahasilTeamUseQueryClassRepository repo;

	@Override
	public List<TahasilDTO> getTahasilData(String distId) {
		List<Object[]> results = repo.getTahasilsWithStatistics(distId);
		return results.stream().map(result -> {
			TahasilDTO dto = new TahasilDTO();
			dto.setTahasilCode((String) result[0]);
			dto.setTahasilName((String) result[1]);
			dto.setTotalMouza((BigInteger) result[2]);
			dto.setTotalKatha((BigInteger) result[3]);
			dto.setTotalPlot((BigInteger) result[4]);
			dto.setTotalArea((BigDecimal) result[5]);
			return dto;
		}).collect(Collectors.toList());

	}

	// login
	@Override
	public List<TahasilTeamUseRequestDto> checkLogin(String tahasilCode) {

		List<TahasilTeamUseRequestDto> count = repo.getTahasilLoginDetails(tahasilCode);
		return count;
	}

	@Override
	public List<VillageInfoDTO> getKathaRecord(String tahasilcode) {
		List<Object[]> results = repo.getVillageInfoForTahasil(tahasilcode);
		return results.stream().map(result -> {
			VillageInfoDTO dto = new VillageInfoDTO();
			dto.setVillageCode((String) result[0]);
			dto.setVillageName((String) result[1]);
			dto.setTotalKatha((BigInteger) result[2]);
			dto.setTotalPlot((BigInteger) result[3]);
			dto.setTotalArea((BigDecimal) result[4]);
			dto.setExtent((String) result[5]);
			log.info("Sucess..Execute and return the result sucess -002");
			return dto;
		}).collect(Collectors.toList());

	}

	@Override
	public List<KhatianPlotDTO> getKathaRecordMore(String villageCode) {

		List<Object[]> results = repo.getKhatianPlotsForVillage(villageCode);
		List<KhatianPlotDTO> districtDTOs = results.stream().map(result -> {
			KhatianPlotDTO dto = new KhatianPlotDTO();
			dto.setKhatianCode((String) result[0]);
			dto.setKhataNo((String) result[1]);
			dto.setPlotNo((String) result[2]);
			dto.setAreaAcre((BigDecimal) result[3]);
			return dto;
		}).collect(Collectors.toList());
		log.info("Sucess..Execute and return the result sucess -002");
		return districtDTOs;
	}

	@Override
	public List<DistrictDTO> getDistricts() {
		log.info("Sucess..Execute and return the result");
		List<Object[]> results = repo.getDistricts();
		List<DistrictDTO> districtDTOs = results.stream().map(result -> {
			DistrictDTO dto = new DistrictDTO();
			dto.setDistrictCode((String) result[0]);
			dto.setDistrictName((String) result[1]);
			return dto;
		}).collect(Collectors.toList());
		log.info("Sucess..Execute and return the result sucess -002");
		return districtDTOs;
	}
	
	
}
