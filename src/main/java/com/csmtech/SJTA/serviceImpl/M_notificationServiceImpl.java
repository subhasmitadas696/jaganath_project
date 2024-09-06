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
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import com.csmtech.SJTA.repository.M_notificationRepository;
import com.csmtech.SJTA.repository.NotificationsNativeRepository;
import com.csmtech.SJTA.entity.M_notification;
import com.csmtech.SJTA.service.M_notificationService;

@Transactional
@Service
public class M_notificationServiceImpl implements M_notificationService {
	@Autowired
	private M_notificationRepository m_notificationRepository;

	@Autowired
	private NotificationsNativeRepository notiRepo;

	@Autowired
	EntityManager entityManager;

	@Value("${file.path}")
	private String filePathloc;

	Integer parentId = 0;
	Object dynamicValue = null;

	@Override
	public JSONObject save(String data) {
		JSONObject json = new JSONObject();
		try {
			ObjectMapper om = new ObjectMapper();
			M_notification m_notification = om.readValue(data, M_notification.class);
			List<String> fileUploadList = new ArrayList<String>();
			fileUploadList.add(m_notification.getFileUploadDocument());
			if (!Objects.isNull(m_notification.getIntId()) && m_notification.getIntId() > 0) {
//				M_notification getEntity = m_notificationRepository.findByIntId(m_notification.getIntId());
				M_notification updateData = notiRepo.updateRecord(m_notification);
				parentId = updateData.getIntId();
				json.put("status", 202);
			} else {
				Integer saveData = notiRepo.saveRecord(m_notification);
				parentId = saveData;
				json.put("status", 200);
			}
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
		M_notification entity = m_notificationRepository.findByIntId(id);
//		entity.setFileUploadDocument(filePathloc + "/" + entity.getFileUploadDocument());
		return new JSONObject(entity);
	}

	@Override
	public JSONArray getAll(String formParams) {
		JSONObject jsonData = new JSONObject(formParams);
		Integer offset = jsonData.getInt("pageSize") * (jsonData.getInt("pageNo") - 1);
		List<M_notification> m_notificationResp = m_notificationRepository
				.findAllByBitDeletedFlagWithPagination(jsonData.getInt("pageSize"), offset);

		return new JSONArray(m_notificationResp);
	}

	@Override
	public JSONObject deleteById(Integer id, Integer updatedby) {
		JSONObject json = new JSONObject();
		try {
			notiRepo.deleteById(id, updatedby);
			json.put("status", 200);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("status", 400);
		}
		return json;
	}

	@Override
	public Integer getTotalAppCount() {
		// TODO Auto-generated method stub
		return m_notificationRepository.getCount();
	}

}