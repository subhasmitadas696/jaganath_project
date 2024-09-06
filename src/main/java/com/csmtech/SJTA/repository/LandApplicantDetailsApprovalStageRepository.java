package com.csmtech.SJTA.repository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.csmtech.SJTA.dto.LandApplicantDTO;
import com.csmtech.SJTA.dto.LandApplicantDetailsUPDTO;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class LandApplicantDetailsApprovalStageRepository {

	@PersistenceContext
	@Autowired
	private EntityManager entityManager;
	
	//---------------------------------------------------------------------------------------------------------------------------
	
	//Select Count Hear
	@Transactional
	public BigInteger getUnerProcessUseLike( String applicantName,
	         String applicantUniqueId, String plotKathaNO) {
		
		String sql = " SELECT "
				+ "        count(*)  "
				+ "    FROM "
				+ "        public.land_application A   "
				+ "    inner join "
				+ "        land_bank.district_master B  "
				+ "            ON A.district_code = B.district_code  "
				+ "    inner join "
				+ "        land_bank.tahasil_master C  "
				+ "            ON A.tehsil_code =C.tahasil_code  "
				+ "    inner join "
				+ "        land_bank.village_master D  "
				+ "            ON A.village_code = D.village_code   "
				+ "    inner join "
				+ "         land_bank.khatian_information E  "
				+ "            ON A.khatian_code = E.khatian_code  "
				+ "    inner join "
				+ "        land_application_approval F "
				+ "            ON A.land_application_id =F.land_application_id    "
				+ "    inner join "
				+ "        m_application_status G  "
				+ "            on F.application_status_id=G.application_status_id   "
				+ "    where "
				+ "        F.approval_action_id in ( "
				+ "            1,5 "
				+ "        ) " 
				+ " AND  A.applicant_name ILIKE :applicantName  "
				+ "  AND A.application_no ILIKE :applicantUniqueId  " 
				+ "   AND E.Khata_no ILIKE :plotKathaNO ";
		
		
		 return  (BigInteger) entityManager.createNativeQuery(sql)
				 .setParameter("applicantName", "%" + applicantName + "%")
					.setParameter("applicantUniqueId", "%" + applicantUniqueId + "%")
					.setParameter("plotKathaNO", "%" + plotKathaNO + "%")
		            .getSingleResult();
	}
	
	@Transactional
	public BigInteger getUnerProcess() {
		
		String sql = " SELECT "
				+ "        count(*)  "
				+ "    FROM "
				+ "        public.land_application A   "
				+ "    inner join "
				+ "        land_bank.district_master B  "
				+ "            ON A.district_code = B.district_code  "
				+ "    inner join "
				+ "        land_bank.tahasil_master C  "
				+ "            ON A.tehsil_code =C.tahasil_code  "
				+ "    inner join "
				+ "        land_bank.village_master D  "
				+ "            ON A.village_code = D.village_code   "
				+ "    inner join "
				+ "         land_bank.khatian_information E  "
				+ "            ON A.khatian_code = E.khatian_code  "
				+ "    inner join "
				+ "        land_application_approval F "
				+ "            ON A.land_application_id =F.land_application_id    "
				+ "    inner join "
				+ "        m_application_status G  "
				+ "            on F.application_status_id=G.application_status_id   "
				+ "    where "
				+ "        F.approval_action_id in ( "
				+ "            1,5 "
				+ "        ) ";
		
		
		 return  (BigInteger) entityManager.createNativeQuery(sql)
		            .getSingleResult();
	}
	
	
	//getRevertToCitizenUseLike
	
	@Transactional
	public BigInteger getRevertToCitizenUseLike(Integer revertToCitiZen,
			String applicantName,  String applicantUniqueId, String plotKathaNO) {
		
		String sql = " SELECT count(*)"
				+ "		 FROM "
				+ "        public.land_application A        "
				+ "    inner join "
				+ "        land_bank.district_master B               "
				+ "            ON A.district_code = B.district_code       "
				+ "    inner join "
				+ "        land_bank.tahasil_master C               "
				+ "            ON A.tehsil_code =C.tahasil_code       "
				+ "    inner join "
				+ "        land_bank.village_master D               "
				+ "            ON A.village_code = D.village_code        "
				+ "    inner join "
				+ "        land_bank.khatian_information E               "
				+ "            ON A.khatian_code = E.khatian_code       "
				+ "    inner join "
				+ "        land_application_approval F              "
				+ "            ON A.land_application_id =F.land_application_id         "
				+ "    inner join "
				+ "        m_application_status G               "
				+ "            on F.application_status_id=G.application_status_id  "
				+ "	where F.approval_action_id =:revertToCitiZen " 
				+ " AND  A.applicant_name ILIKE :applicantName  "
				+ " AND A.application_no ILIKE :applicantUniqueId  " 
				+ " AND E.khata_no ILIKE :plotKathaNO ";

		
		
		 return  (BigInteger) entityManager.createNativeQuery(sql)
				 .setParameter("revertToCitiZen", revertToCitiZen)
				 .setParameter("applicantName", "%" + applicantName + "%")
					.setParameter("applicantUniqueId", "%" + applicantUniqueId + "%")
					.setParameter("plotKathaNO", "%" + plotKathaNO + "%")
		            .getSingleResult();
	}
	
	@Transactional
	public BigInteger getRevertToCitizen(Integer revertToCitiZen) {
		

		String sql = " SELECT "
				+ "        count(*)     "
				+ "		 FROM "
				+ "        public.land_application A        "
				+ "    inner join "
				+ "        land_bank.district_master B               "
				+ "            ON A.district_code = B.district_code       "
				+ "    inner join "
				+ "        land_bank.tahasil_master C               "
				+ "            ON A.tehsil_code =C.tahasil_code       "
				+ "    inner join "
				+ "        land_bank.village_master D               "
				+ "            ON A.village_code = D.village_code        "
				+ "    inner join "
				+ "        land_bank.khatian_information E               "
				+ "            ON A.khatian_code = E.khatian_code       "
				+ "    inner join "
				+ "        land_application_approval F              "
				+ "            ON A.land_application_id =F.land_application_id         "
				+ "    inner join "
				+ "        m_application_status G               "
				+ "            on F.application_status_id=G.application_status_id  "
				+ "			 where "
				+ "        F.approval_action_id =:revertToCitiZen ";
		
		
		 return  (BigInteger) entityManager.createNativeQuery(sql)
				 .setParameter("revertToCitiZen", revertToCitiZen)
		         .getSingleResult();
	}
	
	
	
	
	@Transactional
	public BigInteger getApproveUseLike(Integer approvedUser,
			String applicantName, String applicantUniqueId, String plotKathaNO) {
		

		String sql = " SELECT count(*)"
				+ " FROM "
				+ "        public.land_application A        "
				+ "    inner join "
				+ "        land_bank.district_master B               "
				+ "            ON A.district_code = B.district_code       "
				+ "    inner join "
				+ "        land_bank.tahasil_master C               "
				+ "            ON A.tehsil_code =C.tahasil_code       "
				+ "    inner join "
				+ "        land_bank.village_master D               "
				+ "            ON A.village_code = D.village_code        "
				+ "    inner join "
				+ "        land_bank.khatian_information E               "
				+ "            ON A.khatian_code = E.khatian_code       "
				+ "    inner join "
				+ "        land_application_approval F              "
				+ "            ON A.land_application_id =F.land_application_id         "
				+ "    inner join "
				+ "        m_application_status G               "
				+ "            on F.application_status_id=G.application_status_id   "
				+ "    where "
				+ "        F.approval_action_id =:approvedUser " 
				+ " AND  A.applicant_name ILIKE :applicantName  "
				+ " AND A.application_no ILIKE :applicantUniqueId  " 
				+ " AND E.khata_no ILIKE :plotKathaNO ";
		
		
		 return  (BigInteger) entityManager.createNativeQuery(sql)
				 .setParameter("approvedUser", approvedUser)
				.setParameter("applicantName", "%" + applicantName + "%")
				.setParameter("applicantUniqueId", "%" + applicantUniqueId + "%")
				.setParameter("plotKathaNO", "%" + plotKathaNO + "%")
		         .getSingleResult();
	}
	
	
	@Transactional
	public BigInteger getApprove(Integer approvedUser) {
		

		String sql = " SELECT count(*)"
				+ " FROM "
				+ "        public.land_application A        "
				+ "    inner join "
				+ "        land_bank.district_master B               "
				+ "            ON A.district_code = B.district_code       "
				+ "    inner join "
				+ "        land_bank.tahasil_master C               "
				+ "            ON A.tehsil_code =C.tahasil_code       "
				+ "    inner join "
				+ "        land_bank.village_master D               "
				+ "            ON A.village_code = D.village_code        "
				+ "    inner join "
				+ "        land_bank.khatian_information E               "
				+ "            ON A.khatian_code = E.khatian_code       "
				+ "    inner join "
				+ "        land_application_approval F              "
				+ "            ON A.land_application_id =F.land_application_id         "
				+ "    inner join "
				+ "        m_application_status G               "
				+ "            on F.application_status_id=G.application_status_id   "
				+ "    where "
				+ "        F.approval_action_id =:approvedUser ";
		
		
		 return  (BigInteger) entityManager.createNativeQuery(sql)
				 .setParameter("approvedUser", approvedUser)
		         .getSingleResult();
	}
	
	//use
	@Transactional
	public BigInteger getRejectUseLike(Integer approvalActionId,String applicantName, String applicantUniqueId, String plotKathaNO) {
		
		String sql = " SELECT count(*)"
				+ "FROM "
				+ "        public.land_application A        "
				+ "    inner join "
				+ "        land_bank.district_master B               "
				+ "            ON A.district_code = B.district_code       "
				+ "    inner join "
				+ "        land_bank.tahasil_master C               "
				+ "            ON A.tehsil_code =C.tahasil_code       "
				+ "    inner join "
				+ "        land_bank.village_master D               "
				+ "            ON A.village_code = D.village_code        "
				+ "    inner join "
				+ "        land_bank.khatian_information E               "
				+ "            ON A.khatian_code = E.khatian_code       "
				+ "    inner join "
				+ "        land_application_approval F              "
				+ "            ON A.land_application_id =F.land_application_id         "
				+ "    inner join "
				+ "        m_application_status G               "
				+ "            on F.application_status_id=G.application_status_id  "
				+ " where F.approval_action_id =:approvalActionId " 
				+ " AND  A.applicant_name ILIKE :applicantName  "
				+ " AND A.application_no ILIKE :applicantUniqueId  " 
				+ " AND E.Khata_no ILIKE :plotKathaNO ";
		
		
		
		 return  (BigInteger) entityManager.createNativeQuery(sql)
				 .setParameter("approvalActionId", approvalActionId)
					.setParameter("applicantName", "%" + applicantName + "%")
					.setParameter("applicantUniqueId", "%" + applicantUniqueId + "%")
					.setParameter("plotKathaNO", "%" + plotKathaNO + "%")
		         .getSingleResult();
	}
	
	@Transactional
	public BigInteger getReject(Integer approvalActionId) {
		
		String sql = " SELECT count(*)"
				+ " FROM "
				+ "        public.land_application A        "
				+ "    inner join "
				+ "        land_bank.district_master B               "
				+ "            ON A.district_code = B.district_code       "
				+ "    inner join "
				+ "        land_bank.tahasil_master C               "
				+ "            ON A.tehsil_code =C.tahasil_code       "
				+ "    inner join "
				+ "        land_bank.village_master D               "
				+ "            ON A.village_code = D.village_code        "
				+ "    inner join "
				+ "        land_bank.khatian_information E               "
				+ "            ON A.khatian_code = E.khatian_code       "
				+ "    inner join "
				+ "        land_application_approval F              "
				+ "            ON A.land_application_id =F.land_application_id         "
				+ "    inner join "
				+ "        m_application_status G               "
				+ "            on F.application_status_id=G.application_status_id   "
				+ "    where "
				+ "        F.approval_action_id =:approvalActionId ";
		
		
		 return  (BigInteger) entityManager.createNativeQuery(sql)
				 .setParameter("approvalActionId", approvalActionId)
		         .getSingleResult();
	}
	
	
	
	//-------------------------------------------------------------------------------------------------------------------------------
	

	@Transactional
	public List<LandApplicantDTO> getLandApplicantsWithDetailsSearchFunction(String applicantName, int pageNumber,
			int pageSize, String applicantUniqueId, String plotKathaNO) {
		String sql = "  SELECT "
				+ "        A.land_application_id, "
				+ "        A.application_no, "
				+ "        A.applicant_name, "
				+ "        A.mobile_no, "
				+ "        B.district_name, "
				+ "        C.tahasil_name, "
				+ "        D.village_name, "
				+ "        E.khata_no, "
				+ "        G.application_status, "
				+ "        F.action_on, "
				+ "        F.remark   "
				+ "     FROM "
				+ "        public.land_application A   "
				+ "    inner join "
				+ "        land_bank.district_master B  "
				+ "            ON A.district_code = B.district_code  "
				+ "    inner join "
				+ "        land_bank.tahasil_master C  "
				+ "            ON A.tehsil_code =C.tahasil_code  "
				+ "    inner join "
				+ "        land_bank.village_master D  "
				+ "            ON A.village_code = D.village_code   "
				+ "    inner join "
				+ "         land_bank.khatian_information E  "
				+ "            ON A.khatian_code = E.khatian_code  "
				+ "    inner join "
				+ "        land_application_approval F "
				+ "            ON A.land_application_id =F.land_application_id    "
				+ "    inner join "
				+ "        m_application_status G  "
				+ "            on F.application_status_id=G.application_status_id   "
				+ "    where "
				+ "        F.approval_action_id in ( "
				+ "            1,5 "
				+ "        ) " 
				+ " AND  A.applicant_name ILIKE :applicantName  "
				+ "   AND A.application_no ILIKE :applicantUniqueId  " + "   AND E.Khata_no ILIKE :plotKathaNO "
				+ " ORDER BY A.land_application_id ASC  " + "  LIMIT :pageSize OFFSET :offset ";

		log.info(":: getLandApplicantsWithDetailsSearchFunction()  Query Execution Sucess..!!");

		int offset = (pageNumber - 1) * pageSize;

		Query query = entityManager.createNativeQuery(sql)
				.setParameter("applicantName", "%" + applicantName + "%")
				.setParameter("applicantUniqueId", "%" + applicantUniqueId + "%")
				.setParameter("plotKathaNO", "%" + plotKathaNO + "%").setParameter("pageSize", pageSize)
				.setParameter("offset", offset);

		List<LandApplicantDTO> resultList = new ArrayList<>();
		@SuppressWarnings("unchecked")
		List<Object[]> rows = query.getResultList();
		for (Object[] row : rows) {
			LandApplicantDTO dto = new LandApplicantDTO();
			dto.setLandApplicantId((BigInteger) row[0]);
			dto.setApplicantNo((String) row[1]);
			dto.setApplicantName((String) row[2]);
			dto.setMobileNo((String) row[3]);
			dto.setDistrictName((String) row[4]);
			dto.setTehsilName((String) row[5]);
			dto.setMouzaName((String) row[6]);
			dto.setKhataNo((String) row[7]);
			dto.setApplicationStatus((String) row[8]);
			dto.setActionOn((Date) row[9]);
			dto.setRemark((String) row[10]);

			resultList.add(dto);
		}
		log.info(":: getLandApplicantsWithDetailsSearchFunction() --   Result Return Scucess..!!");
		return resultList;
	}

	@Transactional
	public List<LandApplicantDTO> getLandApplicantsWithDetails(int pageNumber, int pageSize) {
		String sql = " SELECT "
				+ "        A.land_application_id, "
				+ "        A.application_no, "
				+ "        A.applicant_name, "
				+ "        A.mobile_no, "
				+ "        B.district_name, "
				+ "        C.tahasil_name, "
				+ "        D.village_name, "
				+ "        E.khata_no, "
				+ "        G.application_status, "
				+ "        F.action_on, "
				+ "        F.remark  "
				+ "    FROM "
				+ "        public.land_application A   "
				+ "    inner join "
				+ "        land_bank.district_master B  "
				+ "            ON A.district_code = B.district_code  "
				+ "    inner join "
				+ "        land_bank.tahasil_master C  "
				+ "            ON A.tehsil_code =C.tahasil_code  "
				+ "    inner join "
				+ "        land_bank.village_master D  "
				+ "            ON A.village_code = D.village_code   "
				+ "    inner join "
				+ "         land_bank.khatian_information E  "
				+ "            ON A.khatian_code = E.khatian_code  "
				+ "    inner join "
				+ "        land_application_approval F "
				+ "            ON A.land_application_id =F.land_application_id    "
				+ "    inner join "
				+ "        m_application_status G  "
				+ "            on F.application_status_id=G.application_status_id   "
				+ "    where "
				+ "        F.approval_action_id in ( "
				+ "            1,5 "
				+ "        ) "
				+ "		 " 
				+ " ORDER BY A.land_application_id ASC "
						+ "LIMIT :pageSize OFFSET :offset ";

		log.info(":: getLandApplicantsWithDetailsSearchFunction()  Query Execution Sucess..!!");
		int offset = (pageNumber - 1) * pageSize;

		Query query = entityManager.createNativeQuery(sql).setParameter("pageSize", pageSize)
				.setParameter("offset", offset);

		List<LandApplicantDTO> resultList = new ArrayList<>();
		@SuppressWarnings("unchecked")
		List<Object[]> rows = query.getResultList();
		for (Object[] row : rows) {
			LandApplicantDTO dto = new LandApplicantDTO();
			dto.setLandApplicantId((BigInteger) row[0]);
			dto.setApplicantNo((String) row[1]);
			dto.setApplicantName((String) row[2]);
			dto.setMobileNo((String) row[3]);
			dto.setDistrictName((String) row[4]);
			dto.setTehsilName((String) row[5]);
			dto.setMouzaName((String) row[6]);
			dto.setKhataNo((String) row[7]);
			dto.setApplicationStatus((String) row[8]);
			dto.setActionOn((Date) row[9]);
			dto.setRemark((String) row[10]);

			resultList.add(dto);
		}
		log.info(":: getLandApplicantsWithDetails() --   Result Return Scucess..!!");
		return resultList;
	}

	// getLandApplicantsWithApproveDetails
	@Transactional
	public List<LandApplicantDTO> getLandApplicantsWithApproveDetails(Integer approvedUser,int pageNumber, int pageSize) {
		String sql = " SELECT "
				+ "        A.land_application_id, "
				+ "        A.application_no, "
				+ "        A.applicant_name, "
				+ "        A.mobile_no, "
				+ "        B.district_name, "
				+ "        C.tahasil_name, "
				+ "        D.village_name, "
				+ "        E.khata_no, "
				+ "        G.application_status, "
				+ "        F.action_on, "
				+ "        F.remark    "
				+ "    FROM "
				+ "        public.land_application A        "
				+ "    inner join "
				+ "        land_bank.district_master B               "
				+ "            ON A.district_code = B.district_code       "
				+ "    inner join "
				+ "        land_bank.tahasil_master C               "
				+ "            ON A.tehsil_code =C.tahasil_code       "
				+ "    inner join "
				+ "        land_bank.village_master D               "
				+ "            ON A.village_code = D.village_code        "
				+ "    inner join "
				+ "        land_bank.khatian_information E               "
				+ "            ON A.khatian_code = E.khatian_code       "
				+ "    inner join "
				+ "        land_application_approval F              "
				+ "            ON A.land_application_id =F.land_application_id         "
				+ "    inner join "
				+ "        m_application_status G               "
				+ "            on F.application_status_id=G.application_status_id   "
				+ "    where "
				+ "        F.approval_action_id =:approvedUser " 
				+ " ORDER BY A.land_application_id ASC "
				+ " LIMIT :pageSize OFFSET :offset";

		log.info(":: getLandApplicantsWithDetailsSearchFunction()  Query Execution Sucess..!!");
		int offset = (pageNumber - 1) * pageSize;

		Query query = entityManager.createNativeQuery(sql).setParameter("approvedUser", approvedUser).setParameter("pageSize", pageSize)
				.setParameter("offset", offset);

		List<LandApplicantDTO> resultList = new ArrayList<>();
		@SuppressWarnings("unchecked")
		List<Object[]> rows = query.getResultList();
		for (Object[] row : rows) {
			LandApplicantDTO dto = new LandApplicantDTO();
			dto.setLandApplicantId((BigInteger) row[0]);
			dto.setApplicantNo((String) row[1]);
			dto.setApplicantName((String) row[2]);
			dto.setMobileNo((String) row[3]);
			dto.setDistrictName((String) row[4]);
			dto.setTehsilName((String) row[5]);
			dto.setMouzaName((String) row[6]);
			dto.setKhataNo((String) row[7]);
			dto.setApplicationStatus((String) row[8]);
			dto.setActionOn((Date) row[9]);
			dto.setRemark((String) row[10]);

			resultList.add(dto);
		}
		log.info(":: getLandApplicantsWithApproveDetails() --   Result Return Scucess..!!");
		return resultList;

	}

	@Transactional
	public List<LandApplicantDTO> getLandApplicantsWithApproveDetailsSerchFunction(Integer approvedUser,
			String applicantName, int pageNumber, int pageSize, String applicantUniqueId, String plotKathaNO) {
		String sql = " SELECT "
				+ "        A.land_application_id, "
				+ "        A.application_no, "
				+ "        A.applicant_name, "
				+ "        A.mobile_no, "
				+ "        B.district_name, "
				+ "        C.tahasil_name, "
				+ "        D.village_name, "
				+ "        E.khata_no, "
				+ "        G.application_status, "
				+ "        F.action_on, "
				+ "        F.remark    "
				+ "    FROM "
				+ "        public.land_application A        "
				+ "    inner join "
				+ "        land_bank.district_master B               "
				+ "            ON A.district_code = B.district_code       "
				+ "    inner join "
				+ "        land_bank.tahasil_master C               "
				+ "            ON A.tehsil_code =C.tahasil_code       "
				+ "    inner join "
				+ "        land_bank.village_master D               "
				+ "            ON A.village_code = D.village_code        "
				+ "    inner join "
				+ "        land_bank.khatian_information E               "
				+ "            ON A.khatian_code = E.khatian_code       "
				+ "    inner join "
				+ "        land_application_approval F              "
				+ "            ON A.land_application_id =F.land_application_id         "
				+ "    inner join "
				+ "        m_application_status G               "
				+ "            on F.application_status_id=G.application_status_id "
				+ "  where F.approval_action_id = :approvedUser " + " AND  A.applicant_name ILIKE :applicantName  "
				+ " AND A.application_no ILIKE :applicantUniqueId  " + " AND E.khata_no ILIKE :plotKathaNO "
				+ " ORDER BY A.land_application_id ASC" + " LIMIT :pageSize OFFSET :offset";

		log.info(":: getLandApplicantsWithDetailsSearchFunction()  Query Execution Sucess..!!");

		int offset = (pageNumber - 1) * pageSize;

		Query query = entityManager.createNativeQuery(sql)
				.setParameter("approvedUser", approvedUser)
				.setParameter("applicantName", "%" + applicantName + "%")
				.setParameter("applicantUniqueId", "%" + applicantUniqueId + "%")
				.setParameter("plotKathaNO", "%" + plotKathaNO + "%")
				.setParameter("pageSize", pageSize)
				.setParameter("offset", offset);

		List<LandApplicantDTO> resultList = new ArrayList<>();
		@SuppressWarnings("unchecked")
		List<Object[]> rows = query.getResultList();
		for (Object[] row : rows) {
			LandApplicantDTO dto = new LandApplicantDTO();
			dto.setLandApplicantId((BigInteger) row[0]);
			dto.setApplicantNo((String) row[1]);
			dto.setApplicantName((String) row[2]);
			dto.setMobileNo((String) row[3]);
			dto.setDistrictName((String) row[4]);
			dto.setTehsilName((String) row[5]);
			dto.setMouzaName((String) row[6]);
			dto.setKhataNo((String) row[7]);
			dto.setApplicationStatus((String) row[8]);
			dto.setActionOn((Date) row[9]);
			dto.setRemark((String) row[10]);

			resultList.add(dto);
		}
		log.info(":: getLandApplicantsWithApproveDetailsSerchFunction() --   Result Return Scucess..!!");
		return resultList;

	}

	// getLandApplicantsWithApproveDetails

	// getLandApplicantsWithRejectDetails
	@Transactional
	public List<LandApplicantDTO> getLandApplicantsWithRejectDetails(Integer approvalActionId,int pageNumber, int pageSize) {
		String sql = "  SELECT "
				+ "        A.land_application_id, "
				+ "        A.application_no, "
				+ "        A.applicant_name, "
				+ "        A.mobile_no, "
				+ "        B.district_name, "
				+ "        C.tahasil_name, "
				+ "        D.village_name, "
				+ "        E.khata_no, "
				+ "        G.application_status, "
				+ "        F.action_on, "
				+ "        F.remark  "
				+ "     FROM "
				+ "        public.land_application A        "
				+ "    inner join "
				+ "        land_bank.district_master B               "
				+ "            ON A.district_code = B.district_code       "
				+ "    inner join "
				+ "        land_bank.tahasil_master C               "
				+ "            ON A.tehsil_code =C.tahasil_code       "
				+ "    inner join "
				+ "        land_bank.village_master D               "
				+ "            ON A.village_code = D.village_code        "
				+ "    inner join "
				+ "        land_bank.khatian_information E               "
				+ "            ON A.khatian_code = E.khatian_code       "
				+ "    inner join "
				+ "        land_application_approval F              "
				+ "            ON A.land_application_id =F.land_application_id         "
				+ "    inner join "
				+ "        m_application_status G               "
				+ "            on F.application_status_id=G.application_status_id   "
				+ "    where "
				+ "        F.approval_action_id =  "
				+ ":approvalActionId " 
				+ " ORDER BY A.land_application_id ASC "
				+ "  LIMIT :pageSize OFFSET :offset";

		log.info(":: getLandApplicantsWithDetailsSearchFunction()  Query Execution Sucess..!!");
		int offset = (pageNumber - 1) * pageSize;

		Query query = entityManager.createNativeQuery(sql).setParameter("approvalActionId", approvalActionId).setParameter("pageSize", pageSize)
				.setParameter("offset", offset);
		

		List<LandApplicantDTO> resultList = new ArrayList<>();
		@SuppressWarnings("unchecked")
		List<Object[]> rows = query.getResultList();
		for (Object[] row : rows) {
			LandApplicantDTO dto = new LandApplicantDTO();
			dto.setLandApplicantId((BigInteger) row[0]);
			dto.setApplicantNo((String) row[1]);
			dto.setApplicantName((String) row[2]);
			dto.setMobileNo((String) row[3]);
			dto.setDistrictName((String) row[4]);
			dto.setTehsilName((String) row[5]);
			dto.setMouzaName((String) row[6]);
			dto.setKhataNo((String) row[7]);
			dto.setApplicationStatus((String) row[8]);
			dto.setActionOn((Date) row[9]);
			dto.setRemark((String) row[10]);

			resultList.add(dto);
		}
		log.info(":: getLandApplicantsWithRejectDetails() --   Result Return Scucess..!!");
		return resultList;

	}

	@Transactional
	public List<LandApplicantDTO> getLandApplicantsWithRejectDetailsSearchFunction(Integer approvalActionId,
			String applicantName, int pageNumber, int pageSize, String applicantUniqueId, String plotKathaNO) {
		String sql = " SELECT "
				+ "        A.land_application_id, "
				+ "        A.application_no, "
				+ "        A.applicant_name, "
				+ "        A.mobile_no, "
				+ "        B.district_name, "
				+ "        C.tahasil_name, "
				+ "        D.village_name, "
				+ "        E.khata_no, "
				+ "        G.application_status, "
				+ "        F.action_on, "
				+ "        F.remark  "
				+ "     FROM "
				+ "        public.land_application A        "
				+ "    inner join "
				+ "        land_bank.district_master B               "
				+ "            ON A.district_code = B.district_code       "
				+ "    inner join "
				+ "        land_bank.tahasil_master C               "
				+ "            ON A.tehsil_code =C.tahasil_code       "
				+ "    inner join "
				+ "        land_bank.village_master D               "
				+ "            ON A.village_code = D.village_code        "
				+ "    inner join "
				+ "        land_bank.khatian_information E               "
				+ "            ON A.khatian_code = E.khatian_code       "
				+ "    inner join "
				+ "        land_application_approval F              "
				+ "            ON A.land_application_id =F.land_application_id         "
				+ "    inner join "
				+ "        m_application_status G               "
				+ "            on F.application_status_id=G.application_status_id "
				+ " where F.approval_action_id = :approvalActionId " 
				+ " AND  A.applicant_name ILIKE :applicantName  "
				+ " AND A.application_no ILIKE :applicantUniqueId  " 
				+ " AND E.Khata_no ILIKE :plotKathaNO "
				+ " ORDER BY A.land_application_id ASC " 
				+ " LIMIT :pageSize OFFSET :offset ";

		log.info(":: getLandApplicantsWithDetailsSearchFunction()  Query Execution Sucess..!!");

		int offset = (pageNumber - 1) * pageSize;

		Query query = entityManager.createNativeQuery(sql)
				.setParameter("approvalActionId", approvalActionId)
				.setParameter("applicantName", "%" + applicantName + "%")
				.setParameter("applicantUniqueId", "%" + applicantUniqueId + "%")
				.setParameter("plotKathaNO", "%" + plotKathaNO + "%")
				.setParameter("pageSize", pageSize)
				.setParameter("offset", offset);

		List<LandApplicantDTO> resultList = new ArrayList<>();
		@SuppressWarnings("unchecked")
		List<Object[]> rows = query.getResultList();
		for (Object[] row : rows) {
			LandApplicantDTO dto = new LandApplicantDTO();
			dto.setLandApplicantId((BigInteger) row[0]);
			dto.setApplicantNo((String) row[1]);
			dto.setApplicantName((String) row[2]);
			dto.setMobileNo((String) row[3]);
			dto.setDistrictName((String) row[4]);
			dto.setTehsilName((String) row[5]);
			dto.setMouzaName((String) row[6]);
			dto.setKhataNo((String) row[7]);
			dto.setApplicationStatus((String) row[8]);
			dto.setActionOn((Date) row[9]);
			dto.setRemark((String) row[10]);

			resultList.add(dto);
		}
		log.info(":: getLandApplicantsWithRejectDetailsSearchFunction() --   Result Return Scucess..!!");

		return resultList;

	}

	// getLandApplicantsWithRejectDetails

	// getLandApplicantsRevertCitizenDetails
	@Transactional
	public List<LandApplicantDTO> getLandApplicantsRevertCitizenDetails(Integer revertToCitiZen,int pageNumber, int pageSize) {
		String sql = " SELECT "
				+ "        A.land_application_id, "
				+ "        A.application_no, "
				+ "        A.applicant_name, "
				+ "        A.mobile_no, "
				+ "        B.district_name, "
				+ "        C.tahasil_name, "
				+ "        D.village_name, "
				+ "        E.khata_no, "
				+ "        G.application_status, "
				+ "        F.action_on, "
				+ "        F.remark     "
				+ "		 FROM "
				+ "        public.land_application A        "
				+ "    inner join "
				+ "        land_bank.district_master B               "
				+ "            ON A.district_code = B.district_code       "
				+ "    inner join "
				+ "        land_bank.tahasil_master C               "
				+ "            ON A.tehsil_code =C.tahasil_code       "
				+ "    inner join "
				+ "        land_bank.village_master D               "
				+ "            ON A.village_code = D.village_code        "
				+ "    inner join "
				+ "        land_bank.khatian_information E               "
				+ "            ON A.khatian_code = E.khatian_code       "
				+ "    inner join "
				+ "        land_application_approval F              "
				+ "            ON A.land_application_id =F.land_application_id         "
				+ "    inner join "
				+ "        m_application_status G               "
				+ "            on F.application_status_id=G.application_status_id  "
				+ "			 where "
				+ "        F.approval_action_id = :revertToCitiZen   "
				+ "    ORDER BY "
				+ "        A.land_application_id  "
				+ " LIMIT :pageSize OFFSET :offset";

		log.info(":: getLandApplicantsWithDetailsSearchFunction()  Query Execution Sucess..!!");
		int offset = (pageNumber - 1) * pageSize;

		Query query = entityManager.createNativeQuery(sql)
				.setParameter("revertToCitiZen", revertToCitiZen)
				.setParameter("pageSize", pageSize)
				.setParameter("offset", offset);
		

		List<LandApplicantDTO> resultList = new ArrayList<>();
		@SuppressWarnings("unchecked")
		List<Object[]> rows = query.getResultList();
		for (Object[] row : rows) {
			LandApplicantDTO dto = new LandApplicantDTO();
			dto.setLandApplicantId((BigInteger) row[0]);
			dto.setApplicantNo((String) row[1]);
			dto.setApplicantName((String) row[2]);
			dto.setMobileNo((String) row[3]);
			dto.setDistrictName((String) row[4]);
			dto.setTehsilName((String) row[5]);
			dto.setMouzaName((String) row[6]);
			dto.setKhataNo((String) row[7]);
			dto.setApplicationStatus((String) row[8]);
			dto.setActionOn((Date) row[9]);
			dto.setRemark((String) row[10]);

			resultList.add(dto);
		}
		log.info(":: getLandApplicantsRevertCitizenDetails() --   Result Return Scucess..!!");
		return resultList;

	}

	@Transactional
	public List<LandApplicantDTO> getLandApplicantsRevertCitizenDetailsSearchFunction(Integer revertToCitiZen,
			String applicantName, int pageNumber, int pageSize, String applicantUniqueId, String plotKathaNO) {
		String sql = "SELECT "
				+ "        A.land_application_id, "
				+ "        A.application_no, "
				+ "        A.applicant_name, "
				+ "        A.mobile_no, "
				+ "        B.district_name, "
				+ "        C.tahasil_name, "
				+ "        D.village_name, "
				+ "        E.khata_no, "
				+ "        G.application_status, "
				+ "        F.action_on, "
				+ "        F.remark     "
				+ "		 FROM "
				+ "        public.land_application A        "
				+ "    inner join "
				+ "        land_bank.district_master B               "
				+ "            ON A.district_code = B.district_code       "
				+ "    inner join "
				+ "        land_bank.tahasil_master C               "
				+ "            ON A.tehsil_code =C.tahasil_code       "
				+ "    inner join "
				+ "        land_bank.village_master D               "
				+ "            ON A.village_code = D.village_code        "
				+ "    inner join "
				+ "        land_bank.khatian_information E               "
				+ "            ON A.khatian_code = E.khatian_code       "
				+ "    inner join "
				+ "        land_application_approval F              "
				+ "            ON A.land_application_id =F.land_application_id         "
				+ "    inner join "
				+ "        m_application_status G               "
				+ "            on F.application_status_id=G.application_status_id  "
				+ "	where F.approval_action_id =:revertToCitiZen " 
				+ " AND  A.applicant_name ILIKE :applicantName  "
				+ " AND A.application_no ILIKE :applicantUniqueId  " 
				+ " AND E.khata_no ILIKE :plotKathaNO "
				+ " ORDER BY A.land_application_id ASC  " 
				+ " LIMIT :pageSize OFFSET :offset";

		log.info(":: getLandApplicantsWithDetailsSearchFunction()  Query Execution Sucess..!!");

		int offset = (pageNumber - 1) * pageSize;

		Query query = entityManager.createNativeQuery(sql).setParameter("revertToCitiZen", revertToCitiZen)
				.setParameter("applicantName", "%" + applicantName + "%")
				.setParameter("applicantUniqueId", "%" + applicantUniqueId + "%")
				.setParameter("plotKathaNO", "%" + plotKathaNO + "%")
				.setParameter("pageSize", pageSize)
				.setParameter("offset", offset);

		List<LandApplicantDTO> resultList = new ArrayList<>();
		@SuppressWarnings("unchecked")
		List<Object[]> rows = query.getResultList();
		for (Object[] row : rows) {
			LandApplicantDTO dto = new LandApplicantDTO();
			dto.setLandApplicantId((BigInteger) row[0]);
			dto.setApplicantNo((String) row[1]);
			dto.setApplicantName((String) row[2]);
			dto.setMobileNo((String) row[3]);
			dto.setDistrictName((String) row[4]);
			dto.setTehsilName((String) row[5]);
			dto.setMouzaName((String) row[6]);
			dto.setKhataNo((String) row[7]);
			dto.setApplicationStatus((String) row[8]);
			dto.setActionOn((Date) row[9]);
			dto.setRemark((String) row[10]);

			resultList.add(dto);
		}

		log.info(":: getLandApplicantsRevertCitizenDetailsSearchFunction() --   Result Return Scucess..!!");
		return resultList;

	}

	// getLandApplicantsRevertCitizenDetails
}
