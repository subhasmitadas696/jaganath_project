package com.csmtech.SJTA.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.csmtech.SJTA.entity.TehsilEntity;

public interface TehsilRepository extends JpaRepository<TehsilEntity, Long> {

	List<TehsilEntity> findAllByDistrictId(Long districtId);

}
