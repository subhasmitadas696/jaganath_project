package com.csmtech.SJTA.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.csmtech.SJTA.entity.Noc_plot;

@Repository
public interface Noc_plotRepository extends JpaRepository<Noc_plot, Integer> {
	Noc_plot findByIntIdAndBitDeletedFlag(Integer intId, boolean bitDeletedFlag);

	@Query("From Noc_plot where deleted_flag=:bitDeletedFlag")
	List<Noc_plot> findAllByBitDeletedFlag(Boolean bitDeletedFlag);
}