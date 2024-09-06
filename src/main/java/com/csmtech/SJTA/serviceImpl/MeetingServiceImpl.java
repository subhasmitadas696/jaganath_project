package com.csmtech.SJTA.serviceImpl;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csmtech.SJTA.dto.MeetingScheduleDTO;
import com.csmtech.SJTA.entity.DistrictMaster;
import com.csmtech.SJTA.entity.KhatianInformation;
import com.csmtech.SJTA.entity.MeetingLevel;
import com.csmtech.SJTA.entity.PlotInformation;
import com.csmtech.SJTA.entity.StateMaster;
import com.csmtech.SJTA.entity.TahasilMaster;
import com.csmtech.SJTA.entity.VillageMaster;
import com.csmtech.SJTA.repository.DistrictMasterRepository;
import com.csmtech.SJTA.repository.KhatianInformationRepository;
import com.csmtech.SJTA.repository.MeetingLevelRepository;
import com.csmtech.SJTA.repository.MeetingScheduleNativeRepository;
import com.csmtech.SJTA.repository.PlotInformationRepository;
import com.csmtech.SJTA.repository.StateRepository;
import com.csmtech.SJTA.repository.TahasilMasterRepository;
import com.csmtech.SJTA.repository.VillageMasterRepository;
import com.csmtech.SJTA.service.MeetingService;

@Service
public class MeetingServiceImpl implements MeetingService {

	@Autowired
	private StateRepository stateRepo;

	@Autowired
	private DistrictMasterRepository distRepo;

	@Autowired
	private TahasilMasterRepository tahasilRepo;

	@Autowired
	private VillageMasterRepository villageRepo;

	@Autowired
	private KhatianInformationRepository khatianRepo;

	@Autowired
	private PlotInformationRepository plotRepo;

	@Autowired
	private MeetingLevelRepository meetingLevelRepo;

	@Autowired
	private MeetingScheduleNativeRepository meetingScheduleRepo;

	@Override
	public List<StateMaster> getAllStates() {
		return stateRepo.findAll();
	}

	@Override
	public List<DistrictMaster> getAllDistrict() {
		return distRepo.findAll();
	}

	@Override
	public List<TahasilMaster> getAllTahasil(String districtCode) {
		return tahasilRepo.findAllByDistrictCode(districtCode);
	}

	@Override
	public List<VillageMaster> getAllVillage(String tahasilCode) {
		return villageRepo.findAllByTahasilCode(tahasilCode);
	}

	@Override
	public List<KhatianInformation> getAllKhatian(String villageCode) {
		return khatianRepo.findAllByVillageCode(villageCode);
	}

	@Override
	public List<PlotInformation> getAllPlot(String khatianCode) {
		return plotRepo.findAllByKhatianCode(khatianCode);
	}

	@Override
	public List<MeetingLevel> getAllMeetingLevel() {
		return meetingLevelRepo.findAll();
	}

	@Override
	public Integer registerMeeting(MeetingScheduleDTO meetingDTO) {
		String districtCode = meetingDTO.getDistrictCode();
		String tahasilCode = meetingDTO.getTahasilCode();
		String villageCode = meetingDTO.getVillageCode();
		String khatianCode = meetingDTO.getKhatianCode();
		String plotCode = meetingDTO.getPlotCode();
		Date meetingDate = meetingDTO.getMeetingDate();
		String meetingPurpose = meetingDTO.getMeetingPurpose();
		Short meetingLevelId = meetingDTO.getMeetingLevelId();
		BigInteger createdBy = meetingDTO.getCreatedBy();

		return meetingScheduleRepo.savemeeting(districtCode, tahasilCode, villageCode, khatianCode, plotCode,
				meetingDate, meetingPurpose, meetingLevelId, createdBy);

	}

}
