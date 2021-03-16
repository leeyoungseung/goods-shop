package com.goods.shop.config.jwt;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goods.shop.config.auth.PrincipalDetails;
import com.goods.shop.model.User;

import lombok.RequiredArgsConstructor;

// login 요청에서 username, password를 전송하면 (POST)
// UsernamePasswordAuthenticationFilter 가 동작함
// UsernamePasswordAuthenticationFilter를 상속해서 커스텀.
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;
	
	// login 요청을 하면 로그인 시도를 위해서 실행되는 메서드
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		System.out.println("JwtAuthenticationFilter : 로그인 시도중");
		// 1. /login 요청으로 username, password 받기
		try {

			ObjectMapper om = new ObjectMapper();
			User user = om.readValue(request.getInputStream(), User.class);
			System.out.println(user);
			
			UsernamePasswordAuthenticationToken authenticationToken = 
					new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
			
			// 2. PrincipalDetailsService의 loadUserByUsername() 함수가 실행됨
			// 3. PrincipalDetails가 세션에 담겨진다 (PrincipalDetails를 담는 이유는 권한 관리 때문)
			
			Authentication authentication = 
					authenticationManager.authenticate(authenticationToken);
			
			// ※ authentication 객체가 session영역에 저장됨 => 로그인이 되었다는 뜻임
			PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
			System.out.println("=========================================================");
			System.out.println("login complete : "+principalDetails.getUser().getUsername()); // 값이 있다면 로그인이 된것임
			System.out.println("=========================================================");
			
			
			return authentication;
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		
		return null;
	}
	
	// 4. JWT토큰을 만들어서 응답하기
	// attemptAuthentication 실행후 인증이 정상적을 되었으면 이 메서드가 실행됨
	// JWT 토큰을 만들어서 reqeust요청한 사용자에게 토큰을 response해준다.
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		System.out.println("successfulAuthentication : 인증이 완료되었다는 뜻");
		PrincipalDetails principalDetails = (PrincipalDetails)authResult.getPrincipal();
		
		String jwtToken = JWT.create()
				.withSubject("lee-yToken")
				.withExpiresAt(new Date(System.currentTimeMillis() + (60000 * 10)))
				.withClaim("id", principalDetails.getUser().getId())
				.withClaim("username", principalDetails.getUser().getUsername())
				.sign(Algorithm.HMAC512("lee-y"));
		response.addHeader("Authorization", "Bearer "+jwtToken);
		
	}
}
