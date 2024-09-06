package com.csmtech.SJTA.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csmtech.SJTA.dto.DistrictCode;
import com.csmtech.SJTA.repository.BannerDataRepository;
import com.csmtech.SJTA.service.BannerDataService;

@Service
public class BannerDataServiceImpl implements BannerDataService {

	@Autowired
	private BannerDataRepository bannerDataRepository;

	@Override
	public List<DistrictCode> getDistrictCodes() {
		List<Object[]> resultList = bannerDataRepository.getDistrictCodes();
		List<DistrictCode> bannerData = new ArrayList<>();
		for (Object[] result : resultList) {
			String districtCode = (String) result[0];
			String plotNumber = (String) result[1];
			String mouza = (String) result[2];
			String tahasil =(String) result[3];

			DistrictCode bannerdataInfo = new DistrictCode();
			bannerdataInfo.setDistrictCode(districtCode);
			bannerdataInfo.setPlot(plotNumber);
			bannerdataInfo.setMouza(mouza);
			bannerdataInfo.setTahasil(tahasil);
			bannerData.add(bannerdataInfo);
		}

		return bannerData;

	}

}
