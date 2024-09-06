package com.csmtech.SJTA.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csmtech.SJTA.dto.PaginationInRegiserMailRespones;
import com.csmtech.SJTA.dto.PaginationInRegisterDTO;
import com.csmtech.SJTA.dto.PaginationInRegisterDtoResponse;
import com.csmtech.SJTA.repository.PaginationInRegisterClassRepository;

@RestController
@RequestMapping("/mainpagination")
@CrossOrigin("*")
public class PaginationInRegisterController {

	@Autowired
	private PaginationInRegisterClassRepository repo;
	
	

	@PostMapping("/registerpage")
	public ResponseEntity<PaginationInRegiserMailRespones> getPaginationData(@RequestBody PaginationInRegisterDtoResponse res) {
		Integer countint=repo.getTotalUserCount();
		List<PaginationInRegisterDTO> getdtodata=repo.getUserDetailsPage(res.getPageNumber(), res.getPageSize());
		System.out.println("Count   "+countint);
		return ResponseEntity.ok(new PaginationInRegiserMailRespones(getdtodata,countint));
	}
}
