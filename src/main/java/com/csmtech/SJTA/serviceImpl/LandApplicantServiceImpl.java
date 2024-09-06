package com.csmtech.SJTA.serviceImpl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.csmtech.SJTA.dto.ApplicantNumberAndMobileDTO;
import com.csmtech.SJTA.dto.DocumentDTO;
import com.csmtech.SJTA.dto.LandAppResponseStructureDTO;
import com.csmtech.SJTA.dto.LandApplicantDTO;
import com.csmtech.SJTA.dto.LandPaginationDTO;
import com.csmtech.SJTA.dto.PlotDTO;
import com.csmtech.SJTA.entity.LandApplicant;
import com.csmtech.SJTA.entity.LandDocuments;
import com.csmtech.SJTA.entity.LandPlot;
import com.csmtech.SJTA.entity.Land_applicant;
import com.csmtech.SJTA.entity.Land_documents;
import com.csmtech.SJTA.entity.Land_plot;
import com.csmtech.SJTA.repository.LandApplicantDetailsApprovalStageRepository;
import com.csmtech.SJTA.repository.LandApplicantNativeRepository;
import com.csmtech.SJTA.repository.LandApplicantRepo;
import com.csmtech.SJTA.repository.LandApplicantRepository;
import com.csmtech.SJTA.repository.LandDocumentsRepository;
import com.csmtech.SJTA.repository.LandPlotDetailsRepository;
import com.csmtech.SJTA.repository.LandPlotRepository;
import com.csmtech.SJTA.repository.LnadApplicantViewAllDetailsClassRepository;
import com.csmtech.SJTA.service.LandApplicantService;
import com.csmtech.SJTA.util.CommonApplicationNumberGenerator;

import lombok.extern.slf4j.Slf4j;

/*
 * @Auth  GuruPrasad
 */

@Service
@Slf4j
public class LandApplicantServiceImpl implements LandApplicantService {

	@Autowired
	private LandApplicantRepository landApplicantRepository;

	@Autowired
	private LandApplicantRepo landApplicantRepo;

	@Autowired
	private LandApplicantNativeRepository landNativeRepo;

	@Autowired
	private LandDocumentsRepository documentsRepository;

	@Autowired
	private LandPlotRepository plotRepository;

	@Autowired
	private LandPlotDetailsRepository nativerepo;

	@Autowired
	private LnadApplicantViewAllDetailsClassRepository repo;

	@Autowired
	private LandApplicantDetailsApprovalStageRepository landapprepo;

	
	
//	public Integer getTotalApplicantCount() {
//		return landNativeRepo.getTotalApplicantCount();
//	}

//	@Override
//	public List<LandApplicantDTO> getLandApplicantDetailsPage(BigInteger roleId, Integer pageNumber, Integer pageSize,
//			String pageType) {
//		log.info("land all service started..");
//		// they will new action
//		if (pageType.equals("New")) {
//			return landNativeRepo.getLandApplicantDetailsPage(roleId, pageNumber, pageSize);
//		}
//
//		else if (pageType.equals("Under Process")) {
//			return landapprepo.getLandApplicantsWithDetails();
//		}
//
//		else if (pageType.equals("Revert to Citizen")) {
//			return landapprepo.getLandApplicantsRevertCitizenDetails(3);
//		}
//
//		else if (pageType.equals("Approved")) {
//			return landapprepo.getLandApplicantsWithApproveDetails(6);
//		}
//
//		else if (pageType.equals("Reject")) {
//			return landapprepo.getLandApplicantsWithRejectDetails(2);
//		}
//		return null;
//
//	}

