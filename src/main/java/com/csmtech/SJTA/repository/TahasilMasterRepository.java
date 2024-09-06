package com.csmtech.SJTA.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.csmtech.SJTA.entity.TahasilMaster;

public interface TahasilMasterRepository extends JpaRepository<TahasilMaster, String> {

	List<TahasilMaster> findAllByDistrictCode(String districtCode);

}
