package com.csmtech.SJTA.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "m_role", schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MRoleEntity {
	@Id
	@Column(name = "role_id")
	private Long roleId;

	@Column(name = "department_id")
	private Long departmentId;

	@Column(name = "role_name")
	private String roleName;

}
