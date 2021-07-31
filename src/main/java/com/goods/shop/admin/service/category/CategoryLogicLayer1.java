package com.goods.shop.admin.service.category;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.goods.shop.admin.dto.CategoryDTO;
import com.goods.shop.exception.NotFoundException;
import com.goods.shop.model.Category;
import com.goods.shop.repository.CategoryRepository;

public class CategoryLogicLayer1 implements CategoryLogic {
	
	private static final Logger log = LogManager.getLogger(CategoryLogicLayer1.class);

	private CategoryDTO dto;
	
	private CategoryRepository repo;
	
	@Override
	public Category createCategory() {
		log.info("createCategory CategoryDTO="+this.dto);
		
		Category category = Category.builder()
				.categoryCode(dto.getCategoryCode())
				.categoryLayer(1)
				.categoryCodeLayer1(dto.getCategoryCodeLayer1())
				.categoryName(dto.getCategoryName())
				.categoryDescription(dto.getCategoryDescription())
				.categoryStatus(dto.getCategoryStatus())
				.createUser(dto.getCreateUser())
				.updateUser(dto.getUpdateUser())
				.build();
		
		log.info("Input Category Data = "+category);
		
		category = repo.save(category);
		
		log.info("Saved Category Data = "+category);
		
		return category;
	}

	
	@Override
	public Category updateCategory(Long categoryId) {
		// 1. 데이터가 DB에 존재하는지 확인
		log.info("updateCategory");
		Category entity = repo.findById(categoryId)
				.orElseThrow(NotFoundException::new);
		
		log.info("updateCategory DB Data = "+entity);
		
		// 2. 갱신하기
		entity.update(dto.getCategoryName(),
				dto.getCategoryDescription(),
				dto.getCategoryStatus(), dto.getUpdateUser()
				);
		
		return repo.save(entity);
	}


	@Override
	public void setDTO(CategoryDTO dto) {
		this.dto = dto;
	}


	@Override
	public void setRepository(CategoryRepository repo) {
		this.repo = repo;
	}
	
	
}
