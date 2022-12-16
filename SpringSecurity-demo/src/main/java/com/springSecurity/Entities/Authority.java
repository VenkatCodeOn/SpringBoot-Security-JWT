package com.springSecurity.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthoritiesContainer;

@Table(name = "AUTH_AUTHORITY")
@Entity
public class Authority implements GrantedAuthority {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name="ROLE_CODE")
	private String role_code;
	
	@Column(name = "ROLE_DESCRIPTION")
	private String role_decription;
	
	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return role_code;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRole_code() {
		return role_code;
	}

	public void setRole_code(String role_code) {
		this.role_code = role_code;
	}

	public String getRole_decription() {
		return role_decription;
	}

	public void setRole_decription(String role_decription) {
		this.role_decription = role_decription;
	}
	
	

}
