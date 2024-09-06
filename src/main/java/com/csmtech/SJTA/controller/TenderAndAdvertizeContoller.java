package com.csmtech.SJTA.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csmtech.SJTA.dto.TenderAdvertisementDTO;
import com.csmtech.SJTA.dto.TenderAndAdvertizeDTO;
import com.csmtech.SJTA.entity.DistrictMaster;
import com.csmtech.SJTA.entity.KhatianInformation;
import com.csmtech.SJTA.entity.PlotInformation;
import com.csmtech.SJTA.entity.TahasilMaster;
import com.csmtech.SJTA.entity.TenderAndAdvertizeEntity;
import com.csmtech.SJTA.entity.TenderType;
import com.csmtech.SJTA.entity.VillageMaster;
import com.csmtech.SJTA.service.TenderAndAdvertizeService;

@RestController
@RequestMapping("/api/tender")
@CrossOrigin("*")
public class TenderAndAdvertizeContoller {

	/**
	 * @author guru.prasad
	 */

	@Autowired
	private TenderAndAdvertizeService tenderservice;

	@Value("${file.path}")
	private String filePathloc;

	private static final Logger log = LoggerFactory.getLogger(TenderAndAdvertizeContoller.class);
	private static final String STATUS_KEY = "status";
	private static final String MESSAGE_KEY = "message";

	@PostMapping("/getalltendertype")
	public ResponseEntity<Map<String, Object>> getAllTenderType() {
		Map<String, Object> response = new HashMap<>();
		List<TenderType> list = tenderservice.getAllTenderType();
		if (list != null && !list.isEmpty()) {
			response.put(STATUS_KEY, HttpStatus.OK.value());
			response.put(MESSAGE_KEY, "DATA FOUND");
			response.put("result", list);
		} else {
			response.put(STATUS_KEY, HttpStatus.NOT_FOUND.value());
			response.put(MESSAGE_KEY, "NO DATA FOUND");
		}
		return ResponseEntity.ok(response);
	}

	@PostMapping("/getalldistrict")
	public ResponseEntity<Map<String, Object>> getAllDistrict() {
		Map<String, Object> response = new HashMap<>();
		List<DistrictMaster> list = tenderservice.getAllDistrict();
		if (list != null && !list.isEmpty()) {
			response.put(STATUS_KEY, HttpStatus.OK.value());
			response.put(MESSAGE_KEY, "DATA FOUND");
			response.put("result", list);
		} else {
			response.put(STATUS_KEY, HttpStatus.NOT_FOUND.value());
			response.put(MESSAGE_KEY, "NO DATA FOUND");
		}
		return ResponseEntity.ok(response);
	}

	@PostMapping("/getalltehsil")
	public ResponseEntity<Map<String, Object>> getAllTehsil(@RequestBody TahasilMaster tehsil) {
		Map<String, Object> response = new HashMap<>();
		List<TahasilMaster> list = tenderservice.getAllTehsil(tehsil.getDistrictCode());
		if (list != null && !list.isEmpty()) {
			response.put(STATUS_KEY, HttpStatus.OK.value());
			response.put(MESSAGE_KEY, "DATA FOUND");
			response.put("result", list);
		} else {
			response.put(STATUS_KEY, HttpStatus.NOT_FOUND.value());
			response.put(MESSAGE_KEY, "NO DATA FOUND");
		}
		return ResponseEntity.ok(response);
	}

	@PostMapping("/getallvillage")
	public ResponseEntity<Map<String, Object>> getAllVillage(@RequestBody VillageMaster village) {
		Map<String, Object> response = new HashMap<>();
		List<VillageMaster> list = tenderservice.getAllVillage(village.getTahasilCode());
		if (list != null && !list.isEmpty()) {
			response.put(STATUS_KEY, HttpStatus.OK.value());
			response.put(MESSAGE_KEY, "DATA FOUND");
			response.put("result", list);
		} else {
			response.put(STATUS_KEY, HttpStatus.NOT_FOUND.value());
			response.put(MESSAGE_KEY, "NO DATA FOUND");
		}
		return ResponseEntity.ok(response);
	}

	@PostMapping("/getallkhatian")
	public ResponseEntity<Map<String, Object>> getAllKhatian(@RequestBody KhatianInformation khatian) {
		Map<String, Object> response = new HashMap<>();
		List<KhatianInformation> list = tenderservice.getAllKhatian(khatian.getVillageCode());
		if (list != null && !list.isEmpty()) {
			response.put(STATUS_KEY, HttpStatus.OK.value());
			response.put(MESSAGE_KEY, "DATA FOUND");
			response.put("result", list);
		} else {
			response.put(STATUS_KEY, HttpStatus.NOT_FOUND.value());
			response.put(MESSAGE_KEY, "NO DATA FOUND");
		}
		return ResponseEntity.ok(response);
	}

