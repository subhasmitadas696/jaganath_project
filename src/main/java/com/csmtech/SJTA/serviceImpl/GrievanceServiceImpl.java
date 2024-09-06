package com.csmtech.SJTA.serviceImpl;

import java.util.List;
import org.json.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.json.JSONArray;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import java.io.File;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Objects;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.nio.file.Files;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.nio.file.StandardCopyOption;
import com.csmtech.SJTA.util.CommonUtil;
import com.csmtech.SJTA.repository.GrievanceRepository;
import com.csmtech.SJTA.entity.Grievance;
import com.csmtech.SJTA.service.GrievanceService;

@Transactional
@Service
public class GrievanceServiceImpl implements GrievanceService {

	@Autowired
	private GrievanceRepository grievanceRepository;
	@Autowired
	EntityManager entityManager;

	JSONObject selCaste = new JSONObject("{General:General,SC:SC,ST:ST,OBC:OBC,SEBC:SEBC,EBC:EBC}");
	JSONObject chkDiscloseyourdetails = new JSONObject("{1:Yes}");
	JSONObject selModeofOccupation = new JSONObject("{1:Cultivation,2:Erection of House,3:Any other Manner}");

	Integer parentId = 0;
	Object dynamicValue = null;
	private static final Logger logger = LoggerFactory.getLogger(GrievanceServiceImpl.class);
	JSONObject json = new JSONObject();
	@Value("${tempUpload.path}")
	private String tempUploadPath;
	@Value("${file.path}")
	private String finalUploadPath;

