package com.csmtech.SJTA.serviceImpl;

import java.util.List;
import org.json.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.json.JSONArray;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.File;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Objects;
import javax.transaction.Transactional;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import com.csmtech.SJTA.repository.Quarry_appicantRepository;
import com.csmtech.SJTA.entity.Quarry_appicant;
import com.csmtech.SJTA.service.Quarry_appicantService;

@Transactional
@Service
public class Quarry_appicantServiceImpl implements Quarry_appicantService {
	@Autowired
	private Quarry_appicantRepository quarry_appicantRepository;
	@Autowired
	EntityManager entityManager;

	Integer parentId = 0;
	Object dynamicValue = null;

	@Override
	public JSONObject save(String data) {
		JSONObject json = new JSONObject();
		try {
			ObjectMapper om = new ObjectMapper();
			Quarry_appicant quarry_appicant = om.readValue(data, Quarry_appicant.class);
			List<String> fileUploadList = new ArrayList<String>();
			fileUploadList.add(quarry_appicant.getFileDocument());
			if (!Objects.isNull(quarry_appicant.getIntId()) && quarry_appicant.getIntId() > 0) {
				Quarry_appicant getEntity = quarry_appicantRepository
						.findByIntIdAndBitDeletedFlag(quarry_appicant.getIntId(), false);
				getEntity.setTxtQuarryName(quarry_appicant.getTxtQuarryName());
				getEntity.setTxtPropatoryName(quarry_appicant.getTxtPropatoryName());
				getEntity.setTxtFatherHusbandName(quarry_appicant.getTxtFatherHusbandName());
				getEntity.setSelDocumentType(quarry_appicant.getSelDocumentType());
				getEntity.setTxtDocumentRefNo(quarry_appicant.getTxtDocumentRefNo());
				getEntity.setFileDocument(quarry_appicant.getFileDocument());
				getEntity.setSelState(quarry_appicant.getSelState());
				getEntity.setSelDistrict(quarry_appicant.getSelDistrict());
				getEntity.setSelBlockULB(quarry_appicant.getSelBlockULB());
				getEntity.setSelGPWardNumber(quarry_appicant.getSelGPWardNumber());
				getEntity.setSelVillageLocalAreaName(quarry_appicant.getSelVillageLocalAreaName());
				getEntity.setTxtPoliceStation(quarry_appicant.getTxtPoliceStation());
				getEntity.setTxtPostOffice(quarry_appicant.getTxtPostOffice());
				getEntity.setTxtHabitationStreetLandmark(quarry_appicant.getTxtHabitationStreetLandmark());
				getEntity.setTxtHouseNumberOtherDetails(quarry_appicant.getTxtHouseNumberOtherDetails());
				getEntity.setTxtPincode(quarry_appicant.getTxtPincode());
				getEntity.setSelState16(quarry_appicant.getSelState16());
				getEntity.setSelDistrict17(quarry_appicant.getSelDistrict17());
				getEntity.setSelBlockULB18(quarry_appicant.getSelBlockULB18());
				getEntity.setSelGPWardNumber19(quarry_appicant.getSelGPWardNumber19());
				getEntity.setSelVillageLocalAreaName20(quarry_appicant.getSelVillageLocalAreaName20());
				getEntity.setTxtPoliceStation21(quarry_appicant.getTxtPoliceStation21());
				getEntity.setTxtPostOffice22(quarry_appicant.getTxtPostOffice22());
				getEntity.setTxtHabitationStreetLandmark23(quarry_appicant.getTxtHabitationStreetLandmark23());
				getEntity.setTxtHouseNumberOtherDetails24(quarry_appicant.getTxtHouseNumberOtherDetails24());
				getEntity.setTxtPincode25(quarry_appicant.getTxtPincode25());
				getEntity.setTxtDurationStartDate(quarry_appicant.getTxtDurationStartDate());
				getEntity.setTxtDurationEndDate(quarry_appicant.getTxtDurationEndDate());
				Quarry_appicant updateData = quarry_appicantRepository.save(getEntity);
				parentId = updateData.getIntId();
				json.put("status", 202);
			} else {
				Quarry_appicant saveData = quarry_appicantRepository.save(quarry_appicant);
				parentId = saveData.getIntId();
				json.put("status", 200);
			}
			for (String fileUpload : fileUploadList) {
				if (!fileUpload.equals("")) {
					File f = new File("src/storage/tempfile/" + fileUpload);
					if (f.exists()) {
						File src = new File("src/storage/tempfile/" + fileUpload);
						File dest = new File("src/storage/individual-details/" + fileUpload);
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
		Quarry_appicant entity = quarry_appicantRepository.findByIntIdAndBitDeletedFlag(id, false);
		try {
			dynamicValue = entityManager.createNativeQuery(
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
					.createNativeQuery("select gp_name from m_gp where gp_id=" + entity.getSelGPWardNumber())
					.getSingleResult();
		} catch (Exception ex) {
			dynamicValue = "--";
		}
		entity.setSelGPWardNumberVal(dynamicValue.toString());
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
					.createNativeQuery("select state_name from m_state where state_id=" + entity.getSelState16())
					.getSingleResult();
		} catch (Exception ex) {
			dynamicValue = "--";
		}
		entity.setSelState16Val(dynamicValue.toString());
		try {
			dynamicValue = entityManager
					.createNativeQuery(
							"select district_name from m_district where district_id=" + entity.getSelDistrict17())
					.getSingleResult();
		} catch (Exception ex) {
			dynamicValue = "--";
		}
		entity.setSelDistrict17Val(dynamicValue.toString());
		try {
			dynamicValue = entityManager
					.createNativeQuery("select block_name from m_block where block_id=" + entity.getSelBlockULB18())
					.getSingleResult();
		} catch (Exception ex) {
			dynamicValue = "--";
		}
		entity.setSelBlockULB18Val(dynamicValue.toString());
		try {
			dynamicValue = entityManager
					.createNativeQuery("select gp_name from m_gp where gp_id=" + entity.getSelGPWardNumber19())
					.getSingleResult();
		} catch (Exception ex) {
			dynamicValue = "--";
		}
		entity.setSelGPWardNumber19Val(dynamicValue.toString());
		try {
			dynamicValue = entityManager.createNativeQuery("select village_name from m_village_master where village_id="
					+ entity.getSelVillageLocalAreaName20()).getSingleResult();
		} catch (Exception ex) {
			dynamicValue = "--";
		}
		entity.setSelVillageLocalAreaName20Val(dynamicValue.toString());

		return new JSONObject(entity);
	}

	@Override
	public JSONArray getAll(String formParams) {
		JSONObject jsonData = new JSONObject(formParams);
		List<Quarry_appicant> quarry_appicantResp = quarry_appicantRepository.findAllByBitDeletedFlag(false);
		for (Quarry_appicant entity : quarry_appicantResp) {
			try {
				dynamicValue = entityManager.createNativeQuery(
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
						.createNativeQuery("select gp_name from m_gp where gp_id=" + entity.getSelGPWardNumber())
						.getSingleResult();
			} catch (Exception ex) {
				dynamicValue = "--";
			}
			entity.setSelGPWardNumberVal(dynamicValue.toString());
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
						.createNativeQuery("select state_name from m_state where state_id=" + entity.getSelState16())
						.getSingleResult();
			} catch (Exception ex) {
				dynamicValue = "--";
			}
			entity.setSelState16Val(dynamicValue.toString());
			try {
				dynamicValue = entityManager
						.createNativeQuery(
								"select district_name from m_district where district_id=" + entity.getSelDistrict17())
						.getSingleResult();
			} catch (Exception ex) {
				dynamicValue = "--";
			}
			entity.setSelDistrict17Val(dynamicValue.toString());
			try {
				dynamicValue = entityManager
						.createNativeQuery("select block_name from m_block where block_id=" + entity.getSelBlockULB18())
						.getSingleResult();
			} catch (Exception ex) {
				dynamicValue = "--";
			}
			entity.setSelBlockULB18Val(dynamicValue.toString());
			try {
				dynamicValue = entityManager
						.createNativeQuery("select gp_name from m_gp where gp_id=" + entity.getSelGPWardNumber19())
						.getSingleResult();
			} catch (Exception ex) {
				dynamicValue = "--";
			}
			entity.setSelGPWardNumber19Val(dynamicValue.toString());
			try {
				dynamicValue = entityManager
						.createNativeQuery("select village_name from m_village_master where village_id="
								+ entity.getSelVillageLocalAreaName20())
						.getSingleResult();
			} catch (Exception ex) {
				dynamicValue = "--";
			}
			entity.setSelVillageLocalAreaName20Val(dynamicValue.toString());

		}
		return new JSONArray(quarry_appicantResp);
	}

	@Override
	public JSONObject deleteById(Integer id) {
		JSONObject json = new JSONObject();
		try {
			Quarry_appicant entity = quarry_appicantRepository.findByIntIdAndBitDeletedFlag(id, false);
			entity.setBitDeletedFlag(true);
			quarry_appicantRepository.save(entity);
			json.put("status", 200);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("status", 400);
		}
		return json;
	}

	public static JSONArray fillselDocumentTypeList(EntityManager em, String jsonVal) {
		JSONArray mainJSONFile = new JSONArray();
		String query = "Select doc_type_id,doc_name from m_document_type where deleted_flag = 0";
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
		String query = "Select state_id,state_name from m_state where deleted_flag = 0";
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
		String query = "Select district_id,district_name from m_district where deleted_flag = 0 and state_id=" + val;
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
		String query = "Select block_id,block_name from m_block where deleted_flag = 0 and district_id=" + val;
		List<Object[]> dataList = em.createNativeQuery(query).getResultList();
		for (Object[] data : dataList) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("block_id", data[0]);
			jsonObj.put("block_name", data[1]);
			mainJSONFile.put(jsonObj);
		}
		return mainJSONFile;
	}

	public static JSONArray fillselGPWardNumberList(EntityManager em, String jsonVal) {
		JSONArray mainJSONFile = new JSONArray();
		JSONObject jsonDepend = new JSONObject(jsonVal);
		String val = "";
		val = jsonDepend.get("block_id").toString();
		String query = "Select gp_id,gp_name from m_gp where deleted_flag = 0 and block_id=" + val;
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
		String query = "Select village_id,village_name from m_village_master where deleted_flag = 0 and gp_id=" + val;
		List<Object[]> dataList = em.createNativeQuery(query).getResultList();
		for (Object[] data : dataList) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("village_id", data[0]);
			jsonObj.put("village_name", data[1]);
			mainJSONFile.put(jsonObj);
		}
		return mainJSONFile;
	}

	public static JSONArray fillselState16List(EntityManager em, String jsonVal) {
		JSONArray mainJSONFile = new JSONArray();
		String query = "Select state_id,state_name from m_state where deleted_flag = 0";
		List<Object[]> dataList = em.createNativeQuery(query).getResultList();
		for (Object[] data : dataList) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("state_id", data[0]);
			jsonObj.put("state_name", data[1]);
			mainJSONFile.put(jsonObj);
		}
		return mainJSONFile;
	}

	public static JSONArray fillselDistrict17List(EntityManager em, String jsonVal) {
		JSONArray mainJSONFile = new JSONArray();
		JSONObject jsonDepend = new JSONObject(jsonVal);
		String val = "";
		val = jsonDepend.get("state_id").toString();
		String query = "Select district_id,district_name from m_district where deleted_flag = 0 and state_id=" + val;
		List<Object[]> dataList = em.createNativeQuery(query).getResultList();
		for (Object[] data : dataList) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("district_id", data[0]);
			jsonObj.put("district_name", data[1]);
			mainJSONFile.put(jsonObj);
		}
		return mainJSONFile;
	}

