package com.goods.shop.config.auth;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goods.shop.dto.response.ApiResponseCode;
import com.goods.shop.dto.response.ApiResponseDTO;
import com.goods.shop.exception.ApiException;


@Component
public class CustomLogoutSuccessHandler extends HttpStatusReturningLogoutSuccessHandler {
	
	private static final Logger log = LogManager.getLogger(CustomLogoutSuccessHandler.class);
	
	public CustomLogoutSuccessHandler() {
		super();
	}

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException {
		
		
		if (response.isCommitted()) { 
			log.debug("Response has already been committed.");
		}
		
		response.setStatus(HttpStatus.OK.value());
		setResponse(response);
		
	}
	
	private void setResponse(HttpServletResponse response) throws IOException {
		ObjectMapper om = new ObjectMapper();
		ApiResponseDTO<String> dto = ApiResponseDTO.createOK();
		
		response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    String responseJson = om.writeValueAsString(dto);
	    log.warn("LogoutSuccess Response : "+ responseJson);
	    
	    PrintWriter out = response.getWriter();
	    out.print(responseJson);
	    out.flush();
	    out.close();
	}

}
