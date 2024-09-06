package com.csmtech.SJTA.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csmtech.SJTA.service.FaqService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/faq")
@CrossOrigin("*")
@Slf4j
public class FaqController {

	@Autowired
	private FaqService faqService;

	@GetMapping("/questionsandanswers")
	public ResponseEntity<?> getAllQuestionsAndAnswers() {
		log.info("inside getAllQuestionsAndAnswers in controller !!");
		log.info("inside FaqController !!");
		JSONObject response = new JSONObject();
		response.put("status", 200);
		response.put("result", faqService.getAllQuestionsAndAnswers());
		log.info("faq executed successfully !!");
		return ResponseEntity.ok(response.toString());
	}
}