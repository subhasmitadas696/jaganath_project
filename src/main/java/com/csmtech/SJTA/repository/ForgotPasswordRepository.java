package com.csmtech.SJTA.repository;

/**
 * @author prasanta.sethi
 */

import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.csmtech.SJTA.entity.LandAppRegistratationEntity;

@Repository
public interface ForgotPasswordRepository extends JpaRepository<LandAppRegistratationEntity, Serializable>  {

	LandAppRegistratationEntity findBymobileno(String mobileNo);

	

}
