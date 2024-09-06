package com.csmtech.SJTA.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.csmtech.SJTA.entity.LandApplicant;

public interface LandApplicantRepo extends JpaRepository<LandApplicant, BigInteger> {

	@Query(value = "SELECT \r\n" + "    la.land_applicant_id,\r\n" + "	la.applicant_no,\r\n"
			+ "    la.applicant_name,\r\n" + "    la.father_name,\r\n" + "    la.mobile_no,\r\n"
			+ "    la.email_address,\r\n" + "    la.doc_ref_no,\r\n" + "    la.docs_path,\r\n"
			+ "    la.curr_police_station,\r\n" + "    la.curr_post_office,\r\n" + "    la.curr_street_no,\r\n"
			+ "    la.curr_house_no,\r\n" + "    la.curr_pin_code,   \r\n" + "    la.pre_police_station,\r\n"
			+ "    la.pre_post_office,\r\n" + "    la.pre_street_no,\r\n" + "    la.pre_house_no,\r\n"
			+ "    la.pre_pin_code,\r\n" + "    la.doc_name,\r\n" + "    mb.block_name,\r\n"
			+ "    md.district_name,   \r\n" + "    mdt.doc_name,\r\n" + "    mgp.gp_name,\r\n"
			+ "    mkn.khata_no,  \r\n" + "    mm.mouza_name,\r\n" + "    ms.state_name,  \r\n"
			+ "    mt.tehsil_name  \r\n" + " FROM public.land_applicant la\r\n"
			+ "LEFT JOIN public.m_block mb ON la.curr_block_id = mb.block_id\r\n"
			+ "LEFT JOIN public.m_district md ON la.curr_district_id = md.district_id\r\n"
			+ "LEFT JOIN public.m_document_type mdt ON la.doc_type = mdt.doc_type_id\r\n"
			+ "LEFT JOIN public.m_gp mgp ON la.curr_gp_id = mgp.gp_id\r\n"
			+ "LEFT JOIN public.m_khata_no mkn ON la.plot_khata_no_id = mkn.khata_no_id\r\n"
			+ "LEFT JOIN public.m_mouza mm ON la.plot_mouza_id = mm.mouza_id\r\n"
			+ "LEFT JOIN public.m_state ms ON la.curr_state_id = ms.state_id\r\n"
			+ "LEFT JOIN public.m_tehsil mt ON la.curr_district_id = mt.district_id AND la.curr_block_id = mt.tehsil_id\r\n"
			+ "where la.land_applicant_id =:landApplicantId AND la.deleted_flag='false' "
			+ "ORDER BY la.applicant_no;", nativeQuery = true)
	LandApplicant getDetails(BigInteger landApplicantId);

	
}
