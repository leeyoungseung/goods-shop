package com.goods.shop.admin.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.goods.shop.admin.dto.CategoryDTO;
import com.goods.shop.admin.service.AdminCategoryService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/adm/categories")
public class AdminCategoryController {

	private static final Logger log = LogManager.getLogger(AdminCategoryController.class);
	
	private final AdminCategoryService categoryService;
	
	/**
	 * 모든 카테고리 데이터 가져오기
	 * 
	 * @return
	 */
	@GetMapping()
	public ResponseEntity<List<CategoryDTO>> getCategories() {
		log.info("getCategories");
		
		return ResponseEntity.ok(categoryService.getCategories());
	}
	
	
	/**
	 * 카테고리 데이터 생성
	 * 
	 * @param create
	 * @return
	 */
	@PostMapping
	public ResponseEntity<CategoryDTO> createCategory (
			@RequestBody CategoryDTO create 
			) {
		log.info("createCategory "+create);
		
		return ResponseEntity.ok(categoryService.createCategory(create));
	}
	
	/**
	 * 카테고리 데이터 상세 정보가져오기
	 * 
	 * @param categoryId
	 * @return
	 */
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDTO> getCategory(
			@PathVariable("categoryId") Long categoryId
			) {
		log.info("getCategory ="+categoryId);
		
		return ResponseEntity.ok(categoryService.getCategory(categoryId));
	}
	
	/**
	 * 카테고리 데이터 갱신
	 * 
	 * @param categoryId
	 * @param update
	 * @return
	 */
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDTO> updateCategory(
			@PathVariable("categoryId") Long categoryId,
			@RequestBody CategoryDTO update
			) {
		log.info("updateCategory categoryId="+categoryId+", CategoryDTO="+update);
		
		return ResponseEntity.ok(categoryService.updateCategory(categoryId, update));
	}
	
	
	/**
	 * 카테고리 데이터 삭제
	 * 
	 * @param stockId
	 * @return
	 */
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<String> deleteCategory(
			@PathVariable("stockId") Long stockId
			) {
    	log.info("deleteCategory stockId="+stockId);
    	
    	return ResponseEntity.ok(categoryService.deleteCategory(stockId));
    }
	
	
}
