package com.goods.shop.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.goods.shop.dto.AuthDTO;
import com.goods.shop.repository.UserRepository;
import com.goods.shop.exception.NotFoundException;
import com.goods.shop.model.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final UserRepository userRepository;
	
	public List<AuthDTO.Response> findAll() {
		
		List<AuthDTO.Response> list = userRepository.findAll()
				.stream()
				.map(AuthDTO.Response::of)
				.collect(Collectors.toList());
		
		return list;
		
	}
	
	
	public AuthDTO.Response findById(Long id) {

		AuthDTO.Response user = userRepository.findById(id)
				.map(AuthDTO.Response::of)
				.orElseThrow(NotFoundException::new);

		return user;
	}
	
	
	public AuthDTO.Response createUser(AuthDTO.Create create) {
		User user = create.toEntity();
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setRoles("ROLE_USER");
		user.setJoinDate(new Date());
		
		
		User res = userRepository.save(user);

		return AuthDTO.Response.of(res); 
	}

	
	@Transactional
	public AuthDTO.Response updateUser(Long id, AuthDTO.Update update) {
		User updated = userRepository.findById(id)
		.map(update::apply)
		.orElseThrow(NotFoundException::new);
		
		return AuthDTO.Response.of(updated);
	}

	
	public void deleteUser(Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(NotFoundException::new);
		
		userRepository.delete(user);
	}
	
	public AuthDTO.Response findByUsername(String username) {

		AuthDTO.Response user = userRepository.findByUsername(username)
				.map(AuthDTO.Response::of)
				.orElseThrow(NotFoundException::new);

		return user;
	}
	
	
}
