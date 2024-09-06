package com.csmtech.SJTA.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.csmtech.SJTA.entity.LandDocuments;

public interface LandDocumentsRepository extends JpaRepository<LandDocuments, BigInteger> {

	List<LandDocuments> findByLandApplicantId(BigInteger landApplicantId);

}
