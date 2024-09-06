package com.csmtech.SJTA.entity;

import javax.persistence.Entity;
import javax.persistence.Column;
import java.util.List;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Transient;

import java.math.BigDecimal;
import java.sql.Date;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@Table(name = "land_schedule")
@Entity
public class Land_plot {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "land_schedule_id")
	private Integer intId;


	@Column(name = "`plot_code`")
	private String selPlotNo;
	@Transient
	private String selPlotNoVal;
	@Column(name = "`total_area`")
	private BigDecimal txtTotalRakba;
	@Column(name = "`purchase_area`")
	private BigDecimal txtPurchaseRakba;
//	@Column(name = "`varieties_id`")
//	private Integer selVarieties;
	@Transient
	private String selVarietiesVal;
	

	@Column(name = "created_by")
	private Integer intCreatedBy;


	@Column(name = "updated_by")
	private Integer intUpdatedBy;

	@Column(name = "created_on")
	@CreationTimestamp
	private Date dtmCreatedOn;

	@Column(name = "updated_on")
	@UpdateTimestamp
	private Date stmUpdatedOn;

	@Column(name = "deleted_flag")
	private Boolean bitDeletedFlag = false;


	@Column(name = "`land_application_id`")
	@JoinColumn
	private Integer intLandApplicantId;

	@Transient
	private String varitiesName;

}