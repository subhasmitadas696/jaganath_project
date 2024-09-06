package com.csmtech.SJTA.serviceImpl;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.csmtech.SJTA.dto.UserDetails;
import com.csmtech.SJTA.entity.RoleInfo;
import com.csmtech.SJTA.entity.User;
import com.csmtech.SJTA.repository.OfficerRegistrationNativeRepo;
import com.csmtech.SJTA.repository.UserDetailsNativeRepository;
import com.csmtech.SJTA.repository.UserRepository;
import com.csmtech.SJTA.service.UserService;
import com.csmtech.SJTA.util.OtpGenerateCommonUtil;

/*
 * @Auth  GuruPrasad
 */

@Service
public class UserServiceImpl implements UserService {

	@Value("${sjta.bcryptpassword.secretKey}")
	private String secretkey;

	@Autowired
	private UserRepository userDetailsRepository;

	@Autowired
	private UserDetailsNativeRepository usernativeRepo;
	
	@Autowired
	private OfficerRegistrationNativeRepo officeRegisterRepo;

	@Override
	public User findByUserName(String userName) {
		return userDetailsRepository.findByUserName(userName);
	}

	@Override
	public User save(User user) {
		return userDetailsRepository.save(user);
	}

	@Override
	public User findBymobileNo(String mobileNo) {
		return userDetailsRepository.findBymobileNo(mobileNo);
	}

	@Override
	public User findByuserId(BigInteger userId) {
//		return userDetailsRepository.findByuserIdFalse(userId);
		return officeRegisterRepo.findDataById(userId);
	}

	@Override
	public Integer updateDetails(String newFullName, String newemailId, BigInteger newUpdatedBy, BigInteger userId) {
		return usernativeRepo.updateDetails(newFullName, newemailId, newUpdatedBy, userId);
	}

	@Override
	public Integer updateUser(String encodedPassword, String newUpdatedBy, BigInteger userId) {
		return usernativeRepo.updateUser(encodedPassword, newUpdatedBy, userId);
	}

	@Override
	public User findByuserIdFalse(BigInteger userId) {
		return userDetailsRepository.findByuserIdFalse(userId);
	}

	
}
