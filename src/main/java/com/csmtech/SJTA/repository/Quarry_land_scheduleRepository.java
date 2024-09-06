package com.csmtech.SJTA.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.csmtech.SJTA.entity.Quarry_land_schedule;

@Repository
public interface Quarry_land_scheduleRepository extends JpaRepository<Quarry_land_schedule, Integer> {
    Quarry_land_schedule findByIntIdAndBitDeletedFlag(Integer intId, boolean bitDeletedFlag);

    @Query("From Quarry_land_schedule where deleted_flag=:bitDeletedFlag")
    List<Quarry_land_schedule> findAllByBitDeletedFlag(Boolean bitDeletedFlag);
}