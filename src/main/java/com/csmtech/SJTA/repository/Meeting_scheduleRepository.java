package com.csmtech.SJTA.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.csmtech.SJTA.entity.Meeting_schedule;

@Repository
public interface Meeting_scheduleRepository extends JpaRepository<Meeting_schedule, Integer> {
	@Query(" From Meeting_schedule where intId =:intId and status = '0' ")
	Meeting_schedule findByIntId(Integer intId);

	@Query(value = "From Meeting_schedule where status= '0' ")
	List<Meeting_schedule> findAllByBitDeletedFlag(Pageable pageRequest);

	@Query(value = "Select count(*) from application.meeting_schedule where status = '0' ", nativeQuery = true)
	Integer countByBitDeletedFlag();
}