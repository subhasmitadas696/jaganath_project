package com.csmtech.SJTA.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "district_master",schema = "land_bank")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DistrictMaster {

	@Id
	@Column(name = "district_code")
	private String districtCode;

	@Column(name = "district_name")
	private String districtName;

	@Column(name = "state_code")
	private String stateCode;
}
