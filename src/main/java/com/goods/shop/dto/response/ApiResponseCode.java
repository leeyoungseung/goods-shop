package com.goods.shop.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApiResponseCode implements ResponseEnumType {
	OK(200, "Success"),
	NOT_FOUND(404, "Not Found"),
	BAD_PARAMETER(400, "Bad Parameter"),
	UNAUTHORIZED(401, "Unauthorized"),
	SERVER_ERROR(500, "Server Error"),
	NOT_SIGNIN(403, "Not SignIn"),
	EXPIRED_TOKEN(403, "Expired Token"),
	INVALID_TOKEN(403, "Invalid Token")
	;

	private final Integer status;
	private final String message;
	
	@Override
	public String getId() {
		return name();
	}
	
	@Override
	public Integer getStatus() {
		return status;
	}

	@Override
	public String getText() {
		return message;
	}

}
