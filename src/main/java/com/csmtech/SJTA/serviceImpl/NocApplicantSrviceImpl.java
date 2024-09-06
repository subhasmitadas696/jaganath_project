package com.csmtech.SJTA.serviceImpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.csmtech.SJTA.dto.ApplicantDTO;
import com.csmtech.SJTA.dto.ApplicantPlotDTO;
import com.csmtech.SJTA.dto.FileUploadDTO;
import com.csmtech.SJTA.dto.NocApplicantRequestDTO;
import com.csmtech.SJTA.dto.savenocNocPlotDetaisRecxord;
import com.csmtech.SJTA.repository.NocapplicantClassRepository;
import com.csmtech.SJTA.service.NocapplicantSerice;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class NocApplicantSrviceImpl implements NocapplicantSerice {

	@Autowired
	private NocapplicantClassRepository repo;

	@Override
	public Integer saveNocApplicant(NocApplicantRequestDTO reqDto, String filePathcome) {

		Integer saveApplicaintRecord = null;

		String filepath = filePathcome;
		System.out.println(filepath);

		String txtApplicantName = reqDto.getTxtApplicantName();
		String txtFatherHusbandName = reqDto.getTxtFatherHusbandName();
		String txtMobileNo = reqDto.getTxtMobileNo();
		String txtEmail = reqDto.getTxtEmail();
		Integer selDocumentType = reqDto.getSelDocumentType();
		String txtDocumentNo = reqDto.getTxtDocumentNo();
		// String fileUploadDocument = reqDto.getFileUploadDocument();
		Integer selCurrState = reqDto.getSelState();
		Integer selCurrDistrict = reqDto.getSelDistrict();
		Integer selCurrBlockULB = reqDto.getSelBlockULB();
		Integer selCurrGPWardNo = reqDto.getSelGPWardNo();
		Integer selCurrVillageLocalAreaName = reqDto.getSelVillageLocalAreaName();
		String txtCurrPoliceStation = reqDto.getTxtPoliceStation();
		String txtCurrPostOffice = reqDto.getTxtPostOffice();
		String txtCurrHabitationStreetNoLandmark = reqDto.getTxtHabitationStreetNoLandmark();
		String txtCurrHouseNo = reqDto.getTxtHouseNo();
		String txtCurrPinCode = reqDto.getTxtPinCode();
		Integer selPerState = reqDto.getSelState17();
		Integer selPerDistrict = reqDto.getSelDistrict18();
		Integer selPerBlockULB = reqDto.getSelBlockULB19();
		Integer selPerGPWARDNumber = reqDto.getSelGPWARDNumber();
		Integer selPerVillageLocalAreaName = reqDto.getSelVillageLocalAreaName21();
		String txtPerPoliceStation = reqDto.getTxtPoliceStation22();
		String txtPerPostOffice = reqDto.getTxtPostOffice23();
		String txtPerHabitationStreetNoLandmark = reqDto.getTxtHabitationStreetNoLandmark24();
		String txtPerHouseNo = reqDto.getTxtHouseNo25();
		String txtPerPinCode = reqDto.getTxtPinCode26();
		Integer createdby=reqDto.getCreatedBy();

		if (reqDto.getApplicantid() == null) {
			saveApplicaintRecord = repo.saveNocApplicant(txtApplicantName, txtFatherHusbandName, txtMobileNo, txtEmail,
					selDocumentType, txtDocumentNo, filepath, selCurrState, selCurrDistrict, selCurrBlockULB,
					selCurrGPWardNo, selCurrVillageLocalAreaName, txtCurrPoliceStation, txtCurrPostOffice,
					txtCurrHabitationStreetNoLandmark, txtCurrHouseNo, txtCurrPinCode, selPerState, selPerDistrict,
					selPerBlockULB, selPerGPWARDNumber, selPerVillageLocalAreaName, txtPerPoliceStation,
					txtPerPostOffice, txtPerHabitationStreetNoLandmark, txtPerHouseNo, txtPerPinCode, createdby);
		}

		else
			saveApplicaintRecord = repo.saveNocApplicantUpdate(txtApplicantName, txtFatherHusbandName, txtMobileNo,
					txtEmail, selDocumentType, txtDocumentNo, filepath, selCurrState, selCurrDistrict, selCurrBlockULB,
					selCurrGPWardNo, selCurrVillageLocalAreaName, txtCurrPoliceStation, txtCurrPostOffice,
					txtCurrHabitationStreetNoLandmark, txtCurrHouseNo, txtCurrPinCode, selPerState, selPerDistrict,
					selPerBlockULB, selPerGPWARDNumber, selPerVillageLocalAreaName, txtPerPoliceStation,
					txtPerPostOffice, txtPerHabitationStreetNoLandmark, txtPerHouseNo, txtPerPinCode,
					reqDto.getCreatedBy(), reqDto.getApplicantid());

		return saveApplicaintRecord;

	}

	@Override
	public Integer saveNocPlot(savenocNocPlotDetaisRecxord dto) {

		Integer insertnocapp = repo.updateNocApplicantPlotDetails(dto);
		System.out.println("Working" + insertnocapp);
		Integer inertnocplot = repo.batchInsertNocPlots(dto);

		return inertnocplot;
	}

	private String saveFileToSystemDrive(MultipartFile file) {

		// Generate a unique file name to avoid collisions
		String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

		try {
			Path filePath = Paths.get("D://testfile", fileName);
			Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
			return filePath.toString();
		} catch (IOException e) {
			throw new RuntimeException("Failed to save the file.", e);
		}
	}

	@Override
	public Integer saveNocDocument(String halPattaValue, String sabikPattaValue, String sabikHalComparValue,
			String setlementYadastValue, String registeredDeedValue,String fileDocumentaryProofofOccupancyifany, BigInteger nocAppId, BigInteger createdby) {
		return repo.saveNocDocument(halPattaValue, sabikPattaValue, sabikHalComparValue, setlementYadastValue,
				registeredDeedValue,fileDocumentaryProofofOccupancyifany, nocAppId, createdby);
	}

	@Override
	public ApplicantDTO getApplicantDetailsById(BigInteger id) {
		return repo.getApplicantDetailsById(id);
	}

	@Override
	public savenocNocPlotDetaisRecxord getApplicantPlotDetailsById(BigInteger id) {
		return repo.getApplicantPlotDetailsById(id);
	}

}
