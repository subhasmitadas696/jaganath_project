package com.csmtech.SJTA.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LandAppResponseStructureDTO {

	private LandApplicantViewDTO appdto;
	private List<LandPlotViewDTO> plotto;
	private List<LandAppViewDocumentDTO> docsdto;
	
//	Integer pendingRoleId;

}