	@Override
	public LandPaginationDTO getSearchLandApplicantDetailsPage(BigInteger roleId, String applicantName,
			Integer pageNumber, Integer pageSize, String applicantUniqueId, String plotKathaNO, String pageType) {
		List<LandApplicantDTO> respones = null;
		BigInteger count=null;	
		
		if (pageType.equals("New")) {
			log.info(
					":: getSearchLandApplicantDetailsPage() execute and return getSearchLandApplicantDetailsPage() method data");
			if (applicantName != null || plotKathaNO != null || applicantUniqueId != null) {
				count=landNativeRepo.getTotalApplicantCountPagination(roleId, applicantName, applicantUniqueId, plotKathaNO);
				respones=landNativeRepo.getSearchLandApplicantDetailsPage(roleId, applicantName, pageNumber, pageSize,
						applicantUniqueId, plotKathaNO);
				return  new LandPaginationDTO(respones, count);
			} else
				log.info(
						":: getSearchLandApplicantDetailsPage() execute and return getLandApplicantDetailsPage() method data");
			 count= landNativeRepo.getTotalApplicantCount(roleId, applicantName, applicantUniqueId, plotKathaNO);
			respones = landNativeRepo.getLandApplicantDetailsPage(roleId, pageNumber, pageSize);
			return  new LandPaginationDTO(respones, count);
		} 
		
		else if (pageType.equals("Under Process")) {
			if (applicantName != null || plotKathaNO != null || applicantUniqueId != null) {
				log.info(":: getSearchLandApplicantDetailsPage() execute and return getLandApplicantsWithDetailsSearchFunction() method data");
				respones= landapprepo.getLandApplicantsWithDetailsSearchFunction(applicantName, pageNumber, pageSize,applicantUniqueId, plotKathaNO);
				count=landapprepo.getUnerProcessUseLike(applicantName, applicantUniqueId, plotKathaNO);
				return  new LandPaginationDTO(respones, count);
			} else {
				log.info(
						":: getSearchLandApplicantDetailsPage() execute and return getLandApplicantsWithDetails() method data");
				respones= landapprepo.getLandApplicantsWithDetails(pageNumber, pageSize);
				 count=landapprepo.getUnerProcess();
				 return  new LandPaginationDTO(respones, count);
			}				
		} 
		
		else if (pageType.equals("Revert to Citizen")) {
			if (applicantName != null || plotKathaNO != null || applicantUniqueId != null) {
				log.info(":: getSearchLandApplicantDetailsPage() execute and return getLandApplicantsRevertCitizenDetailsSearchFunction() method data");
				
				respones=landapprepo.getLandApplicantsRevertCitizenDetailsSearchFunction(3, applicantName, pageNumber,pageSize, applicantUniqueId, plotKathaNO);
				 count=landapprepo.getRevertToCitizenUseLike(3, applicantName, applicantUniqueId, plotKathaNO);
				 return  new LandPaginationDTO(respones, count);
			} else {
				log.info(":: getSearchLandApplicantDetailsPage() execute and return getLandApplicantsRevertCitizenDetails() method data");
				
				respones=landapprepo.getLandApplicantsRevertCitizenDetails(3,pageNumber, pageSize);
				count=landapprepo.getRevertToCitizen(3);
				return  new LandPaginationDTO(respones, count);
			}
		}

		//hear do
		else if (pageType.equals("Approved")) {
			if (applicantName != null || plotKathaNO != null || applicantUniqueId != null) {
				log.info(":: getSearchLandApplicantDetailsPage() execute and return getLandApplicantsWithApproveDetailsSerchFunction() method data");
		       respones=landapprepo.getLandApplicantsWithApproveDetailsSerchFunction(6, applicantName, pageNumber,pageSize, applicantUniqueId, plotKathaNO);
		       count=landapprepo.getApproveUseLike(6, applicantName, applicantUniqueId, plotKathaNO);
		       return  new LandPaginationDTO(respones, count);
		        
			} else {
				log.info(":: getSearchLandApplicantDetailsPage() execute and return getLandApplicantsWithApproveDetails() method data");
				respones=landapprepo.getLandApplicantsWithApproveDetails(6,pageNumber,pageSize);
				count=landapprepo.getApprove(6);
				return  new LandPaginationDTO(respones, count);
			}
		} 
		
		
		else if (pageType.equals("Reject")) {
			if (applicantName != null || plotKathaNO != null || applicantUniqueId != null) {
				log.info(":: getSearchLandApplicantDetailsPage() execute and return getLandApplicantsWithRejectDetailsSearchFunction() method data");
				respones=landapprepo.getLandApplicantsWithRejectDetailsSearchFunction(2, applicantName, pageNumber,pageSize, applicantUniqueId, plotKathaNO);
				count=landapprepo.getRejectUseLike(2, applicantName, applicantUniqueId, plotKathaNO);
				return  new LandPaginationDTO(respones, count);		
			} else {
				log.info(":: getSearchLandApplicantDetailsPage() execute and return getLandApplicantsWithRejectDetails() method data");
				respones=landapprepo.getLandApplicantsWithRejectDetails(2,pageNumber,pageSize);
				count=landapprepo.getReject(6);
				return  new LandPaginationDTO(respones, count);
			}
		}
		
		
		
		return  new LandPaginationDTO(respones, count);

//		log.info("land search service started..");
//		return landNativeRepo.getSearchLandApplicantDetailsPage(roleId, applicantName, pageNumber, pageSize, applicantUniqueId,plotKathaNO);
	}

