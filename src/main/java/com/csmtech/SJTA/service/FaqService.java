package com.csmtech.SJTA.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csmtech.SJTA.dto.FaqDto;
import com.csmtech.SJTA.repository.FaqRepository;

/**
 * @author prasanta.sethi
 */
public interface FaqService {

	public List<FaqDto> getAllQuestionsAndAnswers();
}
