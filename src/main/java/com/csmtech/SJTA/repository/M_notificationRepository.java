package com.csmtech.SJTA.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.csmtech.SJTA.entity.M_notification;

@Repository
public interface M_notificationRepository extends JpaRepository<M_notification, Integer> {
	@Query(" From M_notification where intId =:intId and status = '0' ")
	M_notification findByIntId(Integer intId);

	@Query("From M_notification where bitDeletedFlag=:bitDeletedFlag")
	List<M_notification> findAllByBitDeletedFlag(Boolean bitDeletedFlag);

	@Query(value = "select * from m_notification where status= '0' limit :pageSize offset :offset", nativeQuery = true)
	List<M_notification> findAllByBitDeletedFlagWithPagination(Integer pageSize, Integer offset);

	@Query(value = "select count(1) from m_notification where status='0' ", nativeQuery = true)
	Integer getCount();
	
	
}