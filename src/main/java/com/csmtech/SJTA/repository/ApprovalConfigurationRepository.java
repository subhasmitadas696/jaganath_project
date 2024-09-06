package com.csmtech.SJTA.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.csmtech.SJTA.entity.ApprovalConfigurationEntity;

@Repository
public interface ApprovalConfigurationRepository extends JpaRepository<ApprovalConfigurationEntity, Short> {
}

