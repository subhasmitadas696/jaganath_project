package com.csmtech.SJTA.serviceImpl;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.csmtech.SJTA.dto.AddOfficerDTO;
import com.csmtech.SJTA.dto.UserDetails;
import com.csmtech.SJTA.entity.Department;
import com.csmtech.SJTA.entity.MRoleEntity;
import com.csmtech.SJTA.entity.RoleInfo;
import com.csmtech.SJTA.entity.User;
import com.csmtech.SJTA.repository.DepartmentRepository;
import com.csmtech.SJTA.repository.OfficerRegistrationNativeRepo;
import com.csmtech.SJTA.repository.OfficerRegistrationRepository;
import com.csmtech.SJTA.repository.RoleRepository;
import com.csmtech.SJTA.service.OfficerRegistrationService;
import com.csmtech.SJTA.util.OtpGenerateCommonUtil;

@Service
public class OfficerRegistrationServiceImpl implements OfficerRegistrationService {

	@Value("${sjta.bcryptpassword.secretKey}")
	private String secretkey;

	@Autowired
	private OfficerRegistrationNativeRepo officeRegisterRepo;

	@Autowired
	private OfficerRegistrationRepository officerRepo;

	@Autowired
	private DepartmentRepository deptRepo;

	@Autowired
	private RoleRepository roleRepo;

	@Override
	public Integer checkExistingRecord(String mobileNo) {
		return officeRegisterRepo.checkExistingRecord(mobileNo);
	}

	@Override
	public Integer registerOfficer(UserDetails officerDTO) {

		String username = officerDTO.getMobileNo();
		String password = officerDTO.getPassword();
		String fullName = officerDTO.getFullName();
		String mobileNo = officerDTO.getMobileNo();
		String email = officerDTO.getEmailId();
		String otp = OtpGenerateCommonUtil.generateOTP();
		BigInteger createdBy = officerDTO.getCreatedBy();
		BigInteger departmentId = officerDTO.getDepartmentId();
		BigInteger roleId = officerDTO.getRoleId();

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedValue = encoder.encode(secretkey + password);

		String userType = "O";
		return officeRegisterRepo.insertUserWithRole(username, encodedValue, fullName, mobileNo, email, otp, userType,
				createdBy, departmentId, roleId);

	}

	@Override
	public List<MRoleEntity> getAll() {
		return officerRepo.findAll();
	}

	@Override
	public List<User> viewOfficer() {
		return officerRepo.viewOfficer();
	}

	@Override
	public Integer updateDetails(String newfullName, String newmobileNo, String newemailId, BigInteger departmentId,
			BigInteger roleId, BigInteger newupdatedBy, BigInteger userId, BigInteger createdBy) {

		return officeRegisterRepo.updatedetails(newfullName, newmobileNo, newemailId, departmentId, roleId,
				newupdatedBy, userId, createdBy);
	}

	@Override
	public Integer deleteRecord(BigInteger createdBy, BigInteger userId) {
		return officeRegisterRepo.deleteRecord(createdBy, userId);
	}

	@Override
	public Integer getTotalOfficerCount() {
		return officeRegisterRepo.getTotalOfficerCount();
	}

	@Override
	public List<AddOfficerDTO> getOfficerInfo(Integer pageNumber, Integer pageSize, String fullName) {
		return officeRegisterRepo.getOfficers(pageNumber, pageSize, fullName);
	}

	@Override
	public List<Department> getAllDept() {
		return deptRepo.findAll();
	}

	@Override
	public List<MRoleEntity> getAllRoleByDepartment(Long departmentId) {
		return roleRepo.findAllByDepartmentId(departmentId);
	}

}
