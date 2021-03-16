package com.goods.shop.controller;

import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.goods.shop.config.auth.PrincipalDetails;
import com.goods.shop.model.User;
import com.goods.shop.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class AuthController {
	
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final UserRepository userRepository;
	
	@GetMapping("/home")
	public String home() {
		return "<h1>home</h1>";
	}
	
	@PostMapping("/join")
	public String join(
			@RequestBody User user
			) {
		
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setRoles("ROLE_USER");
		user.setJoinDate(new Date());
		
		userRepository.save(user);
		
		return "join complete";
	}
	
	@GetMapping("/api/v1/user")
	public String user(Authentication authentication) {
		PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
		System.out.println("authentication : "+principalDetails.getUsername());
		return "user";
	}

	@GetMapping("/api/v1/manager")
	public String manager() {
		return "manager";
	}
	
	@GetMapping("/api/v1/admin")
	public String admin() {
		return "admin";
	}
}
