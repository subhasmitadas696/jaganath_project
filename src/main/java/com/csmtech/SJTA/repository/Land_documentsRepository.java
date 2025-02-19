package com.csmtech.SJTA.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.csmtech.SJTA.entity.Land_documents;

@Repository
public interface Land_documentsRepository extends JpaRepository<Land_documents, Integer> {
	Land_documents findByIntIdAndBitDeletedFlag(Integer intId, boolean bitDeletedFlag);

	@Query("From Land_documents where deleted_flag=:bitDeletedFlag")
	List<Land_documents> findAllByBitDeletedFlag(Boolean bitDeletedFlag);

	void deleteAllByIntParentId(Integer parentId);

	@Query(value = "select * "
			+ "    from land_documents "
			+ "    where "
			+ "        land_application_id=?  "
			+ "        and deleted_flag='0' ",nativeQuery = true)
	List<Land_documents> findByIntParentIdAndBitDeletedFlag(Integer intParentId);

	@Query("From Land_documents where intParentId=:intParentId AND bitDeletedFlag='0' ")
	List<Land_documents> getByparentIdd(Integer intParentId);

}