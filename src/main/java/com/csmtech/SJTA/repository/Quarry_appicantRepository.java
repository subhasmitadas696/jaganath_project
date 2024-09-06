package com.csmtech.SJTA.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.csmtech.SJTA.entity.Quarry_appicant ; 
@Repository
public interface Quarry_appicantRepository extends JpaRepository<Quarry_appicant, Integer> {
Quarry_appicant findByIntIdAndBitDeletedFlag(Integer intId,boolean bitDeletedFlag);

@Query("From Quarry_appicant where deleted_flag=:bitDeletedFlag")
List<Quarry_appicant> findAllByBitDeletedFlag(Boolean bitDeletedFlag);
}