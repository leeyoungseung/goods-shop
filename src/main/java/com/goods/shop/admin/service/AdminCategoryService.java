package com.goods.shop.admin.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goods.shop.admin.dto.CategoryDTO;
import com.goods.shop.admin.service.category.CategoryFactory;
import com.goods.shop.admin.service.category.CategoryLogic;
import com.goods.shop.exception.NotFoundException;
import com.goods.shop.model.Category;
import com.goods.shop.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminCategoryService {

	private static final Logger log = LogManager.getLogger(AdminCategoryService.class);
	
	private final CategoryRepository categoryRepository;
	
	
	/**
	 * 카테고리 등록
	 * @return
	 */
	public CategoryDTO createCategory(CategoryDTO create) {
		// 1. 파라미터 확인
		log.info("createCategory CategoryDTO = "+create);
		
		// 2. 파라미터에 따라 다른 로직 설정
		CategoryLogic logic = CategoryFactory.getInstance(create);
		logic.setRepository(categoryRepository);
		
		Category category = logic.createCategory();
		
		return mappingDTO(category);
	}
	
	
	/**
	 * 카테고리 리스트 전체 가져오기
	 * @return
	 */
	public List<CategoryDTO> getCategories() {
		log.info("getCategorys");
		
		// (1). 1차 카테고리의 데이터를 모두 가져온다. 
		Optional<List<Category>> layer1Data = categoryRepository.findByCategoryLayerOne();
		if (layer1Data.isEmpty()) {
			log.info("layer1Data is Empty");
			return null;
		}
		
		// (2). 1차 카테고리의 데이터 리스트를 DTO에 적재
		List<Category> categoryListLayer1 = layer1Data.get();
		List<CategoryDTO> resList = new ArrayList<CategoryDTO>();
		for (Category c : categoryListLayer1) {
			log.info("getCategoryCode"+c.getCategoryCode());
		}
		
		categoryListLayer1.forEach((category) -> {
			log.info("layer1Data category="+category);
			CategoryDTO dto = mappingDTO(category);
			
			// (3). 2차 카테고리의 데이터를 모두 가져온다.
			Optional<List<Category>> layer2Data =
					categoryRepository.findByCategoryLayer2FromCategoryCodeLayer1(
							category.getCategoryCodeLayer1()
							);
			
			List<Category> categoryListLayer2 = null;
			List<CategoryDTO> resListLayer2 = new ArrayList<CategoryDTO>();
			
			if ( ! layer2Data.isEmpty()) {
				// (3). 2차 카테고리의 데이터 리스트를 DTO에 적재
				categoryListLayer2 = layer2Data.get();
				
				categoryListLayer2.forEach((category2) -> {
					CategoryDTO dtoLayer2 = mappingDTO(category2);
					
					Optional<List<Category>> layer3Data =
							categoryRepository.findByCategoryLayer3FromCategoryCodeLayer2(
									category2.getCategoryCodeLayer2()
									);
					
					List<Category> categoryListLayer3 = null;
					List<CategoryDTO> resListLayer3 = new ArrayList<CategoryDTO>();
					
					if ( ! layer3Data.isEmpty()) {
						// (4). 3차 카테고리의 데이터 리스트를 DTO에 적재
						categoryListLayer3 = layer3Data.get();
						
						categoryListLayer3.forEach((category3) -> {
							CategoryDTO dtoLayer3 = mappingDTO(category3);
							resListLayer3.add(dtoLayer3);
							
						});
						
					}
					
					if (resListLayer3 != null) 
						dtoLayer2.setNextLayerCategories(resListLayer3);
					
					resListLayer2.add(dtoLayer2);
										
				});
				
			}
			
			if (resListLayer2 != null) 
				dto.setNextLayerCategories(resListLayer2);
			
			resList.add(dto);
			
		});
		
		
		return resList;
	}
	
	
	/**
	 * 카테고리 상세 정보 가져오기
	 * @param category
	 * @return
	 */
	public CategoryDTO getCategory(Long categoryId) {
		log.info("getCategory categoryId = "+categoryId);
		
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(NotFoundException::new);
		
		log.info("category Data = "+category);
		
		return mappingDTO(category);
	}
	
	
	/**
	 * 카테고리 데이터 갱신
	 * @param categoryId
	 * @param update
	 * @return
	 */
	@Transactional
	public CategoryDTO updateCategory(Long categoryId, CategoryDTO update) {
		log.info("updateCategory categoryId = "+categoryId+" , StockDTO = "+update);
		
		// 2. 파라미터에 따라 다른 로직 설정
		CategoryLogic logic = CategoryFactory.getInstance(update);
		logic.setRepository(categoryRepository);
		
		Category category = logic.updateCategory(categoryId);
		
		return mappingDTO(category);
	}
	
	/**
	 * 
	 * @param categoryId
	 * @return
	 */
	@Transactional
	public String deleteCategory(Long categoryId) {
		log.info("deleteCategory categoryId = "+categoryId);
		// 1. 데이터가 있는지 확인 
		
		
		// 2. 카테고리 계층을 확인 
		// 1,2계층의 경우 하위계층의 카테고리가 있으면 삭제 할수 없다.
		
		
		// 3. 데이터 삭제
		Long res = categoryRepository.deleteByCategoryId(categoryId);
		
		return (res == 1) ? "Delete Success categoryId : [" + categoryId + "]" : "Delete Failure categoryId : [" + categoryId + "]";
	}
	
	
	
	/**
	 * DTO에 엔티티 데이터 매핑
	 * @param category
	 * @return
	 */
	private CategoryDTO mappingDTO(Category category) {
		CategoryDTO dto = new CategoryDTO();
		dto.setCategoryId(category.getCategoryId());
		dto.setCategoryCode(category.getCategoryCode());
		dto.setCategoryLayer(category.getCategoryLayer());
		dto.setCategoryCodeLayer1(category.getCategoryCodeLayer1());
		if (category.getCategoryCodeLayer2() != null && !category.getCategoryCodeLayer2().equals("")) 
			dto.setCategoryCodeLayer2(category.getCategoryCodeLayer2());
		if (category.getCategoryCodeLayer3() != null && !category.getCategoryCodeLayer3().equals("")) 
			dto.setCategoryCodeLayer3(category.getCategoryCodeLayer3());
		dto.setCategoryName(category.getCategoryName());
		dto.setCategoryDescription(category.getCategoryDescription());
		dto.setCategoryStatus(category.getCategoryStatus());
		
		return dto;
	}
}
