package com.goods.shop.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@Configuration
public class CorsConfig {

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true); 
		//config.addAllowedOrigin("*"); // 모든 ip에 응답을 허용
		// https://stackoverflow.com/questions/64892592/cors-enabled-in-spring-boot-with-angular-still-cors-errors
		config.addAllowedOriginPattern("*"); 
		config.addAllowedHeader("*"); // 모든 header에 응답을 허용
		config.addAllowedMethod("*"); // 모든 post / get / update / delete요청을 허용 
		source.registerCorsConfiguration("/api/**", config);
		return new CorsFilter(source);
	}
}

