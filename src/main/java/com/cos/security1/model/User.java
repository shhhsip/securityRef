package com.cos.security1.model;


import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String username;
	private String password;
	private String email;
	private String role;
	
	private String provider;
	private String provicerId;
	
	@CreationTimestamp
	private Timestamp createDate;

	
	public User() {
		
	}
	
	@Builder
	public User(String username, String password, String email, String role, String provider, String provicerId,
			Timestamp createDate) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = role;
		this.provider = provider;
		this.provicerId = provicerId;
		this.createDate = createDate;
	}

	
}
