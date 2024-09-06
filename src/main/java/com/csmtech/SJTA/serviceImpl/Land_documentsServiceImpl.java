package com.csmtech.SJTA.serviceImpl;

import java.io.File;
import java.io.IOException;
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

import com.csmtech.SJTA.dto.addMoreUploadDocumentsDTO;
import com.csmtech.SJTA.entity.Land_documents;
import com.csmtech.SJTA.repository.LandApplicantNativeRepository;
import com.csmtech.SJTA.repository.Land_documentsRepository;
import com.csmtech.SJTA.repository.PaginationInRegisterClassRepository;
import com.csmtech.SJTA.service.Land_documentsService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Transactional
@Service
public class Land_documentsServiceImpl implements Land_documentsService {
	@Autowired
	private Land_documentsRepository land_documentsRepository;
	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private LandApplicantNativeRepository repoapplicant;

	@Autowired
	private PaginationInRegisterClassRepository lastrepo;

	Integer parentId = 0;
	Object dynamicValue = null;

	@Override
	public JSONObject save(String data) {
		JSONObject json = new JSONObject();
		try {
			ObjectMapper om = new ObjectMapper();
			Land_documents land_documents = om.readValue(data, Land_documents.class);
			List<String> fileUploadList = new ArrayList<String>();
			if (!Objects.isNull(land_documents.getIntParentId()) && land_documents.getIntParentId() > 0) {
//				Land_documents getEntity = land_documentsRepository
//						.findByIntIdAndBitDeletedFlag(land_documents.getIntId(), false);
//				Land_documents updateData = land_documentsRepository.save(getEntity);
//				parentId = updateData.getIntId();
				json.put("status", 202);
				//land_documentsRepository.deleteAllByIntParentId(land_documents.getIntParentId());
			} else {
//				Land_documents saveData = land_documentsRepository.save(land_documents);
//				parentId = saveData.getIntId();
//				json.put("status", 200);
			}

//			Land_documents getEntity = land_documentsRepository
//					.findByIntIdAndBitDeletedFlag(land_documents.getIntId(), false);
//			List<Land_documents> land_documentsList = land_documents.getAddMoreUploadDocuments();
//			// int createdBy = getByIntParentId(intParentId);
//			land_documentsList.forEach(t -> {
//				t.setIntCreatedBy(land_documents.getIntCreatedBy());
//				t.setIntParentId(land_documents.getIntParentId());
//				fileUploadList.add(t.getAmfileDocument());
//			});
			// generate no
//			LandApplicantQueryLastReturnDTO getrecord=lastrepo.findLatestLandApplicantByMobileNo();
//			System.out.println(getrecord.toString());

			//land_documentsRepository.saveAll(land_documentsList);
//			Integer count=repoapplicant.insertLandDocument(land_documents.getAddMoreUploadDocuments().get(0).getAmfileDocument(), 
//					land_documents.getAddMoreUploadDocuments().get(0).getAmtxtDocumentName(), land_documents.getIntCreatedBy(), land_documents.getIntParentId());
			
			List<addMoreUploadDocumentsDTO> documentList = land_documents.getAddMoreUploadDocuments();

			for (addMoreUploadDocumentsDTO document : documentList) {
			    Integer count = repoapplicant.insertLandDocument(
			        document.getAmfileDocument(),
			        document.getAmtxtDocumentName(),
			        land_documents.getIntCreatedBy(),
			        land_documents.getIntParentId()
			    );
			    System.out.println(count);
			}

			json.put("status", 200);
			for (String fileUpload : fileUploadList) {
				if (!fileUpload.equals("")) {
					File f = new File("src/storage/tempfile/" + fileUpload);
					if (f.exists()) {
						File src = new File("src/storage/tempfile/" + fileUpload);
						File dest = new File("D:/docpdf/" + fileUpload);
						try {
							Files.copy(src.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
							Files.delete(src.toPath());
						} catch (IOException e) {
							System.out.println("Iniside Error");
						}
					}
				}
			}
			// }
		} catch (Exception e) {
			e.printStackTrace();
			json.put("status", 400);
		}
		return json;
	}

	@Override
	public JSONObject getById(Integer id) {
		Land_documents entity = land_documentsRepository.findByIntIdAndBitDeletedFlag(id, false);
		List<Land_documents> land_documentsList = land_documentsRepository
				.findByIntParentIdAndBitDeletedFlag(entity.getIntId());
	//	entity.setAddMoreUploadDocuments(land_documentsList);

		return new JSONObject(entity);
	}

	@Override
	public JSONArray getAll(String formParams) {
		JSONObject jsonData = new JSONObject(formParams);
		List<Land_documents> land_documentsResp = land_documentsRepository.findAllByBitDeletedFlag(false);
		for (Land_documents entity : land_documentsResp) {

		}
		return new JSONArray(land_documentsResp);
	}

	@Override
	public JSONObject deleteById(Integer id) {
		JSONObject json = new JSONObject();
		try {
			Land_documents entity = land_documentsRepository.findByIntIdAndBitDeletedFlag(id, false);
			entity.setBitDeletedFlag(true);
			land_documentsRepository.save(entity);
			List<Land_documents> land_documentsList = land_documentsRepository.findByIntParentIdAndBitDeletedFlag(id
					);
			land_documentsList.forEach(t -> t.setBitDeletedFlag(true));
			land_documentsRepository.saveAll(land_documentsList);
			json.put("status", 200);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("status", 400);
		}
		return json;
	}

	@Override
	public JSONObject getByIntParentId(Integer intParentId) {

			System.out.println(intParentId);

			JSONObject jsonobject = new JSONObject();
			List<Land_documents> entity = land_documentsRepository.getByparentIdd(intParentId);
			System.out.println(entity);
			if (entity != null) {
				List<Land_documents> land_documentsList = land_documentsRepository
						.findByIntParentIdAndBitDeletedFlag(intParentId);
				
				jsonobject.put("intId", intParentId);
				jsonobject.put("addMoreUploadDocuments", land_documentsList);
			}
			System.out.println("json=" + jsonobject);
			return jsonobject;
		}

}