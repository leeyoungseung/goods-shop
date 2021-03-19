package com.goods.shop.config.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.goods.shop.config.auth.PrincipalDetails;
import com.goods.shop.exception.NotFoundException;
import com.goods.shop.model.User;
import com.goods.shop.repository.UserRepository;

import lombok.RequiredArgsConstructor;


// spring 시큐리티가 가진 필터중에 BasicAuthenticationFilter라는 것이 있음.
// 권한이나 인증이  필요한 특정 주소를 요청했을 때 위 필터를 무조건 통하게 되어있음.
// 만약에 권한이나 인증이 필요한 주소가 아니라면 이 필터를 통하지 않음.
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	private UserRepository userRepository;
	
	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
		super(authenticationManager);
		this.userRepository = userRepository;
		System.out.println("인증이나 권한이 필요한 주소 요청이 됨. JwtAuthorizationFilter");
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//super.doFilterInternal(request, response, chain);
		
		System.out.println("인증이나 권한이 필요한 주소 요청이 됨. doFilterInternal");
		String jwtHeader = request.getHeader("Authorization");
		
		System.out.println("jwtHeader : "+jwtHeader);
		
		// header가 있는지 확인 
		if (jwtHeader == null || !jwtHeader.startsWith("Bearer")) {
			chain.doFilter(request, response);
			return;
		}
		
		//JWT 토큰 검증
		String jwtToken = request.getHeader("authorization").replace("Bearer ","");
		
		String username = 
				JWT.require(Algorithm.HMAC512("lee-y")) // sign한 값
				.build()
				.verify(jwtToken)
				.getClaim("username")
				.asString();
		System.out.println("sign : "+ username);
		// 서명이 정상적인지 확인 
		if (username != null) {
			User userEntity = userRepository.findByUsername(username)
					.orElseThrow(NotFoundException::new);
			
			//Jwt 토큰 서명을 통해서 서명이 정상이면 Authentication 객체를 만들어준다.
			PrincipalDetails principalDetails = new PrincipalDetails(userEntity);
			Authentication authentication = 
					new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
			
			// 강제로 시큐리티의 세션에 접근하여 Authentication 객체를 저장
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
			chain.doFilter(request, response);
		}
	}

}
