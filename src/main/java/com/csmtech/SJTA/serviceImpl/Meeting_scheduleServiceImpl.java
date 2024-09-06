package com.csmtech.SJTA.serviceImpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.csmtech.SJTA.entity.Meeting_schedule;
import com.csmtech.SJTA.repository.MeetingScheduleNativeRepository;
import com.csmtech.SJTA.repository.Meeting_scheduleRepository;
import com.csmtech.SJTA.service.Meeting_scheduleService;
import com.csmtech.SJTA.util.CommonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@Transactional
@Service
public class Meeting_scheduleServiceImpl implements Meeting_scheduleService {
	@Autowired
	private Meeting_scheduleRepository meeting_scheduleRepository;

	@Autowired
	private MeetingScheduleNativeRepository meetingScheduleRepo;

	@Autowired
	EntityManager entityManager;

	Integer parentId = 0;
	Object dynamicValue = null;
	private static final Logger logger = LoggerFactory.getLogger(Meeting_scheduleServiceImpl.class);
	JSONObject json = new JSONObject();
	@Value("${tempUpload.path}")
	private String tempUploadPath;
	@Value("${file.path}")
	private String finalUploadPath;

	@Override
	public JSONObject save(String data) {
		logger.info("Inside save method of Meeting_scheduleServiceImpl");
		try {
			ObjectMapper om = new ObjectMapper();
			Meeting_schedule meeting_schedule = om.readValue(data, Meeting_schedule.class);
			List<String> fileUploadList = new ArrayList<String>();
			if (!Objects.isNull(meeting_schedule.getIntId()) && meeting_schedule.getIntId() > 0) {
				Meeting_schedule updateData = meetingScheduleRepo.updateRecord(meeting_schedule);
				parentId = updateData.getIntId();
				json.put("status", 202);
			} else {
				Integer saveData = meetingScheduleRepo.saveRecord(meeting_schedule);
				parentId = saveData;
				json.put("status", 200);
			}
			json.put("id", parentId);

		} catch (Exception e) {
			logger.error("Inside save method of Meeting_scheduleServiceImpl some error occur:" + e);
			json.put("status", 400);
		}
		return json;
	}

	@Override
	public JSONObject updateFile(String data) {
		logger.info("Inside file update method of Meeting_scheduleServiceImpl");
		try {
			ObjectMapper om = new ObjectMapper();
			Meeting_schedule meeting_schedule = om.readValue(data, Meeting_schedule.class);
			List<String> fileUploadList = new ArrayList<String>();
			Meeting_schedule updateData = meetingScheduleRepo.updateFile(meeting_schedule);
			logger.info("file updated");
			parentId = updateData.getIntId();
			json.put("status", 202);
			json.put("id", parentId);
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
							System.out.println("Inside Error");
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("Inside save method of Meeting_scheduleServiceImpl some error occur:" + e);
			json.put("status", 400);
		}
		return json;
	}

	@Override
	public JSONObject getById(Integer id) {
		logger.info("Inside getById method of Meeting_scheduleServiceImpl");
		Meeting_schedule entity = meeting_scheduleRepository.findByIntId(id);
		try {
			dynamicValue = CommonUtil.getDynSingleData(entityManager,
					"select district_name from land_bank.district_master where district_code='"
							+ entity.getSelDistrict48() + "'");
		} catch (Exception ex) {
			dynamicValue = "--";
		}
		entity.setSelDistrict48Val(dynamicValue.toString());
		try {
			dynamicValue = CommonUtil.getDynSingleData(entityManager,
					"select tahasil_name from land_bank.tahasil_master where tahasil_code='" + entity.getSelTahasil49()
							+ "'");
		} catch (Exception ex) {
			dynamicValue = "--";
		}
		entity.setSelTahasil49Val(dynamicValue.toString());
		try {
			dynamicValue = CommonUtil.getDynSingleData(entityManager,
					"select village_name from land_bank.village_master where village_code='" + entity.getSelVillage50()
							+ "'");
		} catch (Exception ex) {
			dynamicValue = "--";
		}
		entity.setSelVillage50Val(dynamicValue.toString());
		try {
			dynamicValue = CommonUtil.getDynSingleData(entityManager,
					"select khata_no from land_bank.khatian_information where khatian_code='" + entity.getSelKhatian51()
							+ "'");
		} catch (Exception ex) {
			dynamicValue = "--";
		}
		entity.setSelKhatian51Val(dynamicValue.toString());
		try {
			dynamicValue = CommonUtil.getDynSingleData(entityManager,
					"select plot_no from land_bank.plot_information where plot_code='" + entity.getSelPlot52() + "'");
		} catch (Exception ex) {
			dynamicValue = "--";
		}
		entity.setSelPlot52Val(dynamicValue.toString());
		try {
			dynamicValue = CommonUtil.getDynSingleData(entityManager,
					"select meeting_level from m_meeting_level where meeting_level_id="
							+ entity.getSelMeetingLevel55());
		} catch (Exception ex) {
			dynamicValue = "--";
		}
		entity.setSelMeetingLevel55Val(dynamicValue.toString());

		return new JSONObject(entity);
	}

