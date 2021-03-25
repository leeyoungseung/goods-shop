package com.goods.shop.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.goods.shop.config.auth.PrincipalDetails;
import com.goods.shop.dto.AuthDTO;
import com.goods.shop.dto.response.ApiResponseDTO;
import com.goods.shop.model.User;
import com.goods.shop.service.AuthService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	
	private static final Logger log = LogManager.getLogger(AuthController.class);
			
	private final AuthService authService;
	
	
	@PostMapping("/signup")
	public ApiResponseDTO<AuthDTO.ResponseOne> signup (
			@RequestBody AuthDTO.Create create
			) throws Exception {
		log.info("AuthController signup");
		
		return ApiResponseDTO.createOK(new AuthDTO.ResponseOne(authService.createUser(create)));
	}
	
	
	@GetMapping("/user")
	public ApiResponseDTO<AuthDTO.ResponseOne> user(Authentication authentication) {
		PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
		log.info("authentication : "+principalDetails.getUsername());
		User user = principalDetails.getUser();
		
		return ApiResponseDTO.createOK(new AuthDTO.ResponseOne(AuthDTO.Response.of(user)));
	}

	@GetMapping("/manager")
	public String manager() {
		return "manager";
	}
	
	@GetMapping("/admin")
	public String admin() {
		return "admin";
	}
}
