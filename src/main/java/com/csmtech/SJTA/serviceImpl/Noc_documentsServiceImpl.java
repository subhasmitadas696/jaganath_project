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
import com.csmtech.SJTA.repository.Noc_documentsRepository;
import com.csmtech.SJTA.entity.Noc_documents;
import com.csmtech.SJTA.service.Noc_documentsService;

@Transactional
@Service
public class Noc_documentsServiceImpl implements Noc_documentsService {
	@Autowired
	private Noc_documentsRepository noc_documentsRepository;
	@Autowired
	EntityManager entityManager;

	Integer parentId = 0;
	Object dynamicValue = null;

	@Override
	public JSONObject save(String data) {
		JSONObject json = new JSONObject();
		try {
			ObjectMapper om = new ObjectMapper();
			Noc_documents noc_documents = om.readValue(data, Noc_documents.class);
			List<String> fileUploadList = new ArrayList<String>();
			fileUploadList.add(noc_documents.getFileHalPatta());
			fileUploadList.add(noc_documents.getFileSabikPatta());
			fileUploadList.add(noc_documents.getFileSabikHalComparisonStatement());
			fileUploadList.add(noc_documents.getFileSettlementYaddast());
			fileUploadList.add(noc_documents.getFileRegisteredDeed());
			if (!Objects.isNull(noc_documents.getIntId()) && noc_documents.getIntId() > 0) {
				Noc_documents getEntity = noc_documentsRepository.findByIntIdAndBitDeletedFlag(noc_documents.getIntId(),
						false);
				getEntity.setFileHalPatta(noc_documents.getFileHalPatta());
				getEntity.setFileSabikPatta(noc_documents.getFileSabikPatta());
				getEntity.setFileSabikHalComparisonStatement(noc_documents.getFileSabikHalComparisonStatement());
				getEntity.setFileSettlementYaddast(noc_documents.getFileSettlementYaddast());
				getEntity.setFileRegisteredDeed(noc_documents.getFileRegisteredDeed());
				Noc_documents updateData = noc_documentsRepository.save(getEntity);
				parentId = updateData.getIntId();
				json.put("status", 202);
			} else {
				Noc_documents saveData = noc_documentsRepository.save(noc_documents);
				parentId = saveData.getIntId();
				json.put("status", 200);
			}
			for (String fileUpload : fileUploadList) {
				if (!fileUpload.equals("")) {
					File f = new File("src/storage/tempfile/" + fileUpload);
					if (f.exists()) {
						File src = new File("src/storage/tempfile/" + fileUpload);
						File dest = new File("src/storage/upload-documents/" + fileUpload);
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
		Noc_documents entity = noc_documentsRepository.findByIntIdAndBitDeletedFlag(id, false);

		return new JSONObject(entity);
	}

	@Override
	public JSONArray getAll(String formParams) {
		JSONObject jsonData = new JSONObject(formParams);
		List<Noc_documents> noc_documentsResp = noc_documentsRepository.findAllByBitDeletedFlag(false);
		for (Noc_documents entity : noc_documentsResp) {

		}
		return new JSONArray(noc_documentsResp);
	}

	@Override
	public JSONObject deleteById(Integer id) {
		JSONObject json = new JSONObject();
		try {
			Noc_documents entity = noc_documentsRepository.findByIntIdAndBitDeletedFlag(id, false);
			entity.setBitDeletedFlag(true);
			noc_documentsRepository.save(entity);
			json.put("status", 200);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("status", 400);
		}
		return json;
	}

}