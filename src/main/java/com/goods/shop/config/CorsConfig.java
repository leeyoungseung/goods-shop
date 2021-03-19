package com.goods.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

//배포시에 보안을위해 이 코드는 수정해줘야함
@Configuration
public class CorsConfig {
	
	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		
		//config.setAllowCredentials(true); 
		
		config.addAllowedOrigin("*"); // 모든 ip에 응답을 허용
		config.addAllowedHeader("*"); // 모든 header에 응답을 허용
		config.addAllowedMethod("*"); // 모든 post / get / update / delete요청을 허용 
				
		
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	
	}
}
