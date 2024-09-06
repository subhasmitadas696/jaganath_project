package com.csmtech.SJTA.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.csmtech.SJTA.entity.Noc_documents ; 
@Repository
public interface Noc_documentsRepository extends JpaRepository<Noc_documents, Integer> {
Noc_documents findByIntIdAndBitDeletedFlag(Integer intId,boolean bitDeletedFlag);

@Query("From Noc_documents where deleted_flag=:bitDeletedFlag")
List<Noc_documents> findAllByBitDeletedFlag(Boolean bitDeletedFlag);
}