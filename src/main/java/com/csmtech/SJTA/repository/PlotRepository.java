package com.csmtech.SJTA.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.csmtech.SJTA.entity.PlotNoEntity;

public interface PlotRepository extends JpaRepository<PlotNoEntity, Long> {

	List<PlotNoEntity> findAllByKhataNoId(Long khataNoId);

}