	@Override
	public JSONObject save(String data) {
		logger.info("Inside save method of GrievanceServiceImpl");
		try {
			ObjectMapper om = new ObjectMapper();
			Grievance grievance = om.readValue(data, Grievance.class);
			List<String> fileUploadList = new ArrayList<String>();
			fileUploadList.add(grievance.getFileFileUpload());
			if (!Objects.isNull(grievance.getIntId()) && grievance.getIntId() > 0) {
				Grievance getEntity = grievanceRepository.findByIntIdAndBitDeletedFlag(grievance.getIntId(), false);
				getEntity.setSelMonthofUnauthorizedOccupation(grievance.getSelMonthofUnauthorizedOccupation());
				getEntity.setTxtName(grievance.getTxtName());
				getEntity.setTxtFatherName(grievance.getTxtFatherName());
				getEntity.setSelDistrict(grievance.getSelDistrict());
				getEntity.setSelBlock(grievance.getSelBlock());
				getEntity.setSelGP(grievance.getSelGP());
				getEntity.setSelVillage(grievance.getSelVillage());
				getEntity.setTxtOtherInformation(grievance.getTxtOtherInformation());
				getEntity.setSelCaste(grievance.getSelCaste());
				getEntity.setTxtMobileNo(grievance.getTxtMobileNo());
				getEntity.setChkDiscloseyourdetails(grievance.getChkDiscloseyourdetails());
				getEntity.setSelDistrict13(grievance.getSelDistrict13());
				getEntity.setSelTahasil(grievance.getSelTahasil());
				getEntity.setSelVillage15(grievance.getSelVillage15());
				getEntity.setSelKhataNo(grievance.getSelKhataNo());
				getEntity.setSelPlotNo(grievance.getSelPlotNo());
				getEntity.setTxtTotalAreainacre(grievance.getTxtTotalAreainacre());
				getEntity.setTxtExtentOccupied(grievance.getTxtExtentOccupied());
				getEntity.setSelModeofOccupation(grievance.getSelModeofOccupation());
				getEntity.setTxtOccupationDetails(grievance.getTxtOccupationDetails());
				getEntity.setTxtLandmark(grievance.getTxtLandmark());
				getEntity.setFileFileUpload(grievance.getFileFileUpload());
				getEntity.setTxtrTextarea(grievance.getTxtrTextarea());
				Grievance updateData = grievanceRepository.save(getEntity);
				parentId = updateData.getIntId();
				json.put("status", 202);
			} else {
				Grievance saveData = grievanceRepository.save(grievance);
				parentId = saveData.getIntId();
				json.put("status", 200);
			}
			for (String fileUpload : fileUploadList) {
				if (!fileUpload.equals("")) {
					File f = new File(tempUploadPath + fileUpload);
					if (f.exists()) {
						File src = new File(tempUploadPath + fileUpload);
						File dest = new File(finalUploadPath + "grievance/" + fileUpload);
						try {
							Files.copy(src.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
							Files.delete(src.toPath());
						} catch (IOException e) {
							System.out.println("Iniside Error");
						}
					}
				}
			}
			json.put("id", parentId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Inside save method of GrievanceServiceImpl some error occur:" + e);
			json.put("status", 400);
		}
		return json;
	}

	@Override
	public JSONObject getById(Integer id) {
		logger.info("Inside getById method of GrievanceServiceImpl");
		Grievance entity = grievanceRepository.findByIntIdAndBitDeletedFlag(id, false);
		try {
			dynamicValue = CommonUtil.getDynSingleData(entityManager,
					"select month_name from m_month where month_id=" + entity.getSelMonthofUnauthorizedOccupation());
		} catch (Exception ex) {
			dynamicValue = "--";
		}
		entity.setSelMonthofUnauthorizedOccupationVal(dynamicValue.toString());
		try {
			dynamicValue = CommonUtil.getDynSingleData(entityManager,
					"select district_name from m_district where district_id=" + entity.getSelDistrict());
		} catch (Exception ex) {
			dynamicValue = "--";
		}
		entity.setSelDistrictVal(dynamicValue.toString());
		try {
			dynamicValue = CommonUtil.getDynSingleData(entityManager,
					"select block_name from m_block where block_id=" + entity.getSelBlock());
		} catch (Exception ex) {
			dynamicValue = "--";
		}
		entity.setSelBlockVal(dynamicValue.toString());
		try {
			dynamicValue = CommonUtil.getDynSingleData(entityManager,
					"select gp_name from m_gp where gp_id=" + entity.getSelGP());
		} catch (Exception ex) {
			dynamicValue = "--";
		}
		entity.setSelGPVal(dynamicValue.toString());
		try {
			dynamicValue = CommonUtil.getDynSingleData(entityManager,
					"select village_name from m_village_master where village_id=" + entity.getSelVillage());
		} catch (Exception ex) {
			dynamicValue = "--";
		}
		entity.setSelVillageVal(dynamicValue.toString());
		dynamicValue = (selCaste.has(entity.getSelCaste().toString())) ? selCaste.get(entity.getSelCaste().toString())
				: "--";
		entity.setSelCasteVal(dynamicValue.toString());
		dynamicValue = (chkDiscloseyourdetails.has(entity.getChkDiscloseyourdetails().toString()))
				? chkDiscloseyourdetails.get(entity.getChkDiscloseyourdetails().toString())
				: "--";
		entity.setChkDiscloseyourdetailsVal(dynamicValue.toString());
		try {
			dynamicValue = CommonUtil.getDynSingleData(entityManager,
					"select district_name from land_bank.district_master where district_code="
							+ entity.getSelDistrict13());
		} catch (Exception ex) {
			dynamicValue = "--";
		}
		entity.setSelDistrict13Val(dynamicValue.toString());
		try {
			dynamicValue = CommonUtil.getDynSingleData(entityManager,
					"select tahasil_name from land_bank.tahasil_master where tahasil_code=" + entity.getSelTahasil());
		} catch (Exception ex) {
			dynamicValue = "--";
		}
		entity.setSelTahasilVal(dynamicValue.toString());
		try {
			dynamicValue = CommonUtil.getDynSingleData(entityManager,
					"select village_name from land_bank.village_master where village_code=" + entity.getSelVillage15());
		} catch (Exception ex) {
			dynamicValue = "--";
		}
		entity.setSelVillage15Val(dynamicValue.toString());
		try {
			dynamicValue = CommonUtil.getDynSingleData(entityManager,
					"select khata_no from land_bank.khatian_information where khatian_code=" + entity.getSelKhataNo());
		} catch (Exception ex) {
			dynamicValue = "--";
		}
		entity.setSelKhataNoVal(dynamicValue.toString());
		try {
			dynamicValue = CommonUtil.getDynSingleData(entityManager,
					"select plot_no from land_bank.plot_information where plot_code=" + entity.getSelPlotNo());
		} catch (Exception ex) {
			dynamicValue = "--";
		}
		entity.setSelPlotNoVal(dynamicValue.toString());
		dynamicValue = (selModeofOccupation.has(entity.getSelModeofOccupation().toString()))
				? selModeofOccupation.get(entity.getSelModeofOccupation().toString())
				: "--";
		entity.setSelModeofOccupationVal(dynamicValue.toString());

		return new JSONObject(entity);
	}

	@Override
	public JSONObject getAll(String formParams) {
		logger.info("Inside getAll method of GrievanceServiceImpl");
		JSONObject jsonData = new JSONObject(formParams);
		Integer totalDataPresent = grievanceRepository.countByBitDeletedFlag(false);
		Pageable pageRequest = PageRequest.of(jsonData.has("pageNo") ? jsonData.getInt("pageNo") - 1 : 0,
				jsonData.has("size") ? jsonData.getInt("size") : totalDataPresent);
		List<Grievance> grievanceResp = grievanceRepository.findAllByBitDeletedFlag(false, pageRequest);
		for (Grievance entity : grievanceResp) {
			try {
				dynamicValue = CommonUtil.getDynSingleData(entityManager,
						"select month_name from m_month where month_id="
								+ entity.getSelMonthofUnauthorizedOccupation());
			} catch (Exception ex) {
				dynamicValue = "--";
			}
			entity.setSelMonthofUnauthorizedOccupationVal(dynamicValue.toString());
			try {
				dynamicValue = CommonUtil.getDynSingleData(entityManager,
						"select district_name from m_district where district_id=" + entity.getSelDistrict());
			} catch (Exception ex) {
				dynamicValue = "--";
			}
			entity.setSelDistrictVal(dynamicValue.toString());
			try {
				dynamicValue = CommonUtil.getDynSingleData(entityManager,
						"select block_name from m_block where block_id=" + entity.getSelBlock());
			} catch (Exception ex) {
				dynamicValue = "--";
			}
			entity.setSelBlockVal(dynamicValue.toString());
			try {
				dynamicValue = CommonUtil.getDynSingleData(entityManager,
						"select gp_name from m_gp where gp_id=" + entity.getSelGP());
			} catch (Exception ex) {
				dynamicValue = "--";
			}
			entity.setSelGPVal(dynamicValue.toString());
			try {
				dynamicValue = CommonUtil.getDynSingleData(entityManager,
						"select village_name from m_village_master where village_id=" + entity.getSelVillage());
			} catch (Exception ex) {
				dynamicValue = "--";
			}
			entity.setSelVillageVal(dynamicValue.toString());
			dynamicValue = (selCaste.has(entity.getSelCaste().toString()))
					? selCaste.get(entity.getSelCaste().toString())
					: "--";
			entity.setSelCasteVal(dynamicValue.toString());
			dynamicValue = (chkDiscloseyourdetails.has(entity.getChkDiscloseyourdetails().toString()))
					? chkDiscloseyourdetails.get(entity.getChkDiscloseyourdetails().toString())
					: "--";
			entity.setChkDiscloseyourdetailsVal(dynamicValue.toString());
			try {
				dynamicValue = CommonUtil.getDynSingleData(entityManager,
						"select district_name from land_bank.district_master where district_code="
								+ entity.getSelDistrict13());
			} catch (Exception ex) {
				dynamicValue = "--";
			}
			entity.setSelDistrict13Val(dynamicValue.toString());
			try {
				dynamicValue = CommonUtil.getDynSingleData(entityManager,
						"select tahasil_name from land_bank.tahasil_master where tahasil_code="
								+ entity.getSelTahasil());
			} catch (Exception ex) {
				dynamicValue = "--";
			}
			entity.setSelTahasilVal(dynamicValue.toString());
			try {
				dynamicValue = CommonUtil.getDynSingleData(entityManager,
						"select village_name from land_bank.village_master where village_code="
								+ entity.getSelVillage15());
			} catch (Exception ex) {
				dynamicValue = "--";
			}
			entity.setSelVillage15Val(dynamicValue.toString());
			try {
				dynamicValue = CommonUtil.getDynSingleData(entityManager,
						"select khata_no from land_bank.khatian_information where khatian_code="
								+ entity.getSelKhataNo());
			} catch (Exception ex) {
				dynamicValue = "--";
			}
			entity.setSelKhataNoVal(dynamicValue.toString());
			try {
				dynamicValue = CommonUtil.getDynSingleData(entityManager,
						"select plot_no from land_bank.plot_information where plot_code=" + entity.getSelPlotNo());
			} catch (Exception ex) {
				dynamicValue = "--";
			}
			entity.setSelPlotNoVal(dynamicValue.toString());
			dynamicValue = (selModeofOccupation.has(entity.getSelModeofOccupation().toString()))
					? selModeofOccupation.get(entity.getSelModeofOccupation().toString())
					: "--";
			entity.setSelModeofOccupationVal(dynamicValue.toString());

		}
		json.put("status", 200);
		json.put("result", new JSONArray(grievanceResp));
		json.put("count", totalDataPresent);
		return json;
	}

	@Override
	public JSONObject deleteById(Integer id) {
		logger.info("Inside deleteById method of GrievanceServiceImpl");
		try {
			Grievance entity = grievanceRepository.findByIntIdAndBitDeletedFlag(id, false);
			entity.setBitDeletedFlag(true);
			grievanceRepository.save(entity);
			json.put("status", 200);
		} catch (Exception e) {
			logger.error("Inside deleteById method of GrievanceServiceImpl some error occur:" + e);
			json.put("status", 400);
		}
		return json;
	}

	public static JSONArray fillselMonthofUnauthorizedOccupationList(EntityManager em, String jsonVal) {
		logger.info("Inside fillselMonthofUnauthorizedOccupationList method of GrievanceServiceImpl");
		JSONArray mainJSONFile = new JSONArray();
		String query = "Select month_id,month_name from m_month where deleted_flag ='0' order by month_id";
		List<Object[]> dataList = CommonUtil.getDynResultList(em, query);
		for (Object[] data : dataList) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("month_id", data[0]);
			jsonObj.put("month_name", data[1]);
			mainJSONFile.put(jsonObj);
		}
		return mainJSONFile;
	}

	public static JSONArray fillselDistrictList(EntityManager em, String jsonVal) {
		logger.info("Inside fillselDistrictList method of GrievanceServiceImpl");
		JSONArray mainJSONFile = new JSONArray();
		String query = "Select district_id,district_name from m_district where deleted_flag = '0' and state_id = 1";
		List<Object[]> dataList = CommonUtil.getDynResultList(em, query);
		for (Object[] data : dataList) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("district_id", data[0]);
			jsonObj.put("district_name", data[1]);
			mainJSONFile.put(jsonObj);
		}
		return mainJSONFile;
	}

