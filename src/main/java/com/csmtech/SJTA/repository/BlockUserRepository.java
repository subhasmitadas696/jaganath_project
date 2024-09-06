/**
 * 
 */
package com.csmtech.SJTA.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import com.csmtech.SJTA.entity.BlockUser;

public interface BlockUserRepository extends JpaRepository<BlockUser, Long> {
	@Transactional
	@Modifying
	@Query(value = "UPDATE user_details SET user_block_status = :userBlockStatus, block_remarks = :blockRemarks, status='0' WHERE user_id = :userId", nativeQuery = true)
	void updateUserBlockStatusAndRemarks(Long userId, Boolean userBlockStatus, String blockRemarks);
	
	@Query(value = "SELECT user_id FROM user_details WHERE user_id = ?1", nativeQuery = true)
	Long checkUserExists(Long userId);
	
	@Query(value = "SELECT user_block_status FROM user_details WHERE user_id = ?1", nativeQuery = true)
	Boolean checkBlockStatus(Long userId);
}