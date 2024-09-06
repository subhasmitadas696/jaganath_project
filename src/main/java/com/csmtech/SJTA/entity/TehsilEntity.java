package com.csmtech.SJTA.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "m_tehsil", schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TehsilEntity {
	@Id
	@Column(name = "tehsil_id")
	private Long tehsilId;

	@Column(name = "district_id")
	private Long districtId;
	
	@Column(name = "tehsil_name")
	private String tehsilName;

}
