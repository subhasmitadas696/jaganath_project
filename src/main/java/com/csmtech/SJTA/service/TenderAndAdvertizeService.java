package com.csmtech.SJTA.service;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;

import com.csmtech.SJTA.dto.TenderAdvertisementDTO;
import com.csmtech.SJTA.entity.DistrictMaster;
import com.csmtech.SJTA.entity.KhatianInformation;
import com.csmtech.SJTA.entity.PlotInformation;
import com.csmtech.SJTA.entity.TahasilMaster;
import com.csmtech.SJTA.entity.TenderAndAdvertizeEntity;
import com.csmtech.SJTA.entity.TenderType;
import com.csmtech.SJTA.entity.VillageMaster;

public interface TenderAndAdvertizeService {

//	public Integer saveNocDocument(TenderAndAdvertizeDTO dto);

	public List<TenderAdvertisementDTO> getAllTender(String title);

	public TenderAndAdvertizeEntity findByTenderAdvertisementId(BigInteger tenderAdvertisementId);

	public TenderAndAdvertizeEntity updateTender(TenderAndAdvertizeEntity tender);

	public JSONObject saveRecord(String tenderData);

	public List<TenderType> getAllTenderType();

	public Integer deleteTender(Integer createdBy, BigInteger tenderAdvertisementId);

	public List<DistrictMaster> getAllDistrict();

	public List<TahasilMaster> getAllTehsil(String districtCode);

	public List<VillageMaster> getAllVillage(String tahasilCode);

	public List<KhatianInformation> getAllKhatian(String villageCode);

	public List<PlotInformation> getAllPlot(String khatianCode);

	public List<TenderAdvertisementDTO> viewAllTender(String title, Date startDate);
}
