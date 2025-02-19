package com.csmtech.SJTA.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.csmtech.SJTA.entity.TenderAndAdvertizeEntity;

public interface TenderAndAdvertizeRepository extends JpaRepository<TenderAndAdvertizeEntity, BigInteger> {

	TenderAndAdvertizeEntity findByTenderAdvertisementIdAndStatus(BigInteger tenderAdvertisementId, boolean status);

	List<TenderAndAdvertizeEntity> findAllByStatusFalse();

}