	@Override
	public LandAppResponseStructureDTO findAllDetailsBylandApplicantId(BigInteger landApplicantId) {

		return landNativeRepo.getCombinedDataForApplicant(landApplicantId);

//		Land_applicant applicant = landApplicantRepository.getOne(intId);
		/*
		 * LandApplicant applicants = landApplicantRepo.getDetails(landApplicantId);
		 * System.out.println(applicants + "*******"); log.info("document started...");
		 * List<LandDocuments> documents =
		 * documentsRepository.findByLandApplicantId(landApplicantId);
		 * log.info("documents ended..."); System.out.println(documents + "********");
		 * 
		 * List<LandPlot> plots = plotRepository.findByLandApplicantId(landApplicantId);
		 * System.out.println(plots + "*********");
		 * 
		 * LandApplicantDTO landApplicantDTO = new LandApplicantDTO();
		 * 
		 * 
		 * landApplicantDTO.setApplicantNo(applicant.getApplicantNo());
		 * landApplicantDTO.setApplicantName(applicant.getTxtApplicantName());
		 * landApplicantDTO.setFatherName(applicant.getTxtFatherHusbandName());
		 * landApplicantDTO.setMobileNo(applicant.getTxtMobileNo());
		 * landApplicantDTO.setEmailId(applicant.getTxtEmail());
		 * landApplicantDTO.setDocRefNo(applicant.getTxtDocumentNo());
		 * landApplicantDTO.setDocsPath(applicant.getFileUploadDocument());
		 * 
		 * 
		 * landApplicantDTO.setApplicantNo(applicants.getApplicantNo());
		 * landApplicantDTO.setApplicantName(applicants.getTxtApplicantName());
		 * landApplicantDTO.setFatherName(applicants.getTxtFatherHusbandName());
		 * landApplicantDTO.setMobileNo(applicants.getTxtMobileNo());
		 * landApplicantDTO.setEmailId(applicants.getTxtEmail());
		 * landApplicantDTO.setDocRefNo(applicants.getTxtDocumentNo());
		 * landApplicantDTO.setDocsPath(applicants.getFileUploadDocument());
		 * landApplicantDTO.setBlockName(applicants.getSelBlockULBVal());
		 * landApplicantDTO.setGpName(applicants.getSelGPWardNoVal());
		 * landApplicantDTO.setStateName(applicants.getSelStateVal());
		 * landApplicantDTO.setDistrictName(applicants.getSelDistrict18Val());
		 * landApplicantDTO.setKhataNo(applicants.getKhataNo());
		 * landApplicantDTO.setMouzaName(applicants.getMouzaName());
		 * landApplicantDTO.setTehsilName(applicants.getTehsilName()); //
		 * landApplicantDTO.setPlotName(applicants.get)
		 * landApplicantDTO.setCurrPoliceStation(applicants.getTxtPoliceStation());
		 * landApplicantDTO.setPrePoliceStation(applicants.getTxtPoliceStation22());
		 * 
		 * landApplicantDTO.setCurrPostOffice(applicants.getTxtPostOffice());
		 * landApplicantDTO.setPrePostOffice(applicants.getTxtPostOffice23());
		 * 
		 * landApplicantDTO.setCurrStreetNo(applicants.getTxtHabitationStreetNoLandmark(
		 * ));
		 * landApplicantDTO.setPreStreetNo(applicants.getTxtHabitationStreetNoLandmark24
		 * ());
		 * 
		 * landApplicantDTO.setCurrHouseNo(applicants.getTxtHouseNo());
		 * landApplicantDTO.setPreHouseNo(applicants.getTxtHouseNo25());
		 * 
		 * landApplicantDTO.setCurrPinCode(applicants.getTxtPinCode());
		 * landApplicantDTO.setPrePinCode(applicants.getTxtPinCode26());
		 * 
		 * List<DocumentDTO> documentDTOs = new ArrayList<>(); for (LandDocuments
		 * document : documents) { DocumentDTO documentDTO = new DocumentDTO(); //
		 * documentDTO.setDocName(document.getAmtxtDocumentName());
		 * documentDTO.setDocPath(document.getAmfileDocument());
		 * documentDTOs.add(documentDTO); }
		 * 
		 * landApplicantDTO.setDocuments(documentDTOs); List<PlotDTO> plotDTOs = new
		 * ArrayList<>(); for (LandPlot plot : plots) { PlotDTO plotDTO = new PlotDTO();
		 * plotDTO.setTotalArea(plot.getTxtTotalRakba());
		 * plotDTO.setPurchaseArea(plot.getTxtPurchaseRakba()); //
		 * plotDTO.setVarietiesName(plot.getVaritiesName()); plotDTOs.add(plotDTO); }
		 * 
		 * landApplicantDTO.setPlots(plotDTOs);
		 * 
		 * return landApplicantDTO;
		 */
	}