	@PostMapping("/getallplot")
	public ResponseEntity<Map<String, Object>> getAllPlot(@RequestBody PlotInformation plot) {
		Map<String, Object> response = new HashMap<>();
		List<PlotInformation> list = tenderservice.getAllPlot(plot.getKhatianCode());
		if (list != null && !list.isEmpty()) {
			response.put(STATUS_KEY, HttpStatus.OK.value());
			response.put(MESSAGE_KEY, "DATA FOUND");
			response.put("result", list);
		} else {
			response.put(STATUS_KEY, HttpStatus.NOT_FOUND.value());
			response.put(MESSAGE_KEY, "NO DATA FOUND");
		}
		return ResponseEntity.ok(response);
	}

	@PostMapping(value = "/savetender")
	public ResponseEntity<?> saveRecord(@RequestBody String tenderData) {
		JSONObject jsb;

		log.info("data {}", tenderData);
		jsb = tenderservice.saveRecord(tenderData);

		jsb.put(STATUS_KEY, 200);
		jsb.put(MESSAGE_KEY, "Data Saved Successfully..");
		return ResponseEntity.ok(jsb.toString());
	}

	@PostMapping("/gettender")
	public ResponseEntity<Map<String, Object>> getTender(@RequestBody TenderAdvertisementDTO tenderdto) {
		Map<String, Object> response = new HashMap<>();
		List<TenderAdvertisementDTO> list = tenderservice.viewAllTender(tenderdto.getTitle(), tenderdto.getStartDate());
		if (list != null && !list.isEmpty()) {
			response.put(STATUS_KEY, HttpStatus.OK.value());
			response.put(MESSAGE_KEY, "DATA FOUND");
			response.put("result", list);
		} else {
			response.put(STATUS_KEY, HttpStatus.NOT_FOUND.value());
			response.put(MESSAGE_KEY, "NO DATA FOUND");
		}
		return ResponseEntity.ok(response);
	}

	@PostMapping("/viewtender")
	public ResponseEntity<Map<String, Object>> viewTender(@RequestBody TenderAdvertisementDTO tenderdto) {
		Map<String, Object> response = new HashMap<>();
		List<TenderAdvertisementDTO> list = tenderservice.getAllTender(tenderdto.getTitle());
		if (list != null && !list.isEmpty()) {
			response.put(STATUS_KEY, HttpStatus.OK.value());
			response.put(MESSAGE_KEY, "DATA FOUND");
			response.put("result", list);
		} else {
			response.put(STATUS_KEY, HttpStatus.NOT_FOUND.value());
			response.put(MESSAGE_KEY, "NO DATA FOUND");
		}
		return ResponseEntity.ok(response);
	}

	@PostMapping("/getonetender")
	public ResponseEntity<Map<String, Object>> getDetails(@RequestBody TenderAndAdvertizeDTO tenderDto,
			HttpServletRequest request) {
		log.info("get details started");
		Map<String, Object> response = new HashMap<>();
		try {
			TenderAndAdvertizeEntity tender = tenderservice
					.findByTenderAdvertisementId(tenderDto.getTenderAdvertisementId());

			System.out.println(tender + "*********");
			if (tender != null) {
				response.put(STATUS_KEY, HttpStatus.OK.value());
				response.put(MESSAGE_KEY, "TenderAndAdvertize_Details found");
				response.put("result", tender);

			} else {
				response.put(STATUS_KEY, HttpStatus.NOT_FOUND.value());
				response.put(MESSAGE_KEY, "TenderAndAdvertize_Details not found");
			}
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			log.error("Error occurred while getting details: {}", e.getMessage());
			response.put(STATUS_KEY, HttpStatus.BAD_REQUEST.value());
			response.put(MESSAGE_KEY, "Invalid tenderId");
			return ResponseEntity.badRequest().body(response);
		}
	}

	@PostMapping("/deletetender")
	public ResponseEntity<Map<String, Object>> deleteTender(@RequestBody TenderAdvertisementDTO tenderDto) {
		log.info("delete started");
		Map<String, Object> response = new HashMap<>();
		try {
			if (tenderDto.getTenderAdvertisementId() != null) {
				log.info("delete operation started");
				tenderservice.deleteTender(tenderDto.getCreatedBy(), tenderDto.getTenderAdvertisementId());
				log.info("deletion completed");
				response.put(STATUS_KEY, HttpStatus.OK.value());
				response.put(MESSAGE_KEY, "Tender deleted");
			} else {
				response.put(STATUS_KEY, HttpStatus.BAD_REQUEST.value());
				response.put(MESSAGE_KEY, "Tender not deleted");
			}
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			log.error("Error occurred while getting details: {}", e.getMessage());

			response.put(STATUS_KEY, HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.put(MESSAGE_KEY, "Server Error");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

}
