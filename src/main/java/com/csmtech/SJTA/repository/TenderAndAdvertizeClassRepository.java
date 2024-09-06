package com.csmtech.SJTA.repository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.csmtech.SJTA.dto.LandApplicantDTO;
import com.csmtech.SJTA.dto.TenderAdvertisementDTO;
import com.csmtech.SJTA.dto.TenderAndAdvertizeDTO;
import com.csmtech.SJTA.entity.TenderAndAdvertizeEntity;
import com.csmtech.SJTA.entity.User;

@Repository
public class TenderAndAdvertizeClassRepository {

	@Autowired
	@PersistenceContext
	private EntityManager entity;

	@Transactional
	public Integer saveRecord(Integer tenderTypeId, String title, String filepath, Date startDate, Date closeDate,
			Integer districtId, Integer tehsilId, Integer mouzaId, Integer khataNoId, Integer plotNoId,
			Integer createdBy) {
		String insertQuery = "INSERT INTO public.tender_advertisement "
				+ "(tender_type_id, title, upload_doc, start_date, close_date, district_id, tehsil_id, mouza_id, katha_no_id, plot_no_id,  created_by ) "
				+ "VALUES (:tenderTypeId, :title, :filepath, :startDate,"
				+ " :closedate, :districtId, :tehsilId, :mouzaId, :khataNoId, :plotNoId, :createdBy )";

		return entity.createNativeQuery(insertQuery).setParameter("tenderTypeId", tenderTypeId)
				.setParameter("title", title).setParameter("filepath", filepath).setParameter("startDate", startDate)
				.setParameter("closedate", closeDate).setParameter("districtId", districtId)
				.setParameter("tehsilId", tehsilId).setParameter("mouzaId", mouzaId)
				.setParameter("khataNoId", khataNoId).setParameter("plotNoId", plotNoId)
				.setParameter("createdBy", createdBy).executeUpdate();

	}

	@Transactional
	public Integer updateRecord(Integer tenderTypeId, String title, String filepath, Date startDate, Date closeDate,
			Integer districtId, Integer tehsilId, Integer mouzaId, Integer khataNoId, Integer plotNoId,
			Integer updatedBy, BigInteger tenderAdvertisementId) {
		String updateQuery = "UPDATE public.tender_advertisement SET "
				+ "tender_type_id = :tenderTypeId, title = :title, upload_doc = :filepath, start_date = :startDate, close_date = :closeDate, district_id = :districtId, tehsil_id = :tehsilId, "
				+ "mouza_id = :mouzaId, katha_no_id = :khataNoId, plot_no_id = :plotNoId, updated_by = :updatedBy "
				+ "WHERE tender_advertiesment_id = :tenderAdvertisementId";

		return entity.createNativeQuery(updateQuery).setParameter("tenderTypeId", tenderTypeId)
				.setParameter("title", title).setParameter("filepath", filepath).setParameter("startDate", startDate)
				.setParameter("closeDate", closeDate).setParameter("districtId", districtId)
				.setParameter("tehsilId", tehsilId).setParameter("mouzaId", mouzaId)
				.setParameter("khataNoId", khataNoId).setParameter("plotNoId", plotNoId)
				.setParameter("updatedBy", updatedBy).setParameter("tenderAdvertisementId", tenderAdvertisementId)
				.executeUpdate();

	}

	@Transactional
	public Integer deleteRecord(Integer createdBy, BigInteger tenderAdvertisementId) {
		entity.createNativeQuery("SET CONSTRAINTS ALL DEFERRED").executeUpdate();
		Date currentDateTime = new Date();
		Boolean status = true;
		return entity.createNativeQuery(
				"UPDATE public.tender_advertisement SET status = :setstatus, updated_by = :setupdatedby, updated_on = :setupdatedon WHERE tender_advertiesment_id = :giventenderid")
				.setParameter("setstatus", status).setParameter("setupdatedby", createdBy.longValue())
				.setParameter("setupdatedon", currentDateTime).setParameter("giventenderid", tenderAdvertisementId)
				.executeUpdate();
	}

	@Transactional
	public List<TenderAdvertisementDTO> findAllByTitle(String title) {
		String query = "SELECT a.tender_advertiesment_id, t.tender_type, a.title, a.start_date, a.letter_no \r\n"
				+ "FROM tender_advertisement a JOIN m_tender_type t ON a.tender_type_id = t.tender_type_id\r\n"
				+ "where a.status = false ";

		if (title != null) {
			query = query + " and a.title ILIKE :title \r\n";
		}

		@SuppressWarnings("unchecked")
		List<Object[]> resultList = entity.createNativeQuery(query).setParameter("title", "%" + title + "%")
				.getResultList();

		return transformResultList(resultList);

	}

