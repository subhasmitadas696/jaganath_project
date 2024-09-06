package com.csmtech.SJTA.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "m_mouza", schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MouzaEntity {
	@Id
	@Column(name = "mouza_id")
	private Long mouzaId;

	@Column(name = "tehsil_id")
	private Long tehsilId;
	
	@Column(name = "mouza_name")
	private String mouzaName;

}
