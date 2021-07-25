package com.goods.shop.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.filter.CorsFilter;

import com.goods.shop.auth.filter.JwtAuthenticationFilter;
import com.goods.shop.auth.filter.JwtAuthorizationFilter;
import com.goods.shop.repository.UserRepository;
import com.goods.shop.utils.JwtUtil;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final CorsFilter corsFilter;
	private final UserRepository userRepository;
	private final JwtUtil jwtUtil;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable()
		.sessionManagement()
		    .sessionCreationPolicy(SessionCreationPolicy.STATELESS) 
		    .and()
			.formLogin().disable()
			.httpBasic().disable() 
		    .addFilter(corsFilter)
//			.addFilter(new JwtAuthenticationFilter(authenticationManager(), userRepository, jwtUtil))
//			.addFilter(new JwtAuthorizationFilter(authenticationManager(), userRepository, jwtUtil))
//			.authorizeRequests()
//			    .antMatchers("/api/v1/user/**").access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
//			    .antMatchers("/api/v1/manager/**").access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
//			    .antMatchers("/api/v1/adm/**").access("hasRole('ROLE_ADMIN')")
//			.and()
			.authorizeRequests()
			    .antMatchers("/api/hello","/api/signup","/api/signup/**").permitAll()
			    .anyRequest().permitAll();
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/api/hello", "/api/signup","/api/signup/**");
	}
}