	private List<TenderAdvertisementDTO> transformResultList(List<Object[]> resultList) {
		List<TenderAdvertisementDTO> roleInfoList = new ArrayList<>();
		for (Object[] row : resultList) {
			BigInteger tenderAdvertisementId = (BigInteger) row[0];
			String tenderType = (String) row[1];
			String title = (String) row[2];
			Date startDate = (Date) row[3];
			String letterNo = (String) row[4];

			TenderAdvertisementDTO roleInfo = new TenderAdvertisementDTO();
			roleInfo.setTenderAdvertisementId(tenderAdvertisementId);
			roleInfo.setTenderType(tenderType);
			roleInfo.setTitle(title);
			roleInfo.setStartDate(startDate);
			roleInfo.setLetterNo(letterNo);

			roleInfoList.add(roleInfo);

		}

		return roleInfoList;
	}

	public TenderAndAdvertizeEntity findByTenderAdvertisementId(BigInteger tenderAdvertisementId) {

		String query = "SELECT t.tender_type,  a.title, a.start_date, a.close_date, d.district_name,te.tahasil_name,mo.village_name,ka.khata_no,pl.plot_no, a.upload_doc,a.tender_type_id, a.district_code , a.tahasil_code, a.village_code, a.khatian_code, a.plot_code, a.letter_no  \r\n"
				+ "FROM tender_advertisement a JOIN m_tender_type t ON a.tender_type_id = t.tender_type_id\r\n"
				+ "LEFT JOIN land_bank.district_master d USING(district_code)\r\n"
				+ "LEFT JOIN land_bank.tahasil_master te USING(tahasil_code)\r\n"
				+ "LEFT JOIN land_bank.village_master mo USING(village_code)\r\n"
				+ "LEFT JOIN land_bank.khatian_information ka USING(khatian_code)\r\n"
				+ "LEFT JOIN land_bank.plot_information pl USING(plot_code)\r\n"
				+ "WHERE a.tender_advertiesment_id= :tenderAdvertisementId \r\n";
		try {
			Object[] result = (Object[]) entity.createNativeQuery(query)
					.setParameter("tenderAdvertisementId", tenderAdvertisementId).getSingleResult();

			TenderAndAdvertizeEntity tender = new TenderAndAdvertizeEntity();
			tender.setTenderTypeVal((String) result[0]);
			tender.setTxtTitle((String) result[1]);
			tender.setTxtStartDate((Date) result[2]);
			tender.setTxtCloseDate((Date) result[3]);
			tender.setDistrictVal((String) result[4]);
			tender.setTehsilVal((String) result[5]);
			tender.setMouzaVal((String) result[6]);
			tender.setKathaNoVal((String) result[7]);
			tender.setPlotNoVal((String) result[8]);
			tender.setFileUploadTenderDocument((String) result[9]);
			tender.setSelTenderType((Short) result[10]);
			tender.setSelDistrict((String) result[11]);
			tender.setSelTehsil((String) result[12]);
			tender.setSelMouza((String) result[13]);
			tender.setSelKhataNo((String) result[14]);
			tender.setSelPlotNo((String) result[15]);
			tender.setLetterNo((String) result[16]);

			return tender;
		} catch (NoResultException e) {
			return null;
		}
	}

	@Transactional
	public List<TenderAdvertisementDTO> findAllByTitleStatusFalse(String title, Date startDate) {
		StringBuilder queryBuilder = new StringBuilder(
				"SELECT a.tender_advertiesment_id, a.title, a.start_date, a.close_date, a.letter_no, a.upload_doc \r\n"
						+ "FROM tender_advertisement a where a.status = false ");

		if (title != null) {
			queryBuilder.append(" AND a.title ILIKE :title");
		}

		if (startDate != null) {
			queryBuilder.append(" AND a.start_date = :startDate");
		}

		queryBuilder.append(" ORDER BY close_date");

		Query query = entity.createNativeQuery(queryBuilder.toString());

		if (title != null) {
			query.setParameter("title", "%" + title + "%");
		}

		if (startDate != null) {
			query.setParameter("startDate", startDate);
		}

		@SuppressWarnings("unchecked")
		List<Object[]> resultList = query.getResultList();

		return transformToResultList(resultList);
	}

	private List<TenderAdvertisementDTO> transformToResultList(List<Object[]> resultList) {
		List<TenderAdvertisementDTO> roleInfoList = new ArrayList<>();
		for (Object[] row : resultList) {
			BigInteger tenderAdvertisementId = (BigInteger) row[0];
			String title = (String) row[1];
			Date startDate = (Date) row[2];
			Date closeDate = (Date) row[3];
			String letterNo = (String) row[4];
			String uploadDoc = (String) row[5];

			TenderAdvertisementDTO roleInfo = new TenderAdvertisementDTO();
			roleInfo.setTenderAdvertisementId(tenderAdvertisementId);
			roleInfo.setTitle(title);
			roleInfo.setStartDate(startDate);
			roleInfo.setCloseDate(closeDate);
			roleInfo.setLetterNo(letterNo);
			roleInfo.setUploadDoc(uploadDoc);

			roleInfoList.add(roleInfo);

		}

		return roleInfoList;
	}

}
