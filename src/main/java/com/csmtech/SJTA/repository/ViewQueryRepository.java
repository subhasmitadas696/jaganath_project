package com.csmtech.SJTA.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import com.csmtech.SJTA.entity.QueryEntity;

/**
 * @author prasanta.sethi
 */

public interface ViewQueryRepository extends JpaRepository<QueryEntity, BigInteger> {

}
