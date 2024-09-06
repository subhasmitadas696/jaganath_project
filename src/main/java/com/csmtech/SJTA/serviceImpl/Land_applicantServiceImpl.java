package com.csmtech.SJTA.serviceImpl;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.csmtech.SJTA.entity.Land_applicant;
import com.csmtech.SJTA.repository.LandApplicantNativeRepository;
import com.csmtech.SJTA.repository.LandApprovalRepository;
import com.csmtech.SJTA.repository.Land_applicantRepository;
import com.csmtech.SJTA.service.Land_applicantService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Transactional
@Service
public class Land_applicantServiceImpl implements Land_applicantService {
	@Autowired
	private Land_applicantRepository land_applicantRepository;
	@Autowired
	LandApprovalRepository landApprovalRepository;
	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private LandApplicantNativeRepository reponative;

	Integer parentId;
	Object dynamicValue = null;

	@Override
	public JSONObject userSave(String data) {
		JSONObject json = new JSONObject();
		try {
			ObjectMapper om = new ObjectMapper();
			Land_applicant land_applicant = om.readValue(data, Land_applicant.class);
			List<String> fileUploadList = new ArrayList<String>();
			fileUploadList.add(land_applicant.getFileUploadDocument());
			if (!Objects.isNull(land_applicant.getIntId())) {
				//Land_applicant getEntity = land_applicantRepository
						//.findByIntId(land_applicant.getIntId());
				System.out.println(land_applicant.getIntId());
//				getEntity.setTxtApplicantName(land_applicant.getTxtApplicantName());
//				getEntity.setTxtFatherHusbandName(land_applicant.getTxtFatherHusbandName());
//				getEntity.setTxtMobileNo(land_applicant.getTxtMobileNo());
//				getEntity.setTxtEmail(land_applicant.getTxtEmail());
//				getEntity.setSelDocumentType(land_applicant.getSelDocumentType());
//				getEntity.setTxtDocumentNo(land_applicant.getTxtDocumentNo());
//				getEntity.setFileUploadDocument(land_applicant.getFileUploadDocument());
//				getEntity.setSelState(land_applicant.getSelState());
//				getEntity.setSelDistrict(land_applicant.getSelDistrict());
//				getEntity.setSelBlockULB(land_applicant.getSelBlockULB());
//				getEntity.setSelGPWardNo(land_applicant.getSelGPWardNo());
//				getEntity.setSelVillageLocalAreaName(land_applicant.getSelVillageLocalAreaName());
//				getEntity.setTxtPoliceStation(land_applicant.getTxtPoliceStation());
//				getEntity.setTxtPostOffice(land_applicant.getTxtPostOffice());
//				getEntity.setTxtHabitationStreetNoLandmark(land_applicant.getTxtHabitationStreetNoLandmark());
//				getEntity.setTxtHouseNo(land_applicant.getTxtHouseNo());
//				getEntity.setTxtPinCode(land_applicant.getTxtPinCode());
//				getEntity.setSelState17(land_applicant.getSelState17());
//				getEntity.setSelDistrict18(land_applicant.getSelDistrict18());
//				getEntity.setSelBlockULB19(land_applicant.getSelBlockULB19());
//				getEntity.setSelGPWARDNumber(land_applicant.getSelGPWARDNumber());
//				getEntity.setSelVillageLocalAreaName21(land_applicant.getSelVillageLocalAreaName21());
//				getEntity.setTxtPoliceStation22(land_applicant.getTxtPoliceStation22());
//				getEntity.setTxtPostOffice23(land_applicant.getTxtPostOffice23());
//				getEntity.setTxtHabitationStreetNoLandmark24(land_applicant.getTxtHabitationStreetNoLandmark24());
//				getEntity.setTxtHouseNo25(land_applicant.getTxtHouseNo25());
//				getEntity.setTxtPinCode26(land_applicant.getTxtPinCode26());
//				getEntity.setIntUpdatedBy(land_applicant.getIntCreatedBy());

				//Land_applicant updateData = land_applicantRepository.save(getEntity);
				Land_applicant updateRecord=reponative.updateLandApplicant(land_applicant);
				parentId = updateRecord.getIntId();
				json.put("status", 202);
				json.put("app_id", parentId);
				return json;
			} else {
				land_applicant.setIntCreatedBy(land_applicant.getIntCreatedBy());
				Integer dataSave=reponative.saveLandApplicantRecord(land_applicant);
				parentId = dataSave;
				json.put("status", 200);
			}
			json.put("app_id", parentId);
			for (String fileUpload : fileUploadList) {
				if (!fileUpload.equals("")) {
					File f = new File("D://TempLandAppFile/" + fileUpload);
					if (f.exists()) {
						File src = new File("D://TempLandAppFile/" + fileUpload);
						File dest = new File("D:/docsjts/" + fileUpload);
						try {
							Files.copy(src.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
							Files.delete(src.toPath());
						} catch (IOException e) {
							System.out.println("Iniside Error");
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
	public JSONObject getById(Integer id) {
		Land_applicant entity = land_applicantRepository.findByIntId(id);
		try {
			dynamicValue = entityManager
					.createNativeQuery(
							"select doc_name from m_document_type where doc_type_id=" + entity.getSelDocumentType())
					.getSingleResult();
		} catch (Exception ex) {
			dynamicValue = "--";
		}
		entity.setSelDocumentTypeVal(dynamicValue.toString());
		try {
			dynamicValue = entityManager
					.createNativeQuery("select state_name from m_state where state_id=" + entity.getSelState())
					.getSingleResult();
		} catch (Exception ex) {
			dynamicValue = "--";
		}
		entity.setSelStateVal(dynamicValue.toString());
		try {
			dynamicValue = entityManager
					.createNativeQuery(
							"select district_name from m_district where district_id=" + entity.getSelDistrict())
					.getSingleResult();
		} catch (Exception ex) {
			dynamicValue = "--";
		}
		entity.setSelDistrictVal(dynamicValue.toString());
		try {
			dynamicValue = entityManager
					.createNativeQuery("select block_name from m_block where block_id=" + entity.getSelBlockULB())
					.getSingleResult();
		} catch (Exception ex) {
			dynamicValue = "--";
		}
		entity.setSelBlockULBVal(dynamicValue.toString());
		try {
			dynamicValue = entityManager
					.createNativeQuery("select gp_name from m_gp where gp_id=" + entity.getSelGPWardNo())
					.getSingleResult();
		} catch (Exception ex) {
			dynamicValue = "--";
		}
		entity.setSelGPWardNoVal(dynamicValue.toString());
		try {
			dynamicValue = entityManager.createNativeQuery(
					"select village_name from m_village_master where village_id=" + entity.getSelVillageLocalAreaName())
					.getSingleResult();
		} catch (Exception ex) {
			dynamicValue = "--";
		}
		entity.setSelVillageLocalAreaNameVal(dynamicValue.toString());
		try {
			dynamicValue = entityManager
					.createNativeQuery("select state_name from m_state where state_id=" + entity.getSelState17())
					.getSingleResult();
		} catch (Exception ex) {
			dynamicValue = "--";
		}
		entity.setSelState17Val(dynamicValue.toString());
		try {
			dynamicValue = entityManager
					.createNativeQuery(
							"select district_name from m_district where district_id=" + entity.getSelDistrict18())
					.getSingleResult();
		} catch (Exception ex) {
			dynamicValue = "--";
		}
		entity.setSelDistrict18Val(dynamicValue.toString());
		try {
			dynamicValue = entityManager
					.createNativeQuery("select block_name from m_block where block_id=" + entity.getSelBlockULB19())
					.getSingleResult();
		} catch (Exception ex) {
			dynamicValue = "--";
		}
		entity.setSelBlockULB19Val(dynamicValue.toString());
		try {
			dynamicValue = entityManager
					.createNativeQuery("select gp_name from m_gp where gp_id=" + entity.getSelGPWARDNumber())
					.getSingleResult();
		} catch (Exception ex) {
			dynamicValue = "--";
		}
		entity.setSelGPWARDNumberVal(dynamicValue.toString());
		try {
			dynamicValue = entityManager.createNativeQuery("select village_name from m_village_master where village_id="
					+ entity.getSelVillageLocalAreaName21()).getSingleResult();
		} catch (Exception ex) {
			dynamicValue = "--";
		}
		entity.setSelVillageLocalAreaName21Val(dynamicValue.toString());

		return new JSONObject(entity);
	}

	@Override
	public JSONArray getAll(String formParams) {
		JSONObject jsonData = new JSONObject(formParams);
		List<Land_applicant> land_applicantResp = land_applicantRepository.findAllByBitDeletedFlag();
		for (Land_applicant entity : land_applicantResp) {
			try {
				dynamicValue = entityManager
						.createNativeQuery(
								"select doc_name from m_document_type where doc_type_id=" + entity.getSelDocumentType())
						.getSingleResult();
			} catch (Exception ex) {
				dynamicValue = "--";
			}
			entity.setSelDocumentTypeVal(dynamicValue.toString());
			try {
				dynamicValue = entityManager
						.createNativeQuery("select state_name from m_state where state_id=" + entity.getSelState())
						.getSingleResult();
			} catch (Exception ex) {
				dynamicValue = "--";
			}
			entity.setSelStateVal(dynamicValue.toString());
			try {
				dynamicValue = entityManager
						.createNativeQuery(
								"select district_name from m_district where district_id=" + entity.getSelDistrict())
						.getSingleResult();
			} catch (Exception ex) {
				dynamicValue = "--";
			}
			entity.setSelDistrictVal(dynamicValue.toString());
			try {
				dynamicValue = entityManager
						.createNativeQuery("select block_name from m_block where block_id=" + entity.getSelBlockULB())
						.getSingleResult();
			} catch (Exception ex) {
				dynamicValue = "--";
			}
			entity.setSelBlockULBVal(dynamicValue.toString());
			try {
				dynamicValue = entityManager
						.createNativeQuery("select gp_name from m_gp where gp_id=" + entity.getSelGPWardNo())
						.getSingleResult();
			} catch (Exception ex) {
				dynamicValue = "--";
			}
			entity.setSelGPWardNoVal(dynamicValue.toString());
			try {
				dynamicValue = entityManager
						.createNativeQuery("select village_name from m_village_master where village_id="
								+ entity.getSelVillageLocalAreaName())
						.getSingleResult();
			} catch (Exception ex) {
				dynamicValue = "--";
			}
			entity.setSelVillageLocalAreaNameVal(dynamicValue.toString());
			try {
				dynamicValue = entityManager
						.createNativeQuery("select state_name from m_state where state_id=" + entity.getSelState17())
						.getSingleResult();
			} catch (Exception ex) {
				dynamicValue = "--";
			}
			entity.setSelState17Val(dynamicValue.toString());
			try {
				dynamicValue = entityManager
						.createNativeQuery(
								"select district_name from m_district where district_id=" + entity.getSelDistrict18())
						.getSingleResult();
			} catch (Exception ex) {
				dynamicValue = "--";
			}
			entity.setSelDistrict18Val(dynamicValue.toString());
			try {
				dynamicValue = entityManager
						.createNativeQuery("select block_name from m_block where block_id=" + entity.getSelBlockULB19())
						.getSingleResult();
			} catch (Exception ex) {
				dynamicValue = "--";
			}
			entity.setSelBlockULB19Val(dynamicValue.toString());
			try {
				dynamicValue = entityManager
						.createNativeQuery("select gp_name from m_gp where gp_id=" + entity.getSelGPWARDNumber())
						.getSingleResult();
			} catch (Exception ex) {
				dynamicValue = "--";
			}
			entity.setSelGPWARDNumberVal(dynamicValue.toString());
			try {
				dynamicValue = entityManager
						.createNativeQuery("select village_name from m_village_master where village_id="
								+ entity.getSelVillageLocalAreaName21())
						.getSingleResult();
			} catch (Exception ex) {
				dynamicValue = "--";
			}
			entity.setSelVillageLocalAreaName21Val(dynamicValue.toString());

		}
		return new JSONArray(land_applicantResp);
	}

	@Override
	public JSONObject deleteById(Integer id) {
		JSONObject json = new JSONObject();
		try {
			Land_applicant entity = land_applicantRepository.findByIntId(id);
			//entity.setBitDeletedFlag(true);
			land_applicantRepository.save(entity);
			json.put("status", 200);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("status", 400);
		}
		return json;
	}

	public static JSONArray fillselDocumentTypeList(EntityManager em, String jsonVal) {
		JSONArray mainJSONFile = new JSONArray();
		String query = "Select doc_type_id,doc_name from m_document_type where deleted_flag = '0'";
		List<Object[]> dataList = em.createNativeQuery(query).getResultList();
		for (Object[] data : dataList) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("doc_type_id", data[0]);
			jsonObj.put("doc_name", data[1]);
			mainJSONFile.put(jsonObj);
		}
		return mainJSONFile;
	}

	public static JSONArray fillselStateList(EntityManager em, String jsonVal) {
		JSONArray mainJSONFile = new JSONArray();
		String query = "Select state_id,state_name from m_state where deleted_flag='0'";
		List<Object[]> dataList = em.createNativeQuery(query).getResultList();
		for (Object[] data : dataList) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("state_id", data[0]);
			jsonObj.put("state_name", data[1]);
			mainJSONFile.put(jsonObj);
		}
		return mainJSONFile;
	}

	public static JSONArray fillselDistrictList(EntityManager em, String jsonVal) {
		JSONArray mainJSONFile = new JSONArray();
		JSONObject jsonDepend = new JSONObject(jsonVal);
		String val = "";
		val = jsonDepend.get("state_id").toString();
		String query = "Select district_id,district_name from m_district where deleted_flag ='0' and state_id=" + val;
		List<Object[]> dataList = em.createNativeQuery(query).getResultList();
		for (Object[] data : dataList) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("district_id", data[0]);
			jsonObj.put("district_name", data[1]);
			mainJSONFile.put(jsonObj);
		}
		return mainJSONFile;
	}

	public static JSONArray fillselBlockULBList(EntityManager em, String jsonVal) {
		JSONArray mainJSONFile = new JSONArray();
		JSONObject jsonDepend = new JSONObject(jsonVal);
		String val = "";
		val = jsonDepend.get("district_id").toString();
		String query = "Select block_id,block_name from m_block where deleted_flag = '0' and district_id=" + val;
		List<Object[]> dataList = em.createNativeQuery(query).getResultList();
		for (Object[] data : dataList) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("block_id", data[0]);
			jsonObj.put("block_name", data[1]);
			mainJSONFile.put(jsonObj);
		}
		return mainJSONFile;
	}

	public static JSONArray fillselGPWardNoList(EntityManager em, String jsonVal) {
		JSONArray mainJSONFile = new JSONArray();
		JSONObject jsonDepend = new JSONObject(jsonVal);
		String val = "";
		val = jsonDepend.get("block_id").toString();
		String query = "Select gp_id,gp_name from m_gp where deleted_flag = '0' and block_id=" + val;
		List<Object[]> dataList = em.createNativeQuery(query).getResultList();
		for (Object[] data : dataList) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("gp_id", data[0]);
			jsonObj.put("gp_name", data[1]);
			mainJSONFile.put(jsonObj);
		}
		return mainJSONFile;
	}

	public static JSONArray fillselVillageLocalAreaNameList(EntityManager em, String jsonVal) {
		JSONArray mainJSONFile = new JSONArray();
		JSONObject jsonDepend = new JSONObject(jsonVal);
		String val = "";
		val = jsonDepend.get("gp_id").toString();
		String query = "Select village_id,village_name from m_village_master where deleted_flag='0' and gp_id=" + val;
		List<Object[]> dataList = em.createNativeQuery(query).getResultList();
		for (Object[] data : dataList) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("village_id", data[0]);
			jsonObj.put("village_name", data[1]);
			mainJSONFile.put(jsonObj);
		}
		return mainJSONFile;
	}

	public static JSONArray fillselState17List(EntityManager em, String jsonVal) {
		JSONArray mainJSONFile = new JSONArray();
		String query = "Select state_id,state_name from m_state where deleted_flag ='0'";
		List<Object[]> dataList = em.createNativeQuery(query).getResultList();
		for (Object[] data : dataList) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("state_id", data[0]);
			jsonObj.put("state_name", data[1]);
			mainJSONFile.put(jsonObj);
		}
		return mainJSONFile;
	}

