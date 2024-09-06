package com.csmtech.SJTA.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USER_DETAILS")
public class LandAppRegistratationEntity implements Serializable {

	/**
	 * @Auth Rashmi Ranjan Jena
	 */
	private static final long serialVersionUID = 1L;

	
	@Id
	@Column(name = "user_id")
	private Long id;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "password")
	private String password;

	@Column(name = "full_name")
	private String fullName;

	@Column(name = "mobile_no")
	private String mobileno;

	@Column(name = "email_id")
	private String emailId;

	@Column(name = "otp")
	private String otp;

	@Column(name = "user_type")
	private String userType; //C

	@Column(name = "created_by")
	private Long createdBy; //Id

	@Column(name = "created_on")
	@CreationTimestamp
	private Date createdOn;

	@Column(name = "updated_by") 
	private Long upatedBy; 

	@Column(name = "updated_on")
	@UpdateTimestamp
	private Date upatedOn;

	@Column(name = "status")
	private boolean status;

}
