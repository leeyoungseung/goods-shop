package com.goods.shop.repository.predicate;

import java.util.HashMap;
import java.util.Map;

import com.goods.shop.model.QCategory;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

public class CategoryPredicate {
	
	public static Predicate search(Map searchParams) { 
		HashMap params = (HashMap)searchParams;
		QCategory category = QCategory.category;
		BooleanBuilder builder = new BooleanBuilder();
		
		// 카테고리 계층은 필수 파라미터로 설정
		Integer categoryLayer = params.get("categoryLayer") != null
				    ? (int) Integer.parseInt((String) params.get("categoryLayer"))
				    : null;
		if (categoryLayer != null) {
			builder.and(category.categoryLayer.eq(categoryLayer));
		}
		
		String categoryCode = (String) params.get("categoryCode");
		if (categoryCode != null && !categoryCode.equals("")) {
			builder.and(category.categoryCode.eq(categoryCode));
		}
		
		String categoryName = (String) params.get("categoryName");
		if (categoryName != null && !categoryName.equals("")) {
			builder.and(category.categoryName.contains(categoryName));
		}
		
		String categoryDescription = (String) params.get("categoryDescription");
		if (categoryDescription != null && !categoryDescription.equals("")) {
			builder.and(category.categoryDescription.contains(categoryDescription));
		}
		
		Integer categoryStatus = params.get("categoryStatus") != null
				    ? (int) Integer.parseInt((String) params.get("categoryStatus"))
				    : null;
		if (categoryStatus != null) {
			builder.and(category.categoryStatus.eq(categoryStatus));
		}
		
		return builder;
	}

}
