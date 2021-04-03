package com.goods.shop.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String username;
	
	private String password;
	
	private String givenName;
	
	private String familyName;
	
	private String roles;	//USER, ADMIN
	
	//@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date joinDate;
	
	public List<String> getRoleList() {
		if(this.roles.length() > 0) {
			return Arrays.asList(this.roles.split(","));
		}
		return new ArrayList<>();
	}
	
	@Builder
	private User(String username, String password, String givenName, String familyName) {
		this.username = username;
		this.password = password;
		this.givenName = givenName;
		this.familyName = familyName;
	}
	
	public User update(String password, String givenName, String familyName) {
		this.password = password;
		this.givenName = givenName;
		this.familyName = familyName;
		return this;
	}
	
	
}