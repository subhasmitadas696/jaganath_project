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
import com.csmtech.SJTA.repository.Quarry_land_scheduleRepository;
import com.csmtech.SJTA.entity.Quarry_land_schedule;
import com.csmtech.SJTA.service.Quarry_land_scheduleService;

@Transactional
@Service
public class Quarry_land_scheduleServiceImpl implements Quarry_land_scheduleService {
	@Autowired
	private Quarry_land_scheduleRepository quarry_land_scheduleRepository;
	@Autowired
	EntityManager entityManager;

	Integer parentId = 0;
	Object dynamicValue = null;

	@Override
	public JSONObject save(String data) {
		JSONObject json = new JSONObject();
		try {
			ObjectMapper om = new ObjectMapper();
			Quarry_land_schedule quarry_land_schedule = om.readValue(data, Quarry_land_schedule.class);
			List<String> fileUploadList = new ArrayList<String>();
			fileUploadList.add(quarry_land_schedule.getFileEnvironmentClearance());
			fileUploadList.add(quarry_land_schedule.getFileConsenttoEstablish());
			fileUploadList.add(quarry_land_schedule.getFileCTEApprovalfromPollutionControlBoard());
			fileUploadList.add(quarry_land_schedule.getFileCTOConsenttoOperate());
			fileUploadList.add(quarry_land_schedule.getFileAgreementCopy());
			if (!Objects.isNull(quarry_land_schedule.getIntId()) && quarry_land_schedule.getIntId() > 0) {
				Quarry_land_schedule getEntity = quarry_land_scheduleRepository
						.findByIntIdAndBitDeletedFlag(quarry_land_schedule.getIntId(), false);
				getEntity.setTxtQuarryAgreementNo(quarry_land_schedule.getTxtQuarryAgreementNo());
				getEntity.setSelDistrict(quarry_land_schedule.getSelDistrict());
				getEntity.setSelTehsil(quarry_land_schedule.getSelTehsil());
				getEntity.setSelMouza(quarry_land_schedule.getSelMouza());
				getEntity.setSelKhataNo(quarry_land_schedule.getSelKhataNo());
				getEntity.setSelPlotNo(quarry_land_schedule.getSelPlotNo());
				getEntity.setTxtAreainAcre(quarry_land_schedule.getTxtAreainAcre());
				getEntity.setFileEnvironmentClearance(quarry_land_schedule.getFileEnvironmentClearance());
				getEntity.setFileConsenttoEstablish(quarry_land_schedule.getFileConsenttoEstablish());
				getEntity.setFileCTEApprovalfromPollutionControlBoard(
						quarry_land_schedule.getFileCTEApprovalfromPollutionControlBoard());
				getEntity.setFileCTOConsenttoOperate(quarry_land_schedule.getFileCTOConsenttoOperate());
				getEntity.setFileAgreementCopy(quarry_land_schedule.getFileAgreementCopy());
				Quarry_land_schedule updateData = quarry_land_scheduleRepository.save(getEntity);
				parentId = updateData.getIntId();
				json.put("status", 202);
			} else {
				Quarry_land_schedule saveData = quarry_land_scheduleRepository.save(quarry_land_schedule);
				parentId = saveData.getIntId();
				json.put("status", 200);
			}
			for (String fileUpload : fileUploadList) {
				if (!fileUpload.equals("")) {
					File f = new File("src/storage/tempfile/" + fileUpload);
					if (f.exists()) {
						File src = new File("src/storage/tempfile/" + fileUpload);
						File dest = new File("src/storage/land-schedule/" + fileUpload);
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
		Quarry_land_schedule entity = quarry_land_scheduleRepository.findByIntIdAndBitDeletedFlag(id, false);
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
					.createNativeQuery(
							"select tehsil_name from m_tehsil where tehsil_id=" + entity.getSelTehsil())
					.getSingleResult();
		} catch (Exception ex) {
			dynamicValue = "--";
		}
		entity.setSelTehsilVal(dynamicValue.toString());
		try {
			dynamicValue = entityManager
					.createNativeQuery("select mouza_name from m_mouza where mouza_id=" + entity.getSelMouza())
					.getSingleResult();
		} catch (Exception ex) {
			dynamicValue = "--";
		}
		entity.setSelMouzaVal(dynamicValue.toString());
		try {
			dynamicValue = entityManager
					.createNativeQuery(
							"select khata_no from m_khata_no where khata_no_id=" + entity.getSelKhataNo())
					.getSingleResult();
		} catch (Exception ex) {
			dynamicValue = "--";
		}
		entity.setSelKhataNoVal(dynamicValue.toString());
		try {
			dynamicValue = entityManager
					.createNativeQuery(
							"select plot_no from m_plot_no where plot_no_id=" + entity.getSelPlotNo())
					.getSingleResult();
		} catch (Exception ex) {
			dynamicValue = "--";
		}
		entity.setSelPlotNoVal(dynamicValue.toString());

		return new JSONObject(entity);
	}

	@Override
	public JSONArray getAll(String formParams) {
		JSONObject jsonData = new JSONObject(formParams);
		List<Quarry_land_schedule> quarry_land_scheduleResp = quarry_land_scheduleRepository
				.findAllByBitDeletedFlag(false);
		for (Quarry_land_schedule entity : quarry_land_scheduleResp) {
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
						.createNativeQuery(
								"select tehsil_name from m_tehsil where tehsil_id=" + entity.getSelTehsil())
						.getSingleResult();
			} catch (Exception ex) {
				dynamicValue = "--";
			}
			entity.setSelTehsilVal(dynamicValue.toString());
			try {
				dynamicValue = entityManager
						.createNativeQuery(
								"select mouza_name from m_mouza where mouza_id=" + entity.getSelMouza())
						.getSingleResult();
			} catch (Exception ex) {
				dynamicValue = "--";
			}
			entity.setSelMouzaVal(dynamicValue.toString());
			try {
				dynamicValue = entityManager
						.createNativeQuery(
								"select khata_no from m_khata_no where khata_no_id=" + entity.getSelKhataNo())
						.getSingleResult();
			} catch (Exception ex) {
				dynamicValue = "--";
			}
			entity.setSelKhataNoVal(dynamicValue.toString());
			try {
				dynamicValue = entityManager
						.createNativeQuery(
								"select plot_no from m_plot_no where plot_no_id=" + entity.getSelPlotNo())
						.getSingleResult();
			} catch (Exception ex) {
				dynamicValue = "--";
			}
			entity.setSelPlotNoVal(dynamicValue.toString());

		}
		return new JSONArray(quarry_land_scheduleResp);
	}

	@Override
	public JSONObject deleteById(Integer id) {
		JSONObject json = new JSONObject();
		try {
			Quarry_land_schedule entity = quarry_land_scheduleRepository.findByIntIdAndBitDeletedFlag(id, false);
			entity.setBitDeletedFlag(true);
			quarry_land_scheduleRepository.save(entity);
			json.put("status", 200);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("status", 400);
		}
		return json;
	}

	public static JSONArray fillselDistrictList(EntityManager em, String jsonVal) {
		JSONArray mainJSONFile = new JSONArray();
		String query = "Select district_id,district_name from m_district where deleted_flag = 0 and state_id =1";
		List<Object[]> dataList = em.createNativeQuery(query).getResultList();
		for (Object[] data : dataList) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("district_id", data[0]);
			jsonObj.put("district_name", data[1]);
			mainJSONFile.put(jsonObj);
		}
		return mainJSONFile;
	}

	public static JSONArray fillselTehsilList(EntityManager em, String jsonVal) {
		JSONArray mainJSONFile = new JSONArray();
		JSONObject jsonDepend = new JSONObject(jsonVal);
		String val = "";
		val = jsonDepend.get("int_district_id").toString();
		String query = "Select tehsil_id,tehsil_name from m_tehsil where deleted_flag = 0 and int_district_id="
				+ val;
		List<Object[]> dataList = em.createNativeQuery(query).getResultList();
		for (Object[] data : dataList) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("tehsil_id", data[0]);
			jsonObj.put("tehsil_name", data[1]);
			mainJSONFile.put(jsonObj);
		}
		return mainJSONFile;
	}

	public static JSONArray fillselMouzaList(EntityManager em, String jsonVal) {
		JSONArray mainJSONFile = new JSONArray();
		JSONObject jsonDepend = new JSONObject(jsonVal);
		String val = "";
		val = jsonDepend.get("tehsil_id").toString();
		String query = "Select mouza_id,mouza_name from m_mouza where deleted_flag = 0 and tehsil_id="
				+ val;
		List<Object[]> dataList = em.createNativeQuery(query).getResultList();
		for (Object[] data : dataList) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("mouza_id", data[0]);
			jsonObj.put("mouza_name", data[1]);
			mainJSONFile.put(jsonObj);
		}
		return mainJSONFile;
	}

	public static JSONArray fillselKhataNoList(EntityManager em, String jsonVal) {
		JSONArray mainJSONFile = new JSONArray();
		JSONObject jsonDepend = new JSONObject(jsonVal);
		String val = "";
		val = jsonDepend.get("mouza_id").toString();
		String query = "Select khata_no_id,khata_no from m_khata_no where deleted_flag = 0 and mouza_id="
				+ val;
		List<Object[]> dataList = em.createNativeQuery(query).getResultList();
		for (Object[] data : dataList) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("khata_no_id", data[0]);
			jsonObj.put("khata_no", data[1]);
			mainJSONFile.put(jsonObj);
		}
		return mainJSONFile;
	}

	public static JSONArray fillselPlotNoList(EntityManager em, String jsonVal) {
		JSONArray mainJSONFile = new JSONArray();
		JSONObject jsonDepend = new JSONObject(jsonVal);
		String val = "";
		val = jsonDepend.get("khata_no_id").toString();
		String query = "Select plot_no_id,plot_no from m_plot_no where deleted_flag = 0 and khata_no_id="
				+ val;
		List<Object[]> dataList = em.createNativeQuery(query).getResultList();
		for (Object[] data : dataList) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("plot_no_id", data[0]);
			jsonObj.put("plot_no", data[1]);
			mainJSONFile.put(jsonObj);
		}
		return mainJSONFile;
	}

}