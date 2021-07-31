package com.goods.shop.admin.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDTO {

	private Long categoryId;
	
	private String categoryCode;
	
	private Integer categoryLayer;
	
	private String categoryCodeLayer1;
	
	private String categoryCodeLayer2;
	
	private String categoryCodeLayer3;
	
	private String categoryName;
	
	private String categoryDescription;

	private Integer categoryStatus;
	
	private String createUser;
	
	private String updateUser;
	
	private List<CategoryDTO> nextLayerCategories;
	
	public List<CategoryDTO> getNextLayerCategories() {
		return (nextLayerCategories == null) 
				? new ArrayList<CategoryDTO>()
				: this.nextLayerCategories;
	}
}
