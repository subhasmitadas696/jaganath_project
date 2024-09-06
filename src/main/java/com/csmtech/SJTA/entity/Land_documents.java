package com.csmtech.SJTA.entity;

import javax.persistence.Entity;
import javax.persistence.Column;
import java.util.List;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.sql.Date;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.csmtech.SJTA.dto.addMoreUploadDocumentsDTO;

@Data
@Table(name = "land_documents")
@Entity
public class Land_documents {
//	@Transient
//	private List<Land_documents> addMoreUploadDocuments;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="land_doc_id")
	private Integer intId;

	@Column(name="`document_name`")
	private String amtxtDocumentName;
	
	@Column(name="`docs_path`")
	private String amfileDocument;

	@Column(name="created_by")
	private Integer intCreatedBy;

	@Column(name="land_application_id")
	private Integer intParentId;
	
	@Column(name="updated_by")
	private Integer intUpdatedBy;

	@Column(name="created_on")
	@CreationTimestamp
	private Date dtmCreatedOn;

	@Column(name="updated_on")
	@UpdateTimestamp
	private Date stmUpdatedOn;

	@Column(name="deleted_flag")
	private Boolean bitDeletedFlag=false;

	// @Column(name="jsonopttxtdetails")
	// private String jsonopttxtdetails;
	
	@Transient
	private List<addMoreUploadDocumentsDTO> addMoreUploadDocuments;

}