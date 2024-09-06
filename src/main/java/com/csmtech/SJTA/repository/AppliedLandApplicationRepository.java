/**
 * 
 */
package com.csmtech.SJTA.repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.csmtech.SJTA.dto.ApplicantDTO;
import com.csmtech.SJTA.dto.AppliedLandApplicationDTO;

/**
 * 
 */
@Repository
public class AppliedLandApplicationRepository{
	@PersistenceContext
	@Autowired
	private EntityManager entity;
	
	@Transactional
	public AppliedLandApplicationDTO getAppliedLandApplication(BigInteger createdBy) {
        String nativeQuery = "SELECT la.applicant_no,la.applicant_name,d.district_name,t.tehsil_name,m.mouza_name,k.khata_no,p.plot_no,la.created_on,md.doc_name"
        		+ "FROM land_applicant la LEFT JOIN land_plot lp ON la.land_applicant_id=lp.land_applicant_id"
        		+ "JOIN m_district d ON lp.district_id=d.district_id"
        		+ "JOIN m_tehsil t ON lp.tehsil_id=t.tehsil_id"
        		+ "JOIN m_mouza m ON lp.mouza_id=m.mouza_id"
        		+ "JOIN m_khata_no k ON lp.khata_no_id=k.khata_no_id"
        		+ "JOIN m_plot_no p ON lp.plot_no_id=p.plot_no_id"
        		+ "JOIN m_document_type md ON la.doc_type=md.doc_type_idn"
        		+ "WHERE la.deleted_flag=false AND la.created_by=:createdBy";

        @SuppressWarnings("unchecked")
		List<Object[]> resultList = entity.createNativeQuery(nativeQuery)
                .setParameter("createdBy", createdBy)
                .getResultList();

        if (resultList.isEmpty()) {
            //  where no result is found
            return null;
        }
        entity.close();
        Object[] result = resultList.get(0);

        // Extract the fields and create the DTO
        String applicantName = (String) result[0];
        String fatherName = (String) result[1];
        String mobileNo = (String) result[2];
        String emailAddress = (String) result[3];
        BigInteger docType = (BigInteger) result[4];
        String docRefNo = (String) result[5];
        String docsPath = (String) result[6];
        BigInteger currStateId = (BigInteger) result[7];
        BigInteger currDistrictId = (BigInteger) result[8];
        BigInteger currBlockId = (BigInteger) result[9];
        BigInteger currGpId = (BigInteger) result[10];
        BigInteger currVillageId = (BigInteger) result[11];
        String currPoliceStation = (String) result[12];
        String currPostOffice = (String) result[13];
        String currStreetNo = (String) result[14];
        String currHouseNo = (String) result[15];
        String currPinCode = (String) result[16];
        BigInteger perStateId = (BigInteger) result[17];
        BigInteger perDistrictId = (BigInteger) result[18];
        BigInteger perBlockId = (BigInteger) result[19];
        BigInteger perGpId = (BigInteger) result[20];
        BigInteger perVillageId = (BigInteger) result[21];
        String perPoliceStation = (String) result[22];
        String perPostOffice = (String) result[23];
        String perStreetNo = (String) result[24];
        String perHouseNo = (String) result[25];
        String perPinCode = (String) result[26];
        entity.close();
		return null;

//        return new AppliedLandApplicationDTO(applicantName, fatherName, mobileNo, emailAddress,
//                docType, docRefNo, docsPath,
//                currStateId, currDistrictId, currBlockId,
//                currGpId, currVillageId, currPoliceStation,
//                currPostOffice, currStreetNo, currHouseNo,
//                currPinCode, perStateId, perDistrictId,
//                perBlockId, perGpId, perVillageId,
//                perPoliceStation, perPostOffice, perStreetNo,
//                perHouseNo, perPinCode);
    }
	
}
