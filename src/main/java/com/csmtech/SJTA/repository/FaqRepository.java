/**
 * @author prasanta.sethi
 */
package com.csmtech.SJTA.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.csmtech.SJTA.entity.Faq;

@Repository
public interface FaqRepository extends JpaRepository<Faq, BigInteger> {
	@Query(value = "SELECT question, answer FROM m_faq where status='0'", nativeQuery = true)
	List<Object[]> findAllQuestionsAndAnswers();
}