	@Override
	public JSONObject getAll(String formParams) {
		logger.info("Inside getAll method of Meeting_scheduleServiceImpl");
		JSONObject jsonData = new JSONObject(formParams);
		String selDistrict48 = "0";
		String selTahasil49 = "0";
		String selVillage50 = "0";
		String selKhatian51 = "0";
		String selPlot52 = "0";
		String txtMeetingDate53 = "";
		String selMeetingLevel55 = "";
		if (jsonData.has("selDistrict48") && !jsonData.isNull("selDistrict48")
				&& !jsonData.getString("selDistrict48").equals("")) {
			selDistrict48 = jsonData.getString("selDistrict48");
		}
		if (jsonData.has("selTahasil49") && !jsonData.isNull("selTahasil49")
				&& !jsonData.getString("selTahasil49").equals("")) {
			selTahasil49 = jsonData.getString("selTahasil49");
		}
		if (jsonData.has("selVillage50") && !jsonData.isNull("selVillage50")
				&& !jsonData.getString("selVillage50").equals("")) {
			selVillage50 = jsonData.getString("selVillage50");
		}
		if (jsonData.has("selKhatian51") && !jsonData.isNull("selKhatian51")
				&& !jsonData.getString("selKhatian51").equals("")) {
			selKhatian51 = jsonData.getString("selKhatian51");
		}
		if (jsonData.has("selPlot52") && !jsonData.isNull("selPlot52") && !jsonData.getString("selPlot52").equals("")) {
			selPlot52 = jsonData.getString("selPlot52");
		}
		if (jsonData.has("txtMeetingDate53") && !jsonData.isNull("txtMeetingDate53")
				&& !jsonData.getString("txtMeetingDate53").equals("")) {
			txtMeetingDate53 = jsonData.getString("txtMeetingDate53");
		}
		if (jsonData.has("selMeetingLevel55") && !jsonData.isNull("selMeetingLevel55")
				&& !jsonData.getString("selMeetingLevel55").equals("")) {
			selMeetingLevel55 = jsonData.getString("selMeetingLevel55");
		}
		Integer totalDataPresent = meeting_scheduleRepository.countByBitDeletedFlag();
		Pageable pageRequest = PageRequest.of(jsonData.has("pageNo") ? jsonData.getInt("pageNo") - 1 : 0,
				jsonData.has("size") ? jsonData.getInt("size") : totalDataPresent);
		List<Meeting_schedule> meeting_scheduleResp = meeting_scheduleRepository.findAllByBitDeletedFlag(pageRequest);
		for (Meeting_schedule entity : meeting_scheduleResp) {
			try {
				dynamicValue = CommonUtil.getDynSingleData(entityManager,
						"select district_name from land_bank.district_master where district_code='"
								+ entity.getSelDistrict48() + "'");
			} catch (Exception ex) {
				dynamicValue = "--";
			}
			entity.setSelDistrict48Val(dynamicValue.toString());
			try {
				dynamicValue = CommonUtil.getDynSingleData(entityManager,
						"select tahasil_name from land_bank.tahasil_master where tahasil_code='"
								+ entity.getSelTahasil49() + "'");
			} catch (Exception ex) {
				dynamicValue = "--";
			}
			entity.setSelTahasil49Val(dynamicValue.toString());
			try {
				dynamicValue = CommonUtil.getDynSingleData(entityManager,
						"select village_name from land_bank.village_master where village_code='"
								+ entity.getSelVillage50() + "'");
			} catch (Exception ex) {
				dynamicValue = "--";
			}
			entity.setSelVillage50Val(dynamicValue.toString());
			try {
				dynamicValue = CommonUtil.getDynSingleData(entityManager,
						"select khata_no from land_bank.khatian_information where khatian_code='"
								+ entity.getSelKhatian51() + "'");
			} catch (Exception ex) {
				dynamicValue = "--";
			}
			entity.setSelKhatian51Val(dynamicValue.toString());
			try {
				dynamicValue = CommonUtil.getDynSingleData(entityManager,
						"select plot_no from land_bank.plot_information where plot_code='" + entity.getSelPlot52()
								+ "'");
			} catch (Exception ex) {
				dynamicValue = "--";
			}
			entity.setSelPlot52Val(dynamicValue.toString());
			try {
				dynamicValue = CommonUtil.getDynSingleData(entityManager,
						"select meeting_level from m_meeting_level where meeting_level_id="
								+ entity.getSelMeetingLevel55());
			} catch (Exception ex) {
				dynamicValue = "--";
			}
			entity.setSelMeetingLevel55Val(dynamicValue.toString());

		}
		json.put("status", 200);
		json.put("result", new JSONArray(meeting_scheduleResp));
		json.put("count", totalDataPresent);
		return json;
	}

	@Override
	public JSONObject deleteById(Integer id, Integer updatedby) {
		logger.info("Inside deleteById method of Meeting_scheduleServiceImpl");
		try {
			meetingScheduleRepo.deleteRecord(id, updatedby);
			json.put("status", 200);
		} catch (Exception e) {
			logger.error("Inside deleteById method of Meeting_scheduleServiceImpl some error occur:" + e);
			json.put("status", 400);
		}
		return json;
	}

	public static JSONArray fillselDistrict48List(EntityManager em, String jsonVal) {
		logger.info("Inside fillselDistrict48List method of Meeting_scheduleServiceImpl");
		JSONArray mainJSONFile = new JSONArray();
		String query = "Select district_code,district_name from land_bank.district_master where state_code = '01' ";
		List<Object[]> dataList = CommonUtil.getDynResultList(em, query);
		for (Object[] data : dataList) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("district_code", data[0]);
			jsonObj.put("district_name", data[1]);
			mainJSONFile.put(jsonObj);
		}
		return mainJSONFile;
	}

	public static JSONArray fillselTahasil49List(EntityManager em, String jsonVal) {
		logger.info("Inside fillselTahasil49List method of Meeting_scheduleServiceImpl");
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

	public static JSONArray fillselVillage50List(EntityManager em, String jsonVal) {
		logger.info("Inside fillselVillage50List method of Meeting_scheduleServiceImpl");
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

	public static JSONArray fillselKhatian51List(EntityManager em, String jsonVal) {
		logger.info("Inside fillselKhatian51List method of Meeting_scheduleServiceImpl");
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

	public static JSONArray fillselPlot52List(EntityManager em, String jsonVal) {
		logger.info("Inside fillselPlot52List method of Meeting_scheduleServiceImpl");
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

	public static JSONArray fillselMeetingLevel55List(EntityManager em, String jsonVal) {
		logger.info("Inside fillselMeetingLevel55List method of Meeting_scheduleServiceImpl");
		JSONArray mainJSONFile = new JSONArray();
		String query = "Select meeting_level_id,meeting_level from m_meeting_level where status = '0' ";
		List<Object[]> dataList = CommonUtil.getDynResultList(em, query);
		for (Object[] data : dataList) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("meeting_level_id", data[0]);
			jsonObj.put("meeting_level", data[1]);
			mainJSONFile.put(jsonObj);
		}
		return mainJSONFile;
	}

}