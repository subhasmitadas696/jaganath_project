package com.csmtech.SJTA.serviceImpl;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csmtech.SJTA.entity.Land_applicant;
import com.csmtech.SJTA.entity.Land_plot;
import com.csmtech.SJTA.repository.Land_applicantRepository;
import com.csmtech.SJTA.repository.Land_plotRepository;
import com.csmtech.SJTA.service.Land_plotService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Transactional
@Service
public class Land_plotServiceImpl implements Land_plotService {
	@Autowired
	private Land_plotRepository land_plotRepository;
	@Autowired
	private Land_applicantRepository Land_applicantRepository;
	@Autowired
	EntityManager entityManager;

	Integer parentId ;
	Object dynamicValue = null;

	@Override
	public JSONObject save(String data) {
		JSONObject json = new JSONObject();
		try {
			ObjectMapper om = new ObjectMapper();
			Land_plot land_plot = om.readValue(data, Land_plot.class);
//			List<String> fileUploadList = new ArrayList<String>();
//			fileUploadList.add(land_plot.getFileDocumentaryProofofOccupancyifany());
			Land_plot getEntity = land_plotRepository
					.findByIntLandApplicantIdAndBitDeletedFlag(land_plot.getIntLandApplicantId(), false);
			Integer plot=land_plot.getIntLandApplicantId();
			
			if (!Objects.isNull(land_plot.getIntLandApplicantId()) && 
//					land_plot.getIntLandApplicantId() > 0 
					plot.compareTo(plot) > 0
					&& getEntity != null) {
				
//				getEntity.setSelDistrictName(land_app.getSelDistrictName());
//				getEntity.setSelTehsilName(land_app.getSelTehsilName());
//				getEntity.setSelMouza(land_app.getSelMouza());
//				getEntity.setSelKhataNo(land_app.getSelKhataNo());
				getEntity.setSelPlotNo(land_plot.getSelPlotNo());
				getEntity.setTxtTotalRakba(land_plot.getTxtTotalRakba());
				getEntity.setTxtPurchaseRakba(land_plot.getTxtPurchaseRakba());
				//getEntity.setSelVarieties(land_plot.getSelVarieties());
				// getEntity.setFileDocumentaryProofofOccupancyifany(land_plot.getFileDocumentaryProofofOccupancyifany());
				// getEntity.setTxtFixedPriceperAcreofPurchasedLand(land_plot.getTxtFixedPriceperAcreofPurchasedLand());
				// getEntity.setTxtTotalCostofLandPurchased(land_plot.getTxtTotalCostofLandPurchased());
				// getEntity.setTxtOthers(land_plot.getTxtOthers());
				Land_plot updateData = land_plotRepository.save(getEntity);
				parentId = updateData.getIntLandApplicantId();
				json.put("status", 202);
			} else {
				Land_plot saveData = land_plotRepository.save(land_plot);
				parentId = saveData.getIntLandApplicantId();
				json.put("status", 200);
			}
//			for (String fileUpload : fileUploadList) {
//				if (!fileUpload.equals("")) {
//					File f = new File("src/storage/tempfile/" + fileUpload);
//					if (f.exists()) {
//						File src = new File("src/storage/tempfile/" + fileUpload);
//						File dest = new File("src/storage/plot-details/" + fileUpload);
//						try {
//							Files.copy(src.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
//							Files.delete(src.toPath());
//						} catch (IOException e) {
//							System.out.println("Iniside Error");
//						}
//					}
//				}
//			}
		} catch (Exception e) {
			e.printStackTrace();
			json.put("status", 400);
		}
		return json;
	}

