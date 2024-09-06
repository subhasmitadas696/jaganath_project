package com.csmtech.SJTA.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "m_plot_no", schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlotNoEntity {
	@Id
	@Column(name = "plot_no_id")
	private Long plotNoId;

	@Column(name = "khata_no_id")
	private Long khataNoId;

	@Column(name = "plot_no")
	private String plotNo;

}
