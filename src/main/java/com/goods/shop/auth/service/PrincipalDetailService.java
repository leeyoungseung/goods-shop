package com.goods.shop.auth.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.goods.shop.model.User;
import com.goods.shop.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PrincipalDetailService implements UserDetailsService {
	
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String eamilId) throws UsernameNotFoundException {
		Optional<User> data = userRepository.findByEmailId(eamilId);
		User userEntity = data.get();
		System.out.println("loadUserByUsername userEntity : "+userEntity);
		return new PrincipalDetail(userEntity);
	}
}
