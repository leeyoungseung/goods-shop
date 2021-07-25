package com.goods.shop.admin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemDTO {

	private Long itemId;
	
	private String itemName;
	
	private String itemDescription;
	
	private String makerCode;
	
	private Integer price;
	
	private Integer saleStatus;
	
	private String [] images;
	
	private Integer sold;
	
	private String createUser;
	
	private String updateUser;
	
    
}
