package com.csmtech.SJTA.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "m_khata_no", schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KhataNoEntity {
	@Id
	@Column(name = "khata_no_id")
	private Long khataNoId;

	@Column(name = "mouza_id")
	private Long mouzaId;
	
	@Column(name = "khata_no")
	private String khataNo;

}
