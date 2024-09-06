package com.csmtech.SJTA.repository;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.csmtech.SJTA.entity.Meeting_schedule;

@Repository

public class MeetingScheduleNativeRepository {

	@PersistenceContext
	EntityManager entityManager;

	@Transactional
	public Integer savemeeting(String districtCode, String tahasilCode, String villageCode, String khatianCode,
			String plotCode, Date meetingDate, String meetingPurpose, Short meetingLevelId, BigInteger createdBy) {
		String query = "    INSERT INTO application.meeting_schedule (district_code, tahasil_code, village_code, khatian_code, plot_code, meeting_date, meeting_purpose,meeting_level_id, created_by, created_on) "
				+ "    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP) ";

		return entityManager.createNativeQuery(query).setParameter(1, districtCode).setParameter(2, tahasilCode)
				.setParameter(3, villageCode).setParameter(4, khatianCode).setParameter(5, plotCode)
				.setParameter(6, meetingDate).setParameter(7, meetingPurpose).setParameter(8, meetingLevelId)
				.setParameter(9, createdBy.longValue()).executeUpdate();
	}

	public Meeting_schedule updateRecord(Meeting_schedule meeting_schedule) {

		Date currentDate = new Date();
		String nativeQuery = "UPDATE application.meeting_schedule SET " + "meeting_date = :meetingDate, "
				+ "meeting_purpose =:meetingPurpose, " + "meeting_level_id =:meetingLevelId, "
				+ "updated_by =:updatedBy, " + "updated_on =:updatedOn "
				+ "WHERE meeting_schedule_id = :meetingScheduleId";

		entityManager.createNativeQuery(nativeQuery)
//				.setParameter("districtCode", meeting_schedule.getSelDistrict48())
//				.setParameter("tahasilCode", meeting_schedule.getSelTahasil49())
//
//				.setParameter("villageCode", meeting_schedule.getSelVillage50())
//				.setParameter("khatianCode", meeting_schedule.getSelKhatian51())
//				.setParameter("plotCode", meeting_schedule.getSelPlot52())

				.setParameter("meetingDate", meeting_schedule.getTxtMeetingDate53())
				.setParameter("meetingPurpose", meeting_schedule.getTxtrMeetingPurpose54())
				.setParameter("meetingLevelId", meeting_schedule.getSelMeetingLevel55())

				.setParameter("updatedBy", meeting_schedule.getIntUpdatedBy().longValue())

				.setParameter("updatedOn", currentDate)

				.setParameter("meetingScheduleId", meeting_schedule.getIntId()).executeUpdate();

		return meeting_schedule;
	}

	public Meeting_schedule updateFile(Meeting_schedule meeting_schedule) {

		Date currentDate = new Date();
		String nativeQuery = "UPDATE application.meeting_schedule SET " + "upload_mom =:upload_mom, "
				+ "updated_by =:updatedBy, " + "updated_on =:updatedOn "
				+ "WHERE meeting_schedule_id = :meetingScheduleId";

		entityManager.createNativeQuery(nativeQuery)
				.setParameter("upload_mom", meeting_schedule.getFileUploadDocument())

				.setParameter("updatedBy", meeting_schedule.getIntUpdatedBy().longValue())

				.setParameter("updatedOn", currentDate)

				.setParameter("meetingScheduleId", meeting_schedule.getIntId().longValue()).executeUpdate();

		return meeting_schedule;
	}

	public Integer saveRecord(Meeting_schedule meeting_schedule) {
		String sqlQuery = "INSERT INTO application.meeting_schedule ( "
				+ " meeting_date, meeting_purpose,meeting_level_id, created_by " + " ) VALUES ("
				+ "  :meetingDate, :meetingPurpose, :meetingLevelId, :createdBy  " + " ) "
				+ " RETURNING meeting_schedule_id";

		Query query = entityManager.createNativeQuery(sqlQuery)
//				.setParameter("districtCode", meeting_schedule.getSelDistrict48())
//				.setParameter("tahasilCode", meeting_schedule.getSelTahasil49())
//
//				.setParameter("villageCode", meeting_schedule.getSelVillage50())
//				.setParameter("khatianCode", meeting_schedule.getSelKhatian51())
//				.setParameter("plotCode", meeting_schedule.getSelPlot52())

				.setParameter("meetingDate", meeting_schedule.getTxtMeetingDate53())
				.setParameter("meetingPurpose", meeting_schedule.getTxtrMeetingPurpose54())
				.setParameter("meetingLevelId", meeting_schedule.getSelMeetingLevel55())

				.setParameter("createdBy", meeting_schedule.getIntCreatedBy().longValue());

		BigInteger result = (BigInteger) query.getSingleResult();
		return result.intValue();
	}

	@Transactional
	public Integer deleteRecord(Integer id, Integer updatedby) {
		entityManager.createNativeQuery("SET CONSTRAINTS ALL DEFERRED").executeUpdate();

		Date currentDate = new Date();
		String nativeQuery = "UPDATE application.meeting_schedule SET status = CAST(:setstatus AS BIT), updated_on =:updatedOn, updated_by =:updatedBy WHERE meeting_schedule_id = :meetingScheduleId ";

		return entityManager.createNativeQuery(nativeQuery).setParameter("setstatus", 1)

				.setParameter("updatedOn", currentDate)

				.setParameter("updatedBy", updatedby)

				.setParameter("meetingScheduleId", id)

				.executeUpdate();

	}

}
