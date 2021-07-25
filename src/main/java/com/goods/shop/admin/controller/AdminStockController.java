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

import com.goods.shop.admin.dto.ItemStockDTO;
import com.goods.shop.admin.dto.StockDTO;
import com.goods.shop.admin.service.AdminStockService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/adm/stocks")
public class AdminStockController {

	private static final Logger log = LogManager.getLogger(AdminStockController.class);
	
	private final AdminStockService stockService;
	
	/**
	 * 상품 하나에 해당하는 재고 정보가져오기
	 * @return
	 */
	@GetMapping("/item/{itemId}")
	public ResponseEntity<ItemStockDTO> getItemStocks(
			@PathVariable("itemId") Long itemId
			) {
		
		return ResponseEntity.ok(stockService.getItemStocks(itemId));
	}
	
	
	/**
	 * 재고 리스트 가져오기
	 * @return
	 */
    @GetMapping
	public ResponseEntity<List<StockDTO>> getStocks() {
		
		return ResponseEntity.ok(stockService.getStocks());
	}
	
	
    /**
     * 재고 데이터 생성
     * @return
     */
    @PostMapping
	public ResponseEntity<StockDTO> createStock(
			@RequestBody StockDTO create
			) {
		
		return ResponseEntity.ok(stockService.createStock(create));
	}
	
	
	/**
	 * 재고 상세 데이터 가져오기
	 * @return
	 */
    @GetMapping
	public ResponseEntity<StockDTO> getStock(
			@PathVariable("stockId") Long stockId
			) {
		
		return ResponseEntity.ok(stockService.getStock(stockId));
	}
	
	
    /**
     * 재고 데이터 갱신
     * @return
     */
    @PutMapping("/{stockId}")
	public ResponseEntity<StockDTO> updateStock(
			@PathVariable("stockId") Long stockId,
			@RequestBody StockDTO update
			) {
		
		return ResponseEntity.ok(stockService.updateStock(stockId, update));
	}
	
	
	/**
	 * 재고 데이터 삭제
	 * @return
	 */
    @DeleteMapping("/{stockId}")
	public ResponseEntity<String> deleteStock(
			@PathVariable("stockId") Long stockId
			) {
		
		return ResponseEntity.ok(stockService.deleteStock(stockId));
	}
}
