package com.goods.shop.admin.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemStockDTO {
	
	private Long itemId;
	
	private Integer entireStockQuantity;
	
	private List<StockDTO> stockList;

}
