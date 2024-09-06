package com.csmtech.SJTA.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "m_district", schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DistrictEntity {
	@Id
	@Column(name = "district_id")
	private Long districtId;

	@Column(name = "state_id")
	private Long stateId;
	
	@Column(name = "district_name")
	private String districtName;

}
