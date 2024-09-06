package com.csmtech.SJTA.repository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.csmtech.SJTA.dto.AppliedLandApplicationDTO;
import com.csmtech.SJTA.dto.LandApplicantDTO;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class AppliedLandApplicationNativeRepository {

	@PersistenceContext
	@Autowired
	EntityManager entityManager;

	@Transactional
	public List<AppliedLandApplicationDTO> getAppliedLandApplicantDetailsPage(Integer createdBy, Integer pageNumber,
			Integer pageSize) {
		String query = "SELECT  la.land_application_id,la.application_no,la.applicant_name, la.mobile_no, "
				+ "				   d.district_name,  " + "				        t.tahasil_name,  "
				+ "				       m.village_name,  " + "				        k.khata_no, "
				+ "						G.application_status,F.action_on,F.remark,   "
				+ " pp.pending_at_role_id " 
				+ "				      FROM  "
				+ "				       public.land_application la   " + "				    INNER JOIN  "
				+ "				        land_bank.district_master d  "
				+ "				            ON la.district_code = d.district_code   "
				+ "				    INNER JOIN  " + "				       land_bank.tahasil_master t   "
				+ "				            ON la.tehsil_code = t.tahasil_code   "
				+ "				    INNER JOIN  " + "				       land_bank.village_master m   "
				+ "				            ON la.village_code = m.village_code   "
				+ "				    INNER JOIN  " + "				       land_bank.khatian_information k   "
				+ "				            ON la.khatian_code = k.khatian_code  "
				+ "					inner join land_application_approval F on la.land_application_id=F.land_application_id "
				+ "					inner join m_application_status G on F.application_status_id= G.application_status_id  "
				+ "   inner join "
				+ "        public.land_application_approval pp  "
				+ "            on la.land_application_id= pp.land_application_id"
				+ "				   WHERE la.created_by=:createdBy "
				+ "				  AND la.deleted_flag = '0'  "
				+ "				ORDER BY la.land_application_id ASC " + "LIMIT :pageSize OFFSET :offset";

		int offset = (pageNumber - 1) * pageSize;

		@SuppressWarnings("unchecked")
		List<Object[]> resultList = entityManager.createNativeQuery(query).setParameter("createdBy", createdBy)
				.setParameter("pageSize", pageSize).setParameter("offset", offset).getResultList();

		entityManager.close();
		return transformResultList(resultList);
	}

	@Transactional
	private List<AppliedLandApplicationDTO> transformResultList(List<Object[]> resultList) {
		List<AppliedLandApplicationDTO> roleInfoList = new ArrayList<>();
		for (Object[] row : resultList) {
			BigInteger landApplicantId = (BigInteger) row[0];
			String applicantNo = (String) row[1];
			String applicantName = (String) row[2];
			String mobileNo = (String) row[3];
			String districtName = (String) row[4];
			String tehsilName = (String) row[5];
			String mouzaName = (String) row[6];
			String khataNo = (String) row[7];
			String applicationStatus = (String) row[8];
			Date actionOn = (Date) row[9];
			String remark = (String) row[10];
			Short pendingroleid = (Short) row[11];

			AppliedLandApplicationDTO roleInfo = new AppliedLandApplicationDTO();
			roleInfo.setLandApplicantId(landApplicantId);
			roleInfo.setApplicantNo(applicantNo);
			roleInfo.setApplicantName(applicantName);
			roleInfo.setMobileNo(mobileNo);
			roleInfo.setDistrictName(districtName);
			roleInfo.setTehsilName(tehsilName);
			roleInfo.setMouzaName(mouzaName);
			roleInfo.setKhataNo(khataNo);
			roleInfo.setApplicationStatus(applicationStatus);
			roleInfo.setActionOn(actionOn);
			roleInfo.setRemark(remark);
			roleInfo.setPendingroleid(pendingroleid);
			roleInfoList.add(roleInfo);
			

		}
		return roleInfoList;
	}

	@Transactional
	public Integer getTotalLandApplicantCount(Integer createdBy) {
		String query = "" + "  SELECT " + " COUNT(*)  " + " FROM " + "         public.land_application la \n"
				+ "			INNER JOIN \n" + "			land_bank.district_master d \n"
				+ "			ON la.district_code = d.district_code \n" + "			INNER JOIN\n"
				+ "			land_bank.tahasil_master t \n" + "			ON la.tehsil_code = t.tahasil_code  \n"
				+ "			INNER JOIN \n" + "			land_bank.village_master m  \n"
				+ "			ON la.village_code = m.village_code  \n" + "			INNER JOIN \n"
				+ "			land_bank.khatian_information k  \n" + "			ON la.khatian_code = k.khatian_code \n"
				+ "			inner join land_application_approval F on la.land_application_id=F.land_application_id\n"
				+ "			inner join m_application_status G on F.application_status_id= G.application_status_id \n"
				+ "			WHERE la.created_by=:createdBy \n" + "			AND la.deleted_flag = '0' \n";
		BigInteger count = (BigInteger) entityManager.createNativeQuery(query).setParameter("createdBy", createdBy)
				.getSingleResult();
		LandApplicantDTO dto = new LandApplicantDTO();
		dto.setCountint(count);
		return count.intValue();
	}
	
	//update record
	@Transactional
    public Integer cancelLandApplication(BigInteger landApplicationId,String remark) {
		String updateLandApplicationQuery = "UPDATE public.land_application SET deleted_flag = B'1' WHERE land_application_id = :id";
        String updateLandScheduleQuery = "UPDATE public.land_schedule SET deleted_flag = B'1' WHERE land_application_id = :id";
        String updateLandDocumentsQuery = "UPDATE public.land_documents SET deleted_flag = B'1' WHERE land_application_id = :id";
        String updateLandApplicationApprovalQuery = "UPDATE public.land_application_approval "
        		+ " SET status = B'1', pending_at_role_id = 2, " +
                "   application_status_id = 17, approval_action_id = 0, remark = :remark, approval_level = 1"
                + "  WHERE land_application_id = :id";
        
        entityManager.createNativeQuery(updateLandApplicationQuery)
        .setParameter("id", landApplicationId)
        .executeUpdate();

        entityManager.createNativeQuery(updateLandScheduleQuery)
        .setParameter("id", landApplicationId)
        .executeUpdate();

        entityManager.createNativeQuery(updateLandDocumentsQuery)
        .setParameter("id", landApplicationId)
        .executeUpdate();

        return entityManager.createNativeQuery(updateLandApplicationApprovalQuery)
                .setParameter("id", landApplicationId)
                .setParameter("remark", remark)
                .executeUpdate();
    }
}
