package com.goods.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.goods.shop.model.Item;

public interface ItemRepository extends JpaRepository <Item, Long> , QuerydslPredicateExecutor<Item>{
	
	Long deleteByItemId(Long itemId);

}