	public static JSONArray fillselBlockULB18List(EntityManager em, String jsonVal) {
		JSONArray mainJSONFile = new JSONArray();
		JSONObject jsonDepend = new JSONObject(jsonVal);
		String val = "";
		val = jsonDepend.get("district_id").toString();
		String query = "Select block_id,block_name from m_block where deleted_flag = 0 and district_id=" + val;
		List<Object[]> dataList = em.createNativeQuery(query).getResultList();
		for (Object[] data : dataList) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("block_id", data[0]);
			jsonObj.put("block_name", data[1]);
			mainJSONFile.put(jsonObj);
		}
		return mainJSONFile;
	}

	public static JSONArray fillselGPWardNumber19List(EntityManager em, String jsonVal) {
		JSONArray mainJSONFile = new JSONArray();
		JSONObject jsonDepend = new JSONObject(jsonVal);
		String val = "";
		val = jsonDepend.get("block_id").toString();
		String query = "Select gp_id,gp_name from m_gp where deleted_flag = 0 and block_id=" + val;
		List<Object[]> dataList = em.createNativeQuery(query).getResultList();
		for (Object[] data : dataList) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("gp_id", data[0]);
			jsonObj.put("gp_name", data[1]);
			mainJSONFile.put(jsonObj);
		}
		return mainJSONFile;
	}

	public static JSONArray fillselVillageLocalAreaName20List(EntityManager em, String jsonVal) {
		JSONArray mainJSONFile = new JSONArray();
		JSONObject jsonDepend = new JSONObject(jsonVal);
		String val = "";
		val = jsonDepend.get("gp_id").toString();
		String query = "Select village_id,village_name from m_village_master where deleted_flag = 0 and gp_id=" + val;
		List<Object[]> dataList = em.createNativeQuery(query).getResultList();
		for (Object[] data : dataList) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("village_id", data[0]);
			jsonObj.put("village_name", data[1]);
			mainJSONFile.put(jsonObj);
		}
		return mainJSONFile;
	}

}