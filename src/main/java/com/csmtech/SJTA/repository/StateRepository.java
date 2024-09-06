package com.csmtech.SJTA.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.csmtech.SJTA.entity.StateMaster;

public interface StateRepository extends JpaRepository<StateMaster, String> {

}
