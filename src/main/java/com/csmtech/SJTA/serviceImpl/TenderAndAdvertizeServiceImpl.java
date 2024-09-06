package com.csmtech.SJTA.serviceImpl;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.csmtech.SJTA.dto.TenderAdvertisementDTO;
import com.csmtech.SJTA.entity.DistrictMaster;
import com.csmtech.SJTA.entity.KhatianInformation;
import com.csmtech.SJTA.entity.PlotInformation;
import com.csmtech.SJTA.entity.TahasilMaster;
import com.csmtech.SJTA.entity.TenderAndAdvertizeEntity;
import com.csmtech.SJTA.entity.TenderType;
import com.csmtech.SJTA.entity.VillageMaster;
import com.csmtech.SJTA.repository.DistrictMasterRepository;
import com.csmtech.SJTA.repository.KhatianInformationRepository;
import com.csmtech.SJTA.repository.PlotInformationRepository;
import com.csmtech.SJTA.repository.TahasilMasterRepository;
import com.csmtech.SJTA.repository.TenderAndAdvertizeClassRepository;
import com.csmtech.SJTA.repository.TenderAndAdvertizeRepository;
import com.csmtech.SJTA.repository.TenderTypeRepository;
import com.csmtech.SJTA.repository.VillageMasterRepository;
import com.csmtech.SJTA.service.TenderAndAdvertizeService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TenderAndAdvertizeServiceImpl implements TenderAndAdvertizeService {

	@Autowired
	private TenderAndAdvertizeClassRepository repo;

	@Autowired
	private TenderAndAdvertizeRepository tenderRepo;

	@Autowired
	private TenderTypeRepository tenderTypeRepo;

	@Autowired
	private DistrictMasterRepository districtRepo;

	@Autowired
	private TahasilMasterRepository tehsilRepo;

	@Autowired
	private VillageMasterRepository villageRepo;

	@Autowired
	private KhatianInformationRepository khatianRepo;

	@Autowired
	private PlotInformationRepository plotRepo;

	@Value("${file.path}")
	private String filePathloc;

	BigInteger parentId;

	@Override
	public List<TenderAdvertisementDTO> getAllTender(String title) {
		return repo.findAllByTitle(title);
	}

	@Override
	public TenderAndAdvertizeEntity findByTenderAdvertisementId(BigInteger tenderAdvertisementId) {
		return repo.findByTenderAdvertisementId(tenderAdvertisementId);
	}

	@Override
	public TenderAndAdvertizeEntity updateTender(TenderAndAdvertizeEntity tender) {
		return tenderRepo.save(tender);
	}

	@Override
	public JSONObject saveRecord(String data) {

		Date currentDateTime = new Date();
		JSONObject json = new JSONObject();
		try {
			ObjectMapper om = new ObjectMapper();
			TenderAndAdvertizeEntity tenderData = om.readValue(data, TenderAndAdvertizeEntity.class);
			List<String> fileUploadList = new ArrayList<String>();
			fileUploadList.add(tenderData.getFileUploadTenderDocument());
			if (!Objects.isNull(tenderData.getTenderAdvertisementId())) {
				TenderAndAdvertizeEntity getEntity = tenderRepo
						.findByTenderAdvertisementIdAndStatus(tenderData.getTenderAdvertisementId(), false);
				getEntity.setSelTenderType(tenderData.getSelTenderType());
				getEntity.setTxtTitle(tenderData.getTxtTitle());
				getEntity.setLetterNo(tenderData.getLetterNo());
				getEntity.setTxtStartDate(tenderData.getTxtStartDate());
				getEntity.setTxtCloseDate(tenderData.getTxtCloseDate());
				getEntity.setFileUploadTenderDocument(tenderData.getFileUploadTenderDocument());
				getEntity.setSelDistrict(tenderData.getSelDistrict());
				getEntity.setSelTehsil(tenderData.getSelTehsil());
				getEntity.setSelMouza(tenderData.getSelMouza());
				getEntity.setSelKhataNo(tenderData.getSelKhataNo());
				getEntity.setSelPlotNo(tenderData.getSelPlotNo());

				getEntity.setUpdatedBy(tenderData.getCreatedBy());
				getEntity.setUpdatedOn(currentDateTime);
				TenderAndAdvertizeEntity updateData = tenderRepo.save(getEntity);
				parentId = updateData.getTenderAdvertisementId();
				json.put("status", 202);
			} else {
				tenderData.setCreatedBy(tenderData.getCreatedBy());
				TenderAndAdvertizeEntity saveData = tenderRepo.save(tenderData);
				parentId = saveData.getTenderAdvertisementId();
				json.put("status", 200);
			}
			json.put("app_id", parentId);
			for (String fileUpload : fileUploadList) {
				if (!fileUpload.equals("")) {
					File f = new File("src/storage/tempfile/" + fileUpload);
					if (f.exists()) {
						File src = new File("src/storage/tempfile/" + fileUpload);
						File dest = new File("D:/testfile/" + fileUpload);
						try {
							Files.copy(src.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
							Files.delete(src.toPath());
						} catch (IOException e) {
							log.info("Iniside Error");
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.put("status", 400);
		}
		return json;

	}

	@Override
	public List<TenderType> getAllTenderType() {
		return tenderTypeRepo.findAll();
	}

	@Override
	public Integer deleteTender(Integer createdBy, BigInteger tenderAdvertisementId) {
		return repo.deleteRecord(createdBy, tenderAdvertisementId);
	}

	@Override
	public List<DistrictMaster> getAllDistrict() {
		return districtRepo.findAll();
	}

	@Override
	public List<TahasilMaster> getAllTehsil(String districtCode) {
		return tehsilRepo.findAllByDistrictCode(districtCode);
	}

	@Override
	public List<VillageMaster> getAllVillage(String tahasilCode) {
		return villageRepo.findAllByTahasilCode(tahasilCode);
	}

	@Override
	public List<KhatianInformation> getAllKhatian(String villageCode) {
		return khatianRepo.findAllByVillageCode(villageCode);
	}

	@Override
	public List<PlotInformation> getAllPlot(String khatianCode) {
		return plotRepo.findAllByKhatianCode(khatianCode);
	}


	@Override
	public List<TenderAdvertisementDTO> viewAllTender(String title,Date startDate) {
		return repo.findAllByTitleStatusFalse(title,startDate);
	}

}
