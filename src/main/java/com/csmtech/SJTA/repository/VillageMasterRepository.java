package com.csmtech.SJTA.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.csmtech.SJTA.entity.VillageMaster;

public interface VillageMasterRepository extends JpaRepository<VillageMaster, String> {

	List<VillageMaster> findAllByTahasilCode(String tahasilCode);

}
