package com.goods.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.goods.shop.model.Item;

public interface ItemRepository extends JpaRepository <Item, Long>{

}