	@Override
	public Integer updateApplicantName(BigInteger applicantId) {
		String applicantName = CommonApplicationNumberGenerator.generateApplicantUniqueNumber("LA");
		return nativerepo.updateApplicantName(applicantId, applicantName);

	}

	// hear the method are return the applicant number for land registraitation
	private static final String DATA_FILE_PATH = "LAcounter_data.txt";
	private static Integer coutTabRecord1;
	private static Date lastGeneratedDate;

	static {
		readCounterDataFromFile();
	}

	public static synchronized String generateApplicantUniqueNumber(String appName) {
		String dateFormat = "ddMMyy";

		Date currentDate = new Date();
		if (!isSameDate(currentDate, lastGeneratedDate)) {
			lastGeneratedDate = currentDate;
			coutTabRecord1 = 1;
		}

		String formattedDate = new SimpleDateFormat(dateFormat).format(currentDate);
		String formattedCounter = String.format("%03d", getNextCounterValue());
		saveCounterDataToFile();
		return appName + formattedDate + formattedCounter;
	}

	private static boolean isSameDate(Date date1, Date date2) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		return dateFormat.format(date1).equals(dateFormat.format(date2));
	}

	private static int getNextCounterValue() {
		return coutTabRecord1++;
	}

	private static void readCounterDataFromFile() {
		try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE_PATH))) {
			lastGeneratedDate = new SimpleDateFormat("yyyyMMdd").parse(reader.readLine());
			coutTabRecord1 = Integer.parseInt(reader.readLine());
		} catch (Exception e) {
			lastGeneratedDate = new Date();
			coutTabRecord1 = 1;
		}
	}

	private static void saveCounterDataToFile() {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FILE_PATH))) {
			writer.write(new SimpleDateFormat("yyyyMMdd").format(lastGeneratedDate));
			writer.newLine();
			writer.write(coutTabRecord1.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<ApplicantNumberAndMobileDTO> fetchApplicantInfoById(BigInteger i) {
		return nativerepo.fetchApplicantInfoById(i);
	}

}
