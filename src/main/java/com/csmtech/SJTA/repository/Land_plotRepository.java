package com.csmtech.SJTA.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.csmtech.SJTA.entity.Land_plot;

@Repository
public interface Land_plotRepository extends JpaRepository<Land_plot, Integer> {
	Land_plot findByIntIdAndBitDeletedFlag(Integer intId, Boolean bitDeletedFlag);

	@Query("From Land_plot where deleted_flag='0' ")
	List<Land_plot> findAllByBitDeletedFlag();

	Land_plot findByIntLandApplicantIdAndBitDeletedFlag(Integer intLandApplicantId, Boolean bitDeletedFlag);

	Land_plot deleteByIntLandApplicantIdAndBitDeletedFlag(Integer intLandApplicantId, Boolean bitDeletedFlag);
}