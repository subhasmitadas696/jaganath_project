package com.csmtech.SJTA.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.csmtech.SJTA.entity.MouzaEntity;

public interface MouzaRepository extends JpaRepository<MouzaEntity, Long> {

	List<MouzaEntity> findAllByTehsilId(Long tehsilId);

}
