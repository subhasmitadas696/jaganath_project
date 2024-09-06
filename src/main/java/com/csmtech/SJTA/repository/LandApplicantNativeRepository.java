package com.csmtech.SJTA.repository;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.csmtech.SJTA.dto.LandAppResponseStructureDTO;
import com.csmtech.SJTA.dto.LandAppViewDocumentDTO;
import com.csmtech.SJTA.dto.LandApplicantDTO;
import com.csmtech.SJTA.dto.LandApplicantViewDTO;
import com.csmtech.SJTA.dto.LandPlotViewDTO;
import com.csmtech.SJTA.entity.Land_applicant;
//import com.itextpdf.text.Document;
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.Paragraph;
//import com.itextpdf.text.pdf.PdfWriter;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class LandApplicantNativeRepository {

	@PersistenceContext
	EntityManager entityManager;

	@Value("${file.path}")
	private String filePathloc;

	@Transactional
	public List<LandApplicantDTO> getLandApplicantDetailsPage(BigInteger roleId, int pageNumber, int pageSize) {

		String query = " SELECT "
				+ "        la.land_application_id, "
				+ "        la.application_no, "
				+ "        la.applicant_name, "
				+ "        la.mobile_no, "
				+ "        d.district_name, "
				+ "        t.tahasil_name, "
				+ "        m.village_name, "
				+ "        k.khata_no  "
				+ "     FROM "
				+ "        public.land_application la  "
				+ "    INNER JOIN "
				+ "        land_bank.district_master d  "
				+ "            ON la.district_code = d.district_code  "
				+ "    INNER JOIN "
				+ "       land_bank.tahasil_master t  "
				+ "            ON la.tehsil_code = t.tahasil_code  "
				+ "    INNER JOIN "
				+ "       land_bank.village_master m  "
				+ "            ON la.village_code = m.village_code  "
				+ "    INNER JOIN "
				+ "        land_bank.khatian_information k  "
				+ "            ON la.khatian_code = k.khatian_code  "
				+ "    INNER JOIN "
				+ "        land_application_approval a  "
				+ "            ON la.land_application_id = a.land_application_id  "
				+ "    WHERE "
				+ "        a.pending_at_role_id=:roleId  "
				+ "        AND la.deleted_flag =  '0'    "
				+ "ORDER BY la.land_application_id ASC LIMIT :pageSize OFFSET :offset ";

		int offset = (pageNumber - 1) * pageSize;

		@SuppressWarnings("unchecked")
		List<Object[]> resultList = entityManager.createNativeQuery(query).setParameter("roleId", roleId.longValue())
				.setParameter("pageSize", pageSize).setParameter("offset", offset).getResultList();

		return transformResultList(resultList);
	}

	private List<LandApplicantDTO> transformResultList(List<Object[]> resultList) {
		List<LandApplicantDTO> roleInfoList = new ArrayList<>();
		for (Object[] row : resultList) {
			BigInteger landApplicantId = (BigInteger) row[0];

			String applicantNo = (String) row[1];
			String applicantName = (String) row[2];
			String mobileNo = (String) row[3];
			String districtName = (String) row[4];
			String tehsilName = (String) row[5];
			String mouzaName = (String) row[6];
			String khataNo = (String) row[7];

			LandApplicantDTO roleInfo = new LandApplicantDTO();
			roleInfo.setLandApplicantId(landApplicantId);
			roleInfo.setApplicantNo(applicantNo);
			roleInfo.setApplicantName(applicantName);
			roleInfo.setMobileNo(mobileNo);
			roleInfo.setDistrictName(districtName);
			roleInfo.setTehsilName(tehsilName);
			roleInfo.setMouzaName(mouzaName);
			roleInfo.setKhataNo(khataNo);

			roleInfo.setLandApplicantId(landApplicantId);

			roleInfoList.add(roleInfo);

		}

		return roleInfoList;
	}

	//get count all the tab record ---------------------------------------------------// -------------------------------------------------------------------------------
	
	@Transactional
	public BigInteger getTotalApplicantCount(BigInteger roleId, String applicantName,
	         String applicantUniqueId, String plotKathaNO) {
		 String countQuery = "SELECT "
			 		+ "        count(*)  "
			 		+ "    FROM "
			 		+ "        public.land_application la  "
			 		+ "    INNER JOIN "
			 		+ "        land_bank.district_master d  "
			 		+ "            ON la.district_code = d.district_code  "
			 		+ "    INNER JOIN "
			 		+ "       land_bank.tahasil_master t  "
			 		+ "            ON la.tehsil_code = t.tahasil_code  "
			 		+ "    INNER JOIN "
			 		+ "       land_bank.village_master m  "
			 		+ "            ON la.village_code = m.village_code  "
			 		+ "    INNER JOIN "
			 		+ "        land_bank.khatian_information k  "
			 		+ "            ON la.khatian_code = k.khatian_code  "
			 		+ "    INNER JOIN "
			 		+ "        land_application_approval a  "
			 		+ "            ON la.land_application_id = a.land_application_id  "
			 		+ "    WHERE "
			 		+ "        a.pending_at_role_id=:roleId  "
			 		+ "        AND la.deleted_flag =  '0' ";
		
		//:roleId
		
		 return  (BigInteger) entityManager.createNativeQuery(countQuery)
		            .setParameter("roleId", roleId)
		            .getSingleResult();
		 
	}
	
	
	@Transactional
	public BigInteger getTotalApplicantCountPagination(BigInteger roleId, String applicantName,
	         String applicantUniqueId, String plotKathaNO) {
		String countQuery = "SELECT "
		 		+ "        count(*)  "
		 		+ "   FROM "
		 		+ "        public.land_application la  "
		 		+ "    INNER JOIN "
		 		+ "        land_bank.district_master d  "
		 		+ "            ON la.district_code = d.district_code  "
		 		+ "    INNER JOIN "
		 		+ "       land_bank.tahasil_master t  "
		 		+ "            ON la.tehsil_code = t.tahasil_code  "
		 		+ "    INNER JOIN "
		 		+ "       land_bank.village_master m  "
		 		+ "            ON la.village_code = m.village_code  "
		 		+ "    INNER JOIN "
		 		+ "        land_bank.khatian_information k  "
		 		+ "            ON la.khatian_code = k.khatian_code  "
		 		+ "    INNER JOIN "
		 		+ "        land_application_approval a  "
		 		+ "            ON la.land_application_id = a.land_application_id "
		            + "WHERE a.pending_at_role_id=:roleId AND "
					+ "  la.applicant_name ILIKE :applicantName  "
					+ "   AND la.application_no ILIKE :applicantUniqueId  "
					+ "   AND k.Khata_no ILIKE :plotKathaNO "
					+ "  AND la.deleted_flag = '0'  "
					+ " ";
		
		
		
		 return  (BigInteger) entityManager.createNativeQuery(countQuery)
		            .setParameter("roleId", roleId)
		            .setParameter("applicantName", "%" + applicantName + "%")
					.setParameter("applicantUniqueId", "%" + applicantUniqueId + "%")
					.setParameter("plotKathaNO", "%" + plotKathaNO + "%")
		            .getSingleResult();
		 
	}

	//get count all the tab record ---------------------------------------------------// -------------------------------------------------------------------------------
	
	@Transactional
	public List<LandApplicantDTO> getSearchLandApplicantDetailsPage(BigInteger roleId, String applicantName,
			int pageNumber, int pageSize,String applicantUniqueId,String plotKathaNO) {
		System.out.println(applicantName);
		String query = "SELECT "
				+ "        la.land_application_id, "
				+ "        la.application_no, "
				+ "        la.applicant_name, "
				+ "        la.mobile_no, "
				+ "        d.district_name, "
				+ "        t.tahasil_name, "
				+ "        m.village_name, "
				+ "        k.khata_no   "
				+ "      FROM "
				+ "        public.land_application la  "
				+ "    INNER JOIN "
				+ "        land_bank.district_master d  "
				+ "            ON la.district_code = d.district_code  "
				+ "    INNER JOIN "
				+ "       land_bank.tahasil_master t  "
				+ "            ON la.tehsil_code = t.tahasil_code  "
				+ "    INNER JOIN "
				+ "       land_bank.village_master m  "
				+ "            ON la.village_code = m.village_code  "
				+ "    INNER JOIN "
				+ "        land_bank.khatian_information k  "
				+ "            ON la.khatian_code = k.khatian_code  "
				+ "    INNER JOIN "
				+ "        land_application_approval a  "
				+ "            ON la.land_application_id = a.land_application_id "
				+ "   WHERE a.pending_at_role_id=:roleId AND "
				+ "  la.applicant_name ILIKE :applicantName  "
				+ "   AND la.application_no ILIKE :applicantUniqueId  "
				+ "   AND k.Khata_no ILIKE :plotKathaNO "
				+ "  AND la.deleted_flag = '0' "
				+ "ORDER BY la.land_application_id ASC " 
				+ "LIMIT :pageSize OFFSET :offset ";
		int offset = (pageNumber - 1) * pageSize;

		@SuppressWarnings("unchecked")
		List<Object[]> resultList = entityManager.createNativeQuery(query).setParameter("roleId", roleId)
				.setParameter("applicantName", "%" + applicantName + "%")
				.setParameter("applicantUniqueId", "%" + applicantUniqueId + "%")
				.setParameter("plotKathaNO", "%" + plotKathaNO + "%")
				.setParameter("pageSize", pageSize)
				.setParameter("offset", offset).getResultList();

		return transformResultList(resultList);
	}

	@Transactional

	public Land_applicant findByLandApplicantId(Integer landApplicantId) {
		String query = "SELECT " + "	la.applicant_no," + "    la.applicant_name,   " + " la.father_name,"
				+ " la.mobile_no, " + " la.email_address," + "  la.doc_ref_no," + "    la.docs_path, "
				+ "    la.doc_name," + "    ld.docs_path AS documents_path, " + "    lp.total_area,"
				+ "    lp.purchase_area," + "    mb.block_name," + "    md.district_name,   "
				+ "    mdt.doc_name AS documents_name," + "    mgp.gp_name," + "    mkn.khata_no,  "
				+ "    mm.mouza_name," + "    mpn.plot_no," + "    ms.state_name,  " + "    mt.tehsil_name  "
				+ "FROM public.land_applicant la"
				+ " LEFT JOIN public.land_documents ld ON la.land_applicant_id = ld.land_applicant_id"
				+ " LEFT JOIN public.land_plot lp ON la.land_applicant_id = lp.land_applicant_id"
				+ " LEFT JOIN public.m_block mb ON la.curr_block_id = mb.block_id"
				+ " LEFT JOIN public.m_district md ON la.curr_district_id = md.district_id"
				+ " LEFT JOIN public.m_document_type mdt ON la.doc_type = mdt.doc_type_id"
				+ " LEFT JOIN public.m_gp mgp ON la.curr_gp_id = mgp.gp_id"
				+ " LEFT JOIN public.m_khata_no mkn ON la.plot_khata_no_id = mkn.khata_no_id"
				+ " LEFT JOIN public.m_mouza mm ON la.plot_mouza_id = mm.mouza_id"
				+ " LEFT JOIN public.m_plot_no mpn ON lp.plot_no_id = mpn.plot_no_id"
				+ " LEFT JOIN public.m_state ms ON la.curr_state_id = ms.state_id"
				+ " LEFT JOIN public.m_tehsil mt ON la.curr_district_id = mt.district_id AND la.curr_block_id = mt.tehsil_id"
				+ " WHERE la.land_applicant_id =:landApplicantId";

		try {
			Object[] result = (Object[]) entityManager.createNativeQuery(query)
					.setParameter("landApplicantId", landApplicantId).getSingleResult();

			Land_applicant land = new Land_applicant();
			land.setApplicantNo((String) result[0]);
			land.setTxtApplicantName((String) result[1]);
			land.setTxtFatherHusbandName((String) result[2]);
			land.setTxtMobileNo((String) result[3]);
			land.setTxtEmail((String) result[4]);
			land.setTxtDocumentNo((String) result[5]);
			land.setFileUploadDocument((String) result[6]);
//			land.set
			return land;
		} catch (NoResultException e) {
			return null;
		}
	}

	// get data in prasant
	@Transactional
	public List<LandApplicantDTO> getLandApplicantDetailsUser(BigInteger createdBy, int pageNumber, int pageSize) {

		String query = "SELECT la.applicant_no, la.applicant_name, d.district_name, t.tehsil_name, m.mouza_name, k.khata_no, p.plot_no, la.created_on, md.doc_name "
				+ "FROM land_applicant la LEFT JOIN land_plot lp ON la.land_applicant_id = lp.land_applicant_id "
				+ "LEFT JOIN m_district d ON la.curr_district_id = d.district_id "
				+ "LEFT JOIN m_tehsil t ON la.curr_district_id = t.district_id AND la.curr_block_id = t.tehsil_id "
				+ "LEFT JOIN m_mouza m ON la.plot_mouza_id = m.mouza_id "
				+ "LEFT JOIN m_khata_no k ON la.plot_khata_no_id = k.khata_no_id "
				+ "LEFT JOIN m_plot_no p ON lp.plot_no_id = p.plot_no_id "
				+ "LEFT JOIN m_document_type md ON la.doc_type = md.doc_type_id "
				+ "WHERE la.created_by=:createdBy AND la.deleted_flag = '0'  " + "ORDER BY la.applicant_no "
				+ "LIMIT :pageSize OFFSET :offset ";

		int offset = (pageNumber - 1) * pageSize;

		@SuppressWarnings("unchecked")
		List<Object[]> resultList = entityManager.createNativeQuery(query).setParameter("pageSize", pageSize)
				.setParameter("offset", offset).setParameter("createdBy", createdBy).getResultList();

		return transformResultListRET(resultList);
	}

	private List<LandApplicantDTO> transformResultListRET(List<Object[]> resultList) {
		List<LandApplicantDTO> roleInfoList = new ArrayList<>();
		for (Object[] row : resultList) {
			String applicantNo = (String) row[0];
			String applicantName = (String) row[1];
			String districtName = (String) row[2];
			String tehsilName = (String) row[3];
			String mouzaName = (String) row[4];
			String khataNo = (String) row[5];
			String plotNo = (String) row[6];
			Date createdOn = (Date) row[7];
			String docName = (String) row[8];

			LandApplicantDTO roleInfo = new LandApplicantDTO();
			roleInfo.setApplicantNo(applicantNo);
			roleInfo.setApplicantName(applicantName);
			roleInfo.setDistrictName(districtName);
			roleInfo.setTehsilName(tehsilName);
			roleInfo.setMouzaName(mouzaName);
			roleInfo.setKhataNo(khataNo);
			roleInfo.setPlotNo(plotNo);
			roleInfo.setCreatedOn(createdOn);
			roleInfo.setDocName(docName);

			roleInfoList.add(roleInfo);

		}

		return roleInfoList;
	}

	@SuppressWarnings("unused")
	public LandAppResponseStructureDTO getCombinedDataForApplicant(BigInteger applicantId) {

		log.info(" :: getCombinedDataForApplicant() method Execution Are Start ..!!");

		String nativeQuery = "  SELECT "
				+ "        landapp.land_application_id, "
				+ "        landapp.application_no, "
				+ "        landapp.applicant_name, "
				+ "        landapp.father_name, "
				+ "        landapp.mobile_no, "
				+ "        landapp.email_address, "
				+ "        (SELECT "
				+ "            doc_name  "
				+ "        FROM "
				+ "            m_document_type  "
				+ "        WHERE "
				+ "            doc_type_id = landapp.doc_type LIMIT 1) as doc_type, "
				+ "        landapp.doc_ref_no, "
				+ "        landapp.docs_path, "
				+ "        (SELECT "
				+ "            state_name  "
				+ "        FROM "
				+ "            m_state  "
				+ "        WHERE "
				+ "            curr_state_id = landapp.curr_state_id LIMIT 1) as curr_state_id, "
				+ "        (SELECT "
				+ "            district_name  "
				+ "        FROM "
				+ "            m_district  "
				+ "        WHERE "
				+ "            curr_district_id = landapp.curr_district_id LIMIT 1) as curr_district_id, "
				+ "        (SELECT "
				+ "            block_name  "
				+ "        FROM "
				+ "            m_block  "
				+ "        WHERE "
				+ "            curr_block_id = landapp.curr_block_id LIMIT 1) as curr_block_id, "
				+ "        (SELECT "
				+ "            gp_name  "
				+ "        FROM "
				+ "            m_gp  "
				+ "        WHERE "
				+ "            curr_gp_id = landapp.curr_gp_id LIMIT 1) as curr_gp_id, "
				+ "        (SELECT "
				+ "            village_name  "
				+ "        FROM "
				+ "            m_village_master  "
				+ "        WHERE "
				+ "            curr_village_id = landapp.curr_village_id LIMIT 1) as curr_village_id, "
				+ "        landapp.curr_police_station, "
				+ "        landapp.curr_post_office, "
				+ "        landapp.curr_street_no, "
				+ "        landapp.curr_house_no, "
				+ "        landapp.curr_pin_code, "
				+ "        (SELECT "
				+ "            state_name  "
				+ "        FROM "
				+ "            m_state  "
				+ "        WHERE "
				+ "            curr_state_id = landapp.pre_state_id LIMIT 1) as pre_state_id, "
				+ "        (SELECT "
				+ "            district_name  "
				+ "        FROM "
				+ "            m_district  "
				+ "        WHERE "
				+ "            curr_district_id = landapp.pre_district_id LIMIT 1) as pre_district_id, "
				+ "        (SELECT "
				+ "            block_name  "
				+ "        FROM "
				+ "            m_block  "
				+ "        WHERE "
				+ "            curr_block_id = landapp.pre_block_id LIMIT 1) as pre_block_id, "
				+ "        (SELECT "
				+ "            gp_name  "
				+ "        FROM "
				+ "            m_gp  "
				+ "        WHERE "
				+ "            curr_gp_id = landapp.pre_gp_id LIMIT 1) as pre_gp_id, "
				+ "        (SELECT "
				+ "            village_name  "
				+ "        FROM "
				+ "            m_village_master  "
				+ "        WHERE "
				+ "            curr_village_id = landapp.pre_village_id LIMIT 1) as pre_village_id, "
				+ "        landapp.pre_police_station, "
				+ "        landapp.pre_post_office, "
				+ "        landapp.pre_street_no, "
				+ "        landapp.pre_house_no, "
				+ "        landapp.pre_pin_code, "
				+ "        (SELECT "
				+ "            district_name  "
				+ "        FROM "
				+ "             land_bank.district_master    "
				+ "        WHERE "
				+ "            district_code = landapp.district_code LIMIT 1) as plot_district_id, "
				+ "        (SELECT "
				+ "            tahasil_name  "
				+ "        FROM "
				+ "            land_bank.tahasil_master   "
				+ "        WHERE "
				+ "            tehsil_code = landapp.tehsil_code LIMIT 1) as plot_tehsil_id, "
				+ "        (SELECT "
				+ "            village_name  "
				+ "        FROM "
				+ "            land_bank.village_master  "
				+ "        WHERE "
				+ "            village_code = landapp.village_code LIMIT 1) as plot_mouza_id, "
				+ "        (SELECT "
				+ "            khata_no  "
				+ "        FROM "
				+ "            land_bank.khatian_information  "
				+ "        WHERE "
				+ "            khatian_code = landapp.khatian_code LIMIT 1) as plot_kahata_no_id, "
				+ "        (SELECT "
				+ "            plot_no  "
				+ "        FROM "
				+ "            land_bank.plot_information  "
				+ "        WHERE "
				+ "            plot_code = landplot.plot_code LIMIT 1) as plot_no_id, "
//				+ "        landplot.area_acre, "
                + "        ls.total_area, "
				+ "         "
				+ "        (SELECT "
				+ "            kissam  "
				+ "        FROM "
				+ "            land_bank.plot_information  "
				+ "        WHERE "
				+ "            plot_code = landplot.plot_code LIMIT 1) as varieties_id, "
				+ "        (SELECT "
				+ "            doc_name  "
				+ "        FROM "
				+ "            m_document_type  "
				+ "        WHERE "
				+ "            document_name = landdocs.document_name LIMIT 1) as document_name, "
				+ "        landdocs.docs_path as land_docs_path , "
				+ "        app.pending_at_role_id,    "
				+ "        (SELECT "
				+ "            purchase_area           "
				+ "        FROM "
				+ "            public.land_schedule         "
				+ "        WHERE "
				+ "            plot_code = landplot.plot_code LIMIT 1) as purchasearea "
				+ "    FROM "
				+ "        public.land_application landapp      "
				+ "    LEFT JOIN "
				+ "        land_bank.plot_information landplot  "
				+ "            ON ( "
				+ "                landplot.khatian_code = landapp.khatian_code "
				+ "            )      "
				+ "    LEFT JOIN "
				+ "        land_documents landdocs  "
				+ "            ON ( "
				+ "                landdocs.land_application_id=landapp.land_application_id  "
				+ "            )      "
				+ "    LEFT JOIN "
				+ "        land_application_approval app  "
				+ "            on( "
				+ "                app.land_application_id=landapp.land_application_id "
				+ "            )   "
				+ "   LEFT JOIN public.land_schedule ls ON(ls.land_application_id=landapp.land_application_id)   "
				+ "    WHERE "
				+ "        landapp.land_application_id =:landapplicantid  "
				+ "        AND landapp.deleted_flag='0'";

		@SuppressWarnings("unchecked")
		List<Object[]> queryResults = entityManager.createNativeQuery(nativeQuery)
				.setParameter("landapplicantid", applicantId).getResultList();
		log.info(" :: queryResults Return  ..!!");

		LandApplicantViewDTO applicantDTO = mapToApplicantDTO(queryResults);
		List<LandPlotViewDTO> plotDTOList = mapToPlotDTO(queryResults); //
		List<LandAppViewDocumentDTO> documentDTOList = mapToDocumentDTO(queryResults);

		LandAppResponseStructureDTO response = new LandAppResponseStructureDTO(applicantDTO, plotDTOList,
				documentDTOList);
		if (response != null) {
			log.info(" :: LandAppResponseStructureDTO Return  ..!!");
			return response;
		} else
			log.info(" :: LandAppResponseStructureDTO Return Null  ..!!");
		return null;
	}

	private LandApplicantViewDTO mapToApplicantDTO(List<Object[]> queryResults) {
		if (queryResults.isEmpty()) {
			log.info(" :: LandApplicantViewDTO Return Null  ..!!");
			return null;
		}
		log.info(" :: LandApplicantViewDTO Return Result And Add to a DTO  ..!!");
		Object[] row = queryResults.get(0);
		LandApplicantViewDTO applicantDTO = new LandApplicantViewDTO();
		applicantDTO.setLandApplicantId((BigInteger) row[0]);
		applicantDTO.setApplicantNo((String) row[1]);
		applicantDTO.setApplicantName((String) row[2]);
		applicantDTO.setFatherName((String) row[3]);
		applicantDTO.setMobileNo((String) row[4]);
		applicantDTO.setEmailAddress((String) row[5]);
		applicantDTO.setDocType((String) row[6]);
		applicantDTO.setDocRefNo((String) row[7]);
		applicantDTO.setDocsPath((String) row[8]);
		applicantDTO.setCurrStateId((String) row[9]);
		applicantDTO.setCurrDistrictId((String) row[10]);
		applicantDTO.setCurrBlockId((String) row[11]);
		applicantDTO.setCurrGpId((String) row[12]);
		applicantDTO.setCurrVillageId((String) row[13]);
		applicantDTO.setCurrPoliceStation((String) row[14]);
		applicantDTO.setCurrPostOffice((String) row[15]);
		applicantDTO.setCurrStreetNo((String) row[16]);
		applicantDTO.setCurrHouseNo((String) row[17]);
		applicantDTO.setCurrPinCode((String) row[18]);
		applicantDTO.setPreStateId((String) row[19]);
		applicantDTO.setPreDistrictId((String) row[20]);
		applicantDTO.setPreBlockId((String) row[21]);
		applicantDTO.setPreGpId((String) row[22]);
		applicantDTO.setPreVillageId((String) row[23]);
		applicantDTO.setPrePoliceStation((String) row[24]);
		applicantDTO.setPrePostOffice((String) row[25]);
		applicantDTO.setPreStreetNo((String) row[26]);
		applicantDTO.setPreHouseNo((String) row[27]);
		applicantDTO.setPrePinCode((String) row[28]);
		applicantDTO.setPlotDistrictId((String) row[29]);
		applicantDTO.setPlotTehsilId((String) row[30]);
		applicantDTO.setPlotMouzaId((String) row[31]);
		applicantDTO.setPlotKhataNoId((String) row[32]);
		applicantDTO.setPendingRoleId((Short) row[38]);
//		applicantDTO.setPurchasearea((BigDecimal) row[39]);

		log.info(" :: LandApplicantViewDTO Return Result And Add to a DTO and return the result ..!!");
		return applicantDTO;
	}

	private List<LandPlotViewDTO> mapToPlotDTO(List<Object[]> queryResults) {
		List<LandPlotViewDTO> plotDTOList = new ArrayList<>();

		log.info(" :: LandPlotViewDTO Return Result And Add to a DTO  ..!!");
		for (Object[] row : queryResults) {
			LandPlotViewDTO plotDTO = new LandPlotViewDTO();
			plotDTO.setPlotNoId((String) row[33]);
			plotDTO.setTotalArea((BigDecimal) row[34]);
//			plotDTO.setPurchaseArea((BigDecimal) row[35]);
			plotDTO.setVarietiesId((String) row[35]);
			plotDTO.setPurchaseArea((BigDecimal) row[39]);
			plotDTOList.add(plotDTO);
		}

		log.info(" :: LandPlotViewDTO Return Result And Add to a DTO also return   ..!!");
		return plotDTOList;
	}

	private List<LandAppViewDocumentDTO> mapToDocumentDTO(List<Object[]> queryResults) {
		List<LandAppViewDocumentDTO> documentDTOList = new ArrayList<>();
		log.info(" :: LandAppViewDocumentDTO Return Result And Add to a DTO    ..!!");

		for (Object[] row : queryResults) {
			LandAppViewDocumentDTO documentDTO = new LandAppViewDocumentDTO();
			documentDTO.setDocumentName((String) row[36]);
//			String docsPath = filePathloc + "/" + (String) row[38];
			documentDTO.setDocsPath((String) row[37]);
//			documentDTO.setPendingAtRoleid((Short) row[38]);

			documentDTOList.add(documentDTO);
		}
		log.info(" :: LandAppViewDocumentDTO Return Result And Add to a DTO also return   ..!!");
		return documentDTOList;
	}

	
	//save the query
	public Integer saveLandApplicantRecord(Land_applicant land) {
		
		String sqlQuery = "INSERT INTO public.land_application ( " +
	            " applicant_name, father_name, mobile_no, email_address, doc_type, doc_ref_no," +
	            " docs_path, curr_state_id, curr_district_id, curr_block_id, curr_gp_id," +
	            " curr_village_id, curr_police_station, curr_post_office, curr_street_no," +
	            " curr_house_no, curr_pin_code, pre_state_id, pre_district_id, pre_block_id," +
	            " pre_gp_id, pre_village_id, created_by, pre_police_station," +
	            " pre_post_office, pre_street_no, pre_house_no, pre_pin_code," +
	            " save_status, application_no" +
	            " " +
	        "     ) VALUES (" +
	            " :applicantName, :fatherName, :mobileNo, :emailAddress, :docType, :docRefNo," +
	            " :docsPath, :currStateId, :currDistrictId, :currBlockId, :currGpId," +
	            " :currVillageId, :currPoliceStation, :currPostOffice, :currStreetNo," +
	            " :currHouseNo, :currPinCode, :preStateId, :preDistrictId, :preBlockId," +
	            " :preGpId, :preVillageId, :createdBy,  :prePoliceStation," +
	            " :prePostOffice, :preStreetNo, :preHouseNo, :prePinCode," +
	            " :saveStatus, :applicantNo " +
	        "      ) "
	        + " RETURNING land_application_id";
		
		Query query = entityManager.createNativeQuery(sqlQuery)
       

		
				.setParameter("applicantName", land.getTxtApplicantName())
				.setParameter("fatherName", land.getTxtFatherHusbandName())
				.setParameter("mobileNo", land.getTxtMobileNo())
				.setParameter("emailAddress", land.getTxtEmail())
				.setParameter("docType", land.getSelDocumentType())
				.setParameter("docRefNo", land.getTxtDocumentNo())
				.setParameter("docsPath", land.getFileUploadDocument())
				.setParameter("currStateId", land.getSelState())
				.setParameter("currDistrictId", land.getSelDistrict())
				.setParameter("currBlockId", land.getSelBlockULB())
				.setParameter("currVillageId", land.getSelVillageLocalAreaName())
				.setParameter("currGpId", land.getSelGPWardNo())
				
				.setParameter("preStateId", land.getSelState17())
				.setParameter("preDistrictId", land.getSelDistrict18())
				.setParameter("preBlockId", land.getSelBlockULB19())
				.setParameter("preGpId", land.getSelGPWARDNumber())
				.setParameter("preVillageId", land.getSelVillageLocalAreaName21())
				
				.setParameter("createdBy", land.getIntCreatedBy())
				
				.setParameter("currPoliceStation", land.getTxtPoliceStation())
				.setParameter("currPostOffice", land.getTxtPostOffice())
				.setParameter("currStreetNo", land.getTxtHabitationStreetNoLandmark())
				.setParameter("currHouseNo", land.getTxtHouseNo())
				.setParameter("currPinCode", land.getTxtPinCode())
				
				.setParameter("prePoliceStation", land.getTxtPoliceStation22())
				.setParameter("prePostOffice", land.getTxtPostOffice23())
				.setParameter("preStreetNo", land.getTxtHabitationStreetNoLandmark24())
				.setParameter("preHouseNo", land.getTxtHouseNo25())
				.setParameter("prePinCode", land.getTxtPinCode26())
				.setParameter("saveStatus", land.getSaveStatus())
				.setParameter("applicantNo", land.getApplicantNo());
				
		
		    BigInteger result = (BigInteger) query.getSingleResult();
	        return result.intValue();
		 
		 //return new Land_applicant();

	}
	
	//use the document save propouse
	@Transactional
    public Integer insertLandDocument(String docsPath, String documentName,
                                    Integer createdBy, Integer landApplicantId ) {
        String sqlQuery = "INSERT INTO land_documents (docs_path, document_name, " +
                          "created_by, land_application_id) " +
                          "VALUES (?, ?, ?, ?)";

        System.out.println(docsPath +"  "+documentName);
        return entityManager.createNativeQuery(sqlQuery)
            .setParameter(1, docsPath)
            .setParameter(2, documentName)
            .setParameter(3, createdBy)
            .setParameter(4, landApplicantId)
            .executeUpdate();
    }

	//update the applicant reord
	@Transactional
    public Land_applicant updateLandApplicant(Land_applicant dto) {
        String nativeQuery = "UPDATE public.land_application SET "
            + "application_no = :applicantNo, "
           
           
            + "docs_path = :docsPath, "
            
            + "updated_by = :updatedBy, "
            + "save_status = :saveStatus, "
            + "curr_block_id = :currBlockId, "
            + "pre_block_id = :preBlockId, "
            + "curr_district_id = :currDistrictId, "
            + "pre_district_id = :preDistrictId, "
          
            + "doc_type = :docType, "
            + "pre_gp_id = :preGpId, "
            + "curr_gp_id = :currGpId, "
           
            + "curr_state_id = :currStateId, "
            + "pre_state_id = :preStateId, "
           
            + "curr_village_id = :currVillageId, "
            + "pre_village_id = :preVillageId, "
           
            + "applicant_name = :applicantName, "
          
            + "email_address = :emailAddress, "
            + "father_name = :fatherName, "
            + "curr_street_no = :currStreetNo, "
            + "pre_street_no = :preStreetNo, "
            + "curr_house_no = :currHouseNo, "
            + "pre_house_no = :preHouseNo, "
            + "mobile_no = :mobileNo, "
            + "curr_pin_code = :currPinCode, "
            + "pre_pin_code = :prePinCode, "
            + "curr_police_station = :currPoliceStation, "
            + "pre_police_station = :prePoliceStation, "
            + "curr_post_office = :currPostOffice, "
            + "pre_post_office = :prePostOffice "
            + "WHERE land_application_id = :landApplicantId";
        
         entityManager.createNativeQuery(nativeQuery)
            .setParameter("applicantNo", dto.getApplicantNo())
 
           
            .setParameter("docsPath", dto.getFileUploadDocument())
           
            .setParameter("updatedBy", dto.getIntCreatedBy())
            .setParameter("saveStatus", dto.getSaveStatus())
            .setParameter("currBlockId", dto.getSelBlockULB())
            .setParameter("preBlockId", dto.getSelBlockULB19())
            .setParameter("currDistrictId", dto.getSelDistrict())
            .setParameter("preDistrictId", dto.getSelDistrict18())
    
            .setParameter("docType", dto.getSelDocumentType())
            .setParameter("preGpId", dto.getSelGPWARDNumber())
            .setParameter("currGpId", dto.getSelGPWardNo())
           
            .setParameter("currStateId", dto.getSelState())
            .setParameter("preStateId", dto.getSelState17())
            
            .setParameter("currVillageId", dto.getSelVillageLocalAreaName())
            .setParameter("preVillageId", dto.getSelVillageLocalAreaName21())
          
            .setParameter("applicantName", dto.getTxtApplicantName())
           
            .setParameter("emailAddress", dto.getTxtEmail())
            .setParameter("fatherName", dto.getTxtFatherHusbandName())
            .setParameter("currStreetNo", dto.getTxtHabitationStreetNoLandmark())
            .setParameter("preStreetNo", dto.getTxtHabitationStreetNoLandmark24())
            .setParameter("currHouseNo", dto.getTxtHouseNo())
            .setParameter("preHouseNo", dto.getTxtHouseNo25())
            .setParameter("mobileNo", dto.getTxtMobileNo())
            .setParameter("currPinCode", dto.getTxtPinCode())
            .setParameter("prePinCode", dto.getTxtPinCode26())
            .setParameter("currPoliceStation", dto.getTxtPoliceStation())
            .setParameter("prePoliceStation", dto.getTxtPoliceStation22())
            .setParameter("currPostOffice", dto.getTxtPostOffice())
            .setParameter("prePostOffice", dto.getTxtPostOffice23())
            .setParameter("landApplicantId", dto.getIntId())
            .executeUpdate();
		return dto;
    }
}