	public static JSONArray fillselBlockList(EntityManager em, String jsonVal) {
		logger.info("Inside fillselBlockList method of GrievanceServiceImpl");
		JSONArray mainJSONFile = new JSONArray();
		JSONObject jsonDepend = new JSONObject(jsonVal);
		String val = "";
		val = jsonDepend.get("district_id").toString();
		String query = "Select block_id,block_name from m_block where deleted_flag = '0' and district_id=" + val;
		List<Object[]> dataList = CommonUtil.getDynResultList(em, query);
		for (Object[] data : dataList) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("block_id", data[0]);
			jsonObj.put("block_name", data[1]);
			mainJSONFile.put(jsonObj);
		}
		return mainJSONFile;
	}

	public static JSONArray fillselGPList(EntityManager em, String jsonVal) {
		logger.info("Inside fillselGPList method of GrievanceServiceImpl");
		JSONArray mainJSONFile = new JSONArray();
		JSONObject jsonDepend = new JSONObject(jsonVal);
		String val = "";
		val = jsonDepend.get("block_id").toString();
		String query = "Select gp_id,gp_name from m_gp where deleted_flag = '0' and block_id=" + val;
		List<Object[]> dataList = CommonUtil.getDynResultList(em, query);
		for (Object[] data : dataList) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("gp_id", data[0]);
			jsonObj.put("gp_name", data[1]);
			mainJSONFile.put(jsonObj);
		}
		return mainJSONFile;
	}

	public static JSONArray fillselVillageList(EntityManager em, String jsonVal) {
		logger.info("Inside fillselVillageList method of GrievanceServiceImpl");
		JSONArray mainJSONFile = new JSONArray();
		JSONObject jsonDepend = new JSONObject(jsonVal);
		String val = "";
		val = jsonDepend.get("gp_id").toString();
		String query = "Select village_id,village_name from m_village_master where deleted_flag = '0' and gp_id=" + val;
		List<Object[]> dataList = CommonUtil.getDynResultList(em, query);
		for (Object[] data : dataList) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("village_id", data[0]);
			jsonObj.put("village_name", data[1]);
			mainJSONFile.put(jsonObj);
		}
		return mainJSONFile;
	}

	public static JSONArray fillselDistrict13List(EntityManager em, String jsonVal) {
		logger.info("Inside fillselDistrict13List method of GrievanceServiceImpl");
		JSONArray mainJSONFile = new JSONArray();
		String query = "Select district_code,district_name from land_bank.district_master where state_code = '01'";
		List<Object[]> dataList = CommonUtil.getDynResultList(em, query);
		for (Object[] data : dataList) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("district_code", data[0]);
			jsonObj.put("district_name", data[1]);
			mainJSONFile.put(jsonObj);
		}
		return mainJSONFile;
	}

	public static JSONArray fillselTahasilList(EntityManager em, String jsonVal) {
		logger.info("Inside fillselTahasilList method of GrievanceServiceImpl");
		JSONArray mainJSONFile = new JSONArray();
		JSONObject jsonDepend = new JSONObject(jsonVal);
		String val = "";
		val = jsonDepend.get("district_code").toString();
		String query = "Select tahasil_code,tahasil_name from land_bank.tahasil_master where district_code='" + val
				+ "'";
		List<Object[]> dataList = CommonUtil.getDynResultList(em, query);
		for (Object[] data : dataList) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("tahasil_code", data[0]);
			jsonObj.put("tahasil_name", data[1]);
			mainJSONFile.put(jsonObj);
		}
		return mainJSONFile;
	}

	public static JSONArray fillselVillage15List(EntityManager em, String jsonVal) {
		logger.info("Inside fillselVillage15List method of GrievanceServiceImpl");
		JSONArray mainJSONFile = new JSONArray();
		JSONObject jsonDepend = new JSONObject(jsonVal);
		String val = "";
		val = jsonDepend.get("tahasil_code").toString();
		String query = "Select village_code,village_name from land_bank.village_master where tahasil_code='" + val
				+ "'";
		List<Object[]> dataList = CommonUtil.getDynResultList(em, query);
		for (Object[] data : dataList) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("village_code", data[0]);
			jsonObj.put("village_name", data[1]);
			mainJSONFile.put(jsonObj);
		}
		return mainJSONFile;
	}

	public static JSONArray fillselKhataNoList(EntityManager em, String jsonVal) {
		logger.info("Inside fillselKhataNoList method of GrievanceServiceImpl");
		JSONArray mainJSONFile = new JSONArray();
		JSONObject jsonDepend = new JSONObject(jsonVal);
		String val = "";
		val = jsonDepend.get("village_code").toString();
		String query = "Select khatian_code,khata_no from land_bank.khatian_information where village_code='" + val
				+ "'";
		List<Object[]> dataList = CommonUtil.getDynResultList(em, query);
		for (Object[] data : dataList) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("khatian_code", data[0]);
			jsonObj.put("khata_no", data[1]);
			mainJSONFile.put(jsonObj);
		}
		return mainJSONFile;
	}

	public static JSONArray fillselPlotNoList(EntityManager em, String jsonVal) {
		logger.info("Inside fillselPlotNoList method of GrievanceServiceImpl");
		JSONArray mainJSONFile = new JSONArray();
		JSONObject jsonDepend = new JSONObject(jsonVal);
		String val = "";
		val = jsonDepend.get("khatian_code").toString();
		String query = "Select plot_code,plot_no from land_bank.plot_information where khatian_code='" + val + "'";
		List<Object[]> dataList = CommonUtil.getDynResultList(em, query);
		for (Object[] data : dataList) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("plot_code", data[0]);
			jsonObj.put("plot_no", data[1]);
			mainJSONFile.put(jsonObj);
		}
		return mainJSONFile;
	}

}