package com.csmtech.SJTA.repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.csmtech.SJTA.dto.ApplicantNumberAndMobileDTO;
import com.csmtech.SJTA.dto.LandIndividualAppDTO;
import com.csmtech.SJTA.dto.PlotDetails;

@Repository
public class LandPlotDetailsRepository {

	@PersistenceContext
	@Autowired
	private EntityManager entity;

	@Transactional
	public Integer updateApplicantRecord(LandIndividualAppDTO dto) {
		String queryString = "" + "update land_application " + "set district_code = :plot_district_id , "
				+ "    tehsil_code = :plot_tehsil_id , " + "    village_code = :plot_mouza_id , "
				+ "    khatian_code = :plot_khata_no_id  " + "where land_application_id = :applicantId";

		Query query = entity.createNativeQuery(queryString).setParameter("plot_district_id", dto.getSelDistrictName())
				.setParameter("plot_tehsil_id", dto.getSelTehsilName()).setParameter("plot_mouza_id", dto.getSelMouza())
				.setParameter("plot_khata_no_id", dto.getSelKhataNo())
				.setParameter("applicantId", dto.getIntLandApplicantId());
		return query.executeUpdate();
	}

	@Transactional
	public Integer batchUpdateDeleteNocPlots(LandIndividualAppDTO nocPlot) {
		String insertQuery = "insert into public.land_schedule "
				+ " (plot_code,total_area,purchase_area,created_by,land_application_id)  "
				+ " values "
				+ "(:plot_no_id,:total_area,:purchase_area,:created_by,:land_applicant_id)";

		// Set the batch size to improve performance (optional)

		int batchSize = 50;
		List<PlotDetails> plotDetailsList = nocPlot.getPlot_details();
		for (int i = 0; i < plotDetailsList.size(); i++) {
			PlotDetails plotDetails = plotDetailsList.get(i);
			entity.createNativeQuery(insertQuery)
			.setParameter("plot_no_id", plotDetails.getPlot_id())
			.setParameter("total_area", plotDetails.getTotal_area())
			.setParameter("purchase_area", plotDetails.getPurchase_area())
			.setParameter("created_by", nocPlot.getIntCreatedBy())
			.setParameter("land_applicant_id", nocPlot.getIntLandApplicantId())
			.executeUpdate();
			if (i % batchSize == 0) {
				entity.flush();
			entity.clear();

			}
		}
		return batchSize;

	}

	
	@Transactional
	 public LandIndividualAppDTO getLandIndividualAppDTOByLandApplicantId(Integer landApplicantId) {
		String sqlQuery = " SELECT "
        		+ "        p.district_code as selDistrictName, "
        		+ "        p.tehsil_code as selTehsilName, "
        		+ "        p.village_code as selMouza, "
        		+ "        p.khatian_code as selKhataNo, "
        		+ "        pp.plot_code as selPlotNo, "
        		+ "        pp.total_area as txtTotalRakba, "
        		+ "        pp.purchase_area as txtPurchaseRakba, "
        		+ "        mp.plot_no as plot_name  "
        		+ "    FROM "
        		+ "        public.land_application p  "
        		+ "    JOIN "
        		+ "        public.land_schedule pp  "
        		+ "            ON ( "
        		+ "                pp.land_application_id = p.land_application_id "
        		+ "            )  "
        		+ "     "
        		+ "    JOIN "
        		+ "       land_bank.plot_information mp  "
        		+ "            ON ( "
        		+ "                mp.plot_code = pp.plot_code "
        		+ "            )  "
        		+ "    WHERE "
        		+ "        p.land_application_id =:landApplicantId";

        @SuppressWarnings("unchecked")
		List<Object[]> resultList = entity.createNativeQuery(sqlQuery)
                                                .setParameter("landApplicantId", landApplicantId)
                                                .getResultList();

        LandIndividualAppDTO landIndividualAppDTO = new LandIndividualAppDTO();
        List<PlotDetails> plotDetailsList = new ArrayList<>();

        for (Object[] result : resultList) {
            landIndividualAppDTO.setSelDistrictName(((String) result[0]));
            landIndividualAppDTO.setSelTehsilName(((String) result[1]));
            landIndividualAppDTO.setSelMouza(((String) result[2]));
            landIndividualAppDTO.setSelKhataNo(((String) result[3]));
           // landIndividualAppDTO.setSelPlotNo(((BigInteger) result[5]).intValue());
           // landIndividualAppDTO.setTxtTotalRakba((String) result[6]);
           // landIndividualAppDTO.setTxtPurchaseRakba((String) result[7]);
           // landIndividualAppDTO.setSelVarieties(((BigInteger) result[8]).intValue());

            PlotDetails plotDetails = new PlotDetails();
            plotDetails.setPlot_id(((String) result[4]));
            plotDetails.setPlot_name((String) result[7]);
            plotDetails.setTotal_area((BigDecimal) result[5]);
            plotDetails.setPurchase_area((BigDecimal) result[6]);
//            plotDetails.setVariety_id(((String) result[8]));
//            plotDetails.setVariety_name((String) result[4]);

            plotDetailsList.add(plotDetails);
        }

        landIndividualAppDTO.setPlot_details(plotDetailsList);
        return landIndividualAppDTO;
    }

	@Transactional
	public Integer updateApplicantName(BigInteger applicantId, String newAppName) {
		String nativeUpdateQuery = "UPDATE land_application SET application_no = :newAppName WHERE land_application_id = :applicantId";

		return entity.createNativeQuery(nativeUpdateQuery).setParameter("newAppName", newAppName)
				.setParameter("applicantId", applicantId).executeUpdate();
	}

	// returive applicant no or mobile no
	@Transactional
	public List<ApplicantNumberAndMobileDTO> fetchApplicantInfoById(BigInteger i) {
		String nativeQuery = "SELECT application_no, mobile_no FROM land_application WHERE land_application_id = :applicantId";

		Query query = entity.createNativeQuery(nativeQuery);
		query.setParameter("applicantId", i);

		@SuppressWarnings("unchecked")
		List<Object[]> results = query.getResultList();
		List<ApplicantNumberAndMobileDTO> applicantInfoList = mapToDTOList(results);

		return applicantInfoList;
	}

	private List<ApplicantNumberAndMobileDTO> mapToDTOList(List<Object[]> resultList) {
		// Create a list to hold DTO objects
		List<ApplicantNumberAndMobileDTO> applicantInfoList = new ArrayList<>();

		for (Object[] result : resultList) {
			String applicantNo = (String) result[0];
			String mobileNo = (String) result[1];

			ApplicantNumberAndMobileDTO dto = new ApplicantNumberAndMobileDTO();
			dto.setApplicantNo(applicantNo);
			dto.setMobileNo(mobileNo);

			applicantInfoList.add(dto);
		}

		return applicantInfoList;
	}

}
