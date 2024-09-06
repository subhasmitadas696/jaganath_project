package com.csmtech.SJTA.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfficerPaginationDTO {

	List<AddOfficerDTO> dtodata;
	Integer count;

}
