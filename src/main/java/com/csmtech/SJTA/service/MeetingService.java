package com.csmtech.SJTA.service;

import java.util.List;

import com.csmtech.SJTA.dto.MeetingScheduleDTO;
import com.csmtech.SJTA.entity.DistrictMaster;
import com.csmtech.SJTA.entity.KhatianInformation;
import com.csmtech.SJTA.entity.MeetingLevel;
import com.csmtech.SJTA.entity.PlotInformation;
import com.csmtech.SJTA.entity.StateMaster;
import com.csmtech.SJTA.entity.TahasilMaster;
import com.csmtech.SJTA.entity.VillageMaster;

public interface MeetingService {

	List<StateMaster> getAllStates();

	List<DistrictMaster> getAllDistrict();

	List<TahasilMaster> getAllTahasil(String districtCode);

	List<VillageMaster> getAllVillage(String tahasilCode);

	List<KhatianInformation> getAllKhatian(String villageCode);

	List<PlotInformation> getAllPlot(String khatianCode);

	List<MeetingLevel> getAllMeetingLevel();

	Integer registerMeeting(MeetingScheduleDTO meetingDTO);

}
