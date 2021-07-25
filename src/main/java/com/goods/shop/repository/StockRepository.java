package com.goods.shop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.goods.shop.model.Stock;

public interface StockRepository extends JpaRepository<Stock, Long> {
	
	Optional<List<Stock>> findByItemId(Long itemId);

	Long deleteByStockId(Long stockId);
}
