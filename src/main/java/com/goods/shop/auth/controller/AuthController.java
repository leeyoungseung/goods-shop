package com.goods.shop.auth.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.goods.shop.auth.service.PrincipalDetail;
import com.goods.shop.model.User;
import com.goods.shop.repository.UserRepository;
import com.goods.shop.utils.JwtUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AuthController {

	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private final UserRepository userRepository;
	
	@Autowired
	private JwtUtil jwtUtil;
    
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
	
	@PostMapping("/signup")
	public String signup(@RequestBody User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setRoles("ROLE_USER");
		user.setJoinDate(LocalDateTime.now());
		
		userRepository.save(user);
		return "signup complete";
	}
	
	
	@GetMapping("/api/v1/user")
	public String user(Authentication authentication) {
		PrincipalDetail principalDetails = (PrincipalDetail) authentication.getPrincipal();
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
