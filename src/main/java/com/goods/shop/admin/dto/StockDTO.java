package com.goods.shop.admin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockDTO {

	private Long stockId;
	
	private Long itemId;
	
	private Integer stockQuantity;
	
	private String createUser;
	
	private String updateUser;
}