	public static JSONArray fillselDistrict18List(EntityManager em, String jsonVal) {
		JSONArray mainJSONFile = new JSONArray();
		JSONObject jsonDepend = new JSONObject(jsonVal);
		String val = "";
		val = jsonDepend.get("state_id").toString();
		String query = "Select district_id,district_name from m_district where deleted_flag = '0' and state_id=" + val;
		List<Object[]> dataList = em.createNativeQuery(query).getResultList();
		for (Object[] data : dataList) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("district_id", data[0]);
			jsonObj.put("district_name", data[1]);
			mainJSONFile.put(jsonObj);
		}
		return mainJSONFile;
	}

	public static JSONArray fillselBlockULB19List(EntityManager em, String jsonVal) {
		JSONArray mainJSONFile = new JSONArray();
		JSONObject jsonDepend = new JSONObject(jsonVal);
		String val = "";
		val = jsonDepend.get("district_id").toString();
		String query = "Select block_id,block_name from m_block where deleted_flag = '0' and district_id=" + val;
		List<Object[]> dataList = em.createNativeQuery(query).getResultList();
		for (Object[] data : dataList) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("block_id", data[0]);
			jsonObj.put("block_name", data[1]);
			mainJSONFile.put(jsonObj);
		}
		return mainJSONFile;
	}

	public static JSONArray fillselGPWARDNumberList(EntityManager em, String jsonVal) {
		JSONArray mainJSONFile = new JSONArray();
		JSONObject jsonDepend = new JSONObject(jsonVal);
		String val = "";
		val = jsonDepend.get("block_id").toString();
		String query = "Select gp_id,gp_name from m_gp where deleted_flag = '0' and block_id=" + val;
		List<Object[]> dataList = em.createNativeQuery(query).getResultList();
		for (Object[] data : dataList) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("gp_id", data[0]);
			jsonObj.put("gp_name", data[1]);
			mainJSONFile.put(jsonObj);
		}
		return mainJSONFile;
	}

	public static JSONArray fillselVillageLocalAreaName21List(EntityManager em, String jsonVal) {
		JSONArray mainJSONFile = new JSONArray();
		JSONObject jsonDepend = new JSONObject(jsonVal);
		String val = "";
		val = jsonDepend.get("gp_id").toString();
		String query = "Select village_id,village_name from m_village_master where deleted_flag = '0' and gp_id=" + val;
		List<Object[]> dataList = em.createNativeQuery(query).getResultList();
		for (Object[] data : dataList) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("village_id", data[0]);
			jsonObj.put("village_name", data[1]);
			mainJSONFile.put(jsonObj);
		}
		return mainJSONFile;
	}

	@Override
	public JSONObject savedata(Integer landappid, Integer userId) {
		String query = "select role_id from approval_configration where status = '0' and approval_type='Land Application' and approval_level = 1";
		Object roleId = entityManager.createNativeQuery(query).getSingleResult();
		
		landApprovalRepository.insertLandWithRole(landappid, 1, (Short) roleId, 1, 0, "", userId);
		
		return null;
	}




}