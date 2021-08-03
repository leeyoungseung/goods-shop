package com.goods.shop.repository.predicate;

import java.util.HashMap;
import java.util.Map;

import com.goods.shop.model.QItem;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

public class ItemPredicate {
	
	public static Predicate search(Map searchParams) {
		HashMap params = (HashMap) searchParams;
		QItem item = QItem.item;
		BooleanBuilder builder = new BooleanBuilder();
		
		Long itemId = params.get("itemId") != null
				    ? (long) Long.parseLong((String) params.get("itemId"))
				    : null;
		if (itemId != null) {
			builder.and(item.itemId.eq(itemId));
		}
		
		String itemName = (String) params.get("itemName");
		if (itemName != null && !itemName.equals("")) {
			builder.and(item.itemName.eq(itemName));
		}
		
		String itemDescription = (String) params.get("itemDescription");
		if (itemDescription != null && !itemDescription.equals("")) {
			builder.and(item.itemDescription.eq(itemDescription));
		}
		
		String categoryCode = (String) params.get("categoryCode");
		if (categoryCode != null && !categoryCode.equals("")) {
			builder.and(item.categoryCode.eq(categoryCode));
		}
		
		String makerCode = (String) params.get("makerCode");
		if (makerCode != null && !makerCode.equals("")) {
			builder.and(item.makerCode.eq(makerCode));
		}
		
		Integer saleStatus = params.get("saleStatus") != null
			    ? (int) Integer.parseInt((String) params.get("saleStatus"))
			    : null;
	    if (saleStatus != null) {
			builder.and(item.saleStatus.eq(saleStatus));
		}
		
		return builder;
	}

}
