package com.csmtech.SJTA.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.csmtech.SJTA.entity.KhatianInformation;

public interface KhatianInformationRepository extends JpaRepository<KhatianInformation, String> {

	List<KhatianInformation> findAllByVillageCode(String villageCode);

}
