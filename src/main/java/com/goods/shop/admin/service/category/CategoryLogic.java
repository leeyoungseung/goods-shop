package com.goods.shop.admin.service.category;

import com.goods.shop.admin.dto.CategoryDTO;
import com.goods.shop.model.Category;
import com.goods.shop.repository.CategoryRepository;

public interface CategoryLogic {

	public void setDTO(CategoryDTO dto);
	
	public void setRepository(CategoryRepository repo);
	
	public Category createCategory();
	
	public Category updateCategory(Long categoryId);
	
}
