package com.goods.shop.config.auth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.goods.shop.model.User;
import com.goods.shop.repository.UserRepository;

import lombok.RequiredArgsConstructor;

// http://localhost:8080/login 요청시 동작해야하는데 안쓰도록 설정(SecurityConfig.formLogin().disable())되었으니 
// 이 서비스를 사용하는 필터를 만들어줘야한다.

@Service
@RequiredArgsConstructor
public class PrincipaDetailsService implements UserDetailsService{
	
	private static final Logger log = LogManager.getLogger(PrincipaDetailsService.class);
	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("PrincipaDetailsService : loadUserByUsername()");
		User userEntity = userRepository.findByUsername(username).get();
		log.info("userEntity : "+userEntity);
		return new PrincipalDetails(userEntity);
	}

}