	@Override
	public JSONObject getById(Integer id) {
		Land_plot entity = land_plotRepository.findByIntIdAndBitDeletedFlag(id, false);
		Land_applicant entity1 = Land_applicantRepository.findByIntId(id);
		try {
			dynamicValue = entityManager
					.createNativeQuery(
							"select district_name from land_bank.district_master where district_code=" + entity1.getSelDistrictName())
					.getSingleResult();
		} catch (Exception ex) {
			dynamicValue = "--";
		}
		entity1.setSelDistrictNameVal(dynamicValue.toString());
		try {
			dynamicValue = entityManager
					.createNativeQuery("select tahasil_name from land_bank.tahasil_master where tahasil_code=" + entity1.getSelTehsilName())
					.getSingleResult();
		} catch (Exception ex) {
			dynamicValue = "--";
		}
		entity1.setSelTehsilNameVal(dynamicValue.toString());
		try {
			dynamicValue = entityManager
					.createNativeQuery("select village_name from land_bank.village_master where village_code=" + entity1.getSelMouza())
					.getSingleResult();
		} catch (Exception ex) {
			dynamicValue = "--";
		}
		entity1.setSelMouzaVal(dynamicValue.toString());
		try {
			dynamicValue = entityManager
					.createNativeQuery("select khata_no from land_bank.khatian_information where khatian_code=" + entity1.getSelKhataNo())
					.getSingleResult();
		} catch (Exception ex) {
			dynamicValue = "--";
		}
		entity1.setSelKhataNoVal(dynamicValue.toString());
		try {
			dynamicValue = entityManager
					.createNativeQuery("select plot_no from land_bank.plot_information where plot_code=" + entity.getSelPlotNo())
					.getSingleResult();
		} catch (Exception ex) {
			dynamicValue = "--";
		}
		entity.setSelPlotNoVal(dynamicValue.toString());
//		try {
//			dynamicValue = entityManager
//					.createNativeQuery(
//							"select varieties_name from m_varieties where varieties_id=" + entity.getSelVarieties())
//					.getSingleResult();
//		} catch (Exception ex) {
//			dynamicValue = "--";
//		}
//		entity.setSelVarietiesVal(dynamicValue.toString());

		return new JSONObject(entity);
	}

	@Override
	public JSONArray getAll(String formParams) {
		JSONObject jsonData = new JSONObject(formParams);
		List<Land_plot> land_plotResp = land_plotRepository.findAllByBitDeletedFlag();

		
		for (Land_plot entity : land_plotResp) {
			Land_applicant entity1 = Land_applicantRepository.findByIntId(entity.getIntLandApplicantId());
			try {
				dynamicValue = entityManager
						.createNativeQuery(
								"select district_name from land_bank.district_master where district_code=" + entity1.getSelDistrictName())
						.getSingleResult();
			} catch (Exception ex) {
				dynamicValue = "--";
			}
			entity1.setSelDistrictNameVal(dynamicValue.toString());
			try {
				dynamicValue = entityManager
						.createNativeQuery(
								"select tahasil_name from land_bank.tahasil_master where tahasil_code=" + entity1.getSelTehsilName())
						.getSingleResult();
			} catch (Exception ex) {
				dynamicValue = "--";
			}
			entity1.setSelTehsilNameVal(dynamicValue.toString());
			try {
				dynamicValue = entityManager
						.createNativeQuery("select village_name from land_bank.village_master where village_code=" + entity1.getSelMouza())
						.getSingleResult();
			} catch (Exception ex) {
				dynamicValue = "--";
			}
			entity1.setSelMouzaVal(dynamicValue.toString());
			try {
				dynamicValue = entityManager
						.createNativeQuery(
								"select khata_no from land_bank.khatian_information where khatian_code=" + entity1.getSelKhataNo())
						.getSingleResult();
			} catch (Exception ex) {
				dynamicValue = "--";
			}
			entity1.setSelKhataNoVal(dynamicValue.toString());
			try {
				dynamicValue = entityManager
						.createNativeQuery("select plot_no from land_bank.plot_information where plot_code=" + entity.getSelPlotNo())
						.getSingleResult();
			} catch (Exception ex) {
				dynamicValue = "--";
			}
			entity.setSelPlotNoVal(dynamicValue.toString());
//			try {
//				dynamicValue = entityManager
//						.createNativeQuery(
//								"select varieties_name from m_varieties where varieties_id=" + entity.getSelVarieties())
//						.getSingleResult();
//			} catch (Exception ex) {
//				dynamicValue = "--";
//			}
//			entity.setSelVarietiesVal(dynamicValue.toString());
//
		}
		return new JSONArray(land_plotResp);
	}

	@Override
	public JSONObject deleteById(Integer id) {
		JSONObject json = new JSONObject();
		try {
			Land_plot entity = land_plotRepository.findByIntIdAndBitDeletedFlag(id, false);
			entity.setBitDeletedFlag(true);
			land_plotRepository.save(entity);
			json.put("status", 200);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("status", 400);
		}
		return json;
	}

