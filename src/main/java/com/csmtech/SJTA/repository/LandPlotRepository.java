package com.csmtech.SJTA.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.csmtech.SJTA.entity.LandPlot;

public interface LandPlotRepository extends JpaRepository<LandPlot, BigInteger> {

//	@Query(value = "select lp.land_applicant_plot_id, lp.total_area,lp.purchase_area,v.varieties_name,lp.deleted_flag from land_plot lp LEFT JOIN m_varieties v ON lp.varieties_id=v.varieties_id where land_applicant_id =:intId AND lp.deleted_flag='false' ", nativeQuery = true)
//	List<LandPlot> findByIntLandApplicantId(BigInteger landApplicantId);

	List<LandPlot> findByLandApplicantId(BigInteger landApplicantId);

}
