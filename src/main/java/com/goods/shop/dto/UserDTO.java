package com.goods.shop.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

	private Long userNo;
	
	//E-mail
	private String emailId;
	
	private String password;
	
	private String givenName;
	
	private String familyName;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime joinDate;
	
	private Integer sex;
	
	private String phoneNumber;
	
	private String address;
}
