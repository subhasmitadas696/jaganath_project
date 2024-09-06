package com.csmtech.SJTA.serviceImpl;

/**
 * @Auth Rashmi Ranjan Jena
 */

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.csmtech.SJTA.entity.LandAppRegistratationEntity;
import com.csmtech.SJTA.repository.LandAppRegistratationRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LandAppLoginServiceImpl implements UserDetailsService {

	@Autowired
	private LandAppRegistratationRepository regrepository;

	@Override
	public UserDetails loadUserByUsername(String mobileno) throws UsernameNotFoundException {
		log.info("::  execution start of loadUserByUsername method");
		LandAppRegistratationEntity entity = regrepository.findBymobileno(mobileno);
		log.info(":: execution end of loadUserByUsername method return to controller");
		return new org.springframework.security.core.userdetails.User(entity.getMobileno(), entity.getPassword(),
				new ArrayList<>());
	}

}
