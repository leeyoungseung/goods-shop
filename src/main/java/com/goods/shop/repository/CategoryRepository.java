package com.goods.shop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.goods.shop.model.Category;

public interface CategoryRepository extends JpaRepository <Category, Long> , QuerydslPredicateExecutor<Category> {

	Long deleteByCategoryId(Long categoryId);

	
	@Query(value = 
			"SELECT "
			+ "ct "
			+ "FROM "
			+ "category ct "
			+ "WHERE ct.categoryCode = ct.categoryCodeLayer1 "
			+ "AND ct.categoryLayer = 1")
	Optional<List<Category>> findByCategoryLayerOne();
	
	
	@Query(value = 
			"SELECT "
			+ "ct "
			+ "FROM "
			+ "category ct "
			+ "WHERE ct.categoryCodeLayer1 = :categoryCodeLayer1 "
			+ "AND ct.categoryCode = ct.categoryCodeLayer2 "
			+ "AND ct.categoryLayer = 2")
	Optional<List<Category>> findByCategoryLayer2FromCategoryCodeLayer1(
			@Param("categoryCodeLayer1") String categoryCodeLayer1
			);
	
	
	@Query(value = 
			"SELECT "
			+ "ct "
			+ "FROM "
			+ "category ct "
			+ "WHERE ct.categoryCodeLayer2 = :categoryCodeLayer2 "
			+ "AND ct.categoryCode = ct.categoryCodeLayer3 "
			+ "AND ct.categoryLayer = 3")
	Optional<List<Category>> findByCategoryLayer3FromCategoryCodeLayer2(
			@Param("categoryCodeLayer2") String categoryCodeLayer2
			);
	
	
	
}