	public static JSONArray fillselDistrictNameList(EntityManager em, String jsonVal) {
		JSONArray mainJSONFile = new JSONArray();
//		String query = "Select district_id,district_name from m_district where deleted_flag = '0' and state_id = 1";
		String query = "select (district_code) as district_id,(district_name)  as district_name  from land_bank.district_master WHERE state_code='01' ";
		List<Object[]> dataList = em.createNativeQuery(query).getResultList();
		for (Object[] data : dataList) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("district_id", data[0]);
			jsonObj.put("district_name", data[1]);
			mainJSONFile.put(jsonObj);
		}
		return mainJSONFile;
	}

	public static JSONArray fillselTehsilNameList(EntityManager em, String jsonVal) {
		JSONArray mainJSONFile = new JSONArray();
		JSONObject jsonDepend = new JSONObject(jsonVal);
		String val =jsonDepend.get("district_id").toString();
		String query = "select (tahasil_code) as tehsil_id,(tahasil_name) as tehsil_name from land_bank.tahasil_master where  district_code='" + val +"'";
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
		String query = "Select (village_code) as mouza_id,(village_name) as mouza_name "
				+ " from land_bank.village_master where  tahasil_code='" + val+"'";
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
		String query = "Select (khatian_code) as khata_no_id,(khata_no) as khata_no "
				+ " from land_bank.khatian_information where village_code='" + val+"'";
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
		String query = "Select (plot_code) as  plot_no_id,(plot_no) as plot_no, kissam "
				+ " from land_bank.plot_information where khatian_code='" + val+"'";
		List<Object[]> dataList = em.createNativeQuery(query).getResultList();
		for (Object[] data : dataList) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("plot_code", data[0]);
			jsonObj.put("plot_no", data[1]);
			jsonObj.put("kissam", data[2]);
			mainJSONFile.put(jsonObj);
		}
		return mainJSONFile;
	}


	public static JSONArray fillselVarietiesList(EntityManager em, String jsonVal) {
		JSONArray mainJSONFile = new JSONArray();
		String query = "Select (plot_code) as varieties_id,(plot_no) as varieties_name  from  land_bank.plot_information";
		List<Object[]> dataList = em.createNativeQuery(query).getResultList();
		for (Object[] data : dataList) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("varieties_id", data[0]);
			jsonObj.put("varieties_name", data[1]);
			mainJSONFile.put(jsonObj);
		}
		return mainJSONFile;
	}


	@Override
	public JSONObject getByIntLandApplicantId(Integer IntLandApplicantId) {
		Land_plot entity = land_plotRepository.findByIntLandApplicantIdAndBitDeletedFlag(IntLandApplicantId, false);
		Land_applicant entity1 = Land_applicantRepository.findByIntId(IntLandApplicantId);
		JSONObject jsonObj = new JSONObject();
		if (entity != null) {
			try {
				dynamicValue = entityManager
						.createNativeQuery(
								"select district_name from land_bank.district_master where district_code=" + entity1.getSelDistrictName())
						.getSingleResult();
			} catch (Exception ex) {
				dynamicValue = "--";
			}
			entity1.setSelDistrictNameVal(dynamicValue.toString());
			try {
				dynamicValue = entityManager
						.createNativeQuery(
								"select tahasil_name from land_bank.tahasil_master where tahasil_code=" + entity1.getSelTehsilName())
						.getSingleResult();
			} catch (Exception ex) {
				dynamicValue = "--";
			}
			entity1.setSelTehsilNameVal(dynamicValue.toString());
			try {
				dynamicValue = entityManager
						.createNativeQuery("select village_name from land_bank.village_master where village_code=" + entity1.getSelMouza())
						.getSingleResult();
			} catch (Exception ex) {
				dynamicValue = "--";
			}
			entity1.setSelMouzaVal(dynamicValue.toString());
			try {
				dynamicValue = entityManager
						.createNativeQuery(
								"select khata_no from land_bank.khatian_information where khatian_code=" + entity1.getSelKhataNo())
						.getSingleResult();
			} catch (Exception ex) {
				dynamicValue = "--";
			}
			entity1.setSelKhataNoVal(dynamicValue.toString());
			try {
				dynamicValue = entityManager
						.createNativeQuery("select plot_no from land_bank.plot_information where plot_code=" + entity.getSelPlotNo())
						.getSingleResult();
			} catch (Exception ex) {
				dynamicValue = "--";
			}
			entity.setSelPlotNoVal(dynamicValue.toString());
//			try {
//				dynamicValue = entityManager
//						.createNativeQuery(
//								"select varieties_name from m_varieties where varieties_id=" + entity.getSelVarieties())
//						.getSingleResult();
//			} catch (Exception ex) {
//				dynamicValue = "--";
//			}
//			entity.setSelVarietiesVal(dynamicValue.toString());
			jsonObj.put("data", new JSONObject(entity));
		} else {
			jsonObj.put("data", "");
		}

		return jsonObj;

	}

	@Override
	public JSONObject deleteByIntLandApplicantId(Integer IntLandApplicantId) {

		JSONObject json = new JSONObject();
		try {
			Land_plot entity = land_plotRepository.deleteByIntLandApplicantIdAndBitDeletedFlag(IntLandApplicantId,
					false);
			entity.setBitDeletedFlag(true);
			land_plotRepository.save(entity);
			json.put("status", 200);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("status", 400);
		}
		return json;
	}

}
