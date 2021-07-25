package com.goods.shop.admin.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goods.shop.admin.dto.ItemStockDTO;
import com.goods.shop.admin.dto.StockDTO;
import com.goods.shop.exception.NotFoundException;
import com.goods.shop.model.Stock;
import com.goods.shop.repository.ItemRepository;
import com.goods.shop.repository.StockRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminStockService {

	private static final Logger log = LogManager.getLogger(AdminStockService.class);
	
	private final StockRepository stockRepository;
	
	private final ItemRepository itemRepository;
	
	
	
	/**
	 * 재고 등록
	 * @param create
	 * @return
	 */
	public StockDTO createStock(StockDTO create) {
		// 1. param check
		log.info("createStock Param StockDTO = "+ create);
		
		
		// 2. exist item from DB ?
		if (itemRepository.existsById(create.getItemId())) {
			throw new NotFoundException();
		}
		
		// 3. save stock data in DB
		Stock stock = Stock.builder()
				.itemId(create.getItemId())
				.stockQuantity(create.getStockQuantity())
				.createUser(create.getCreateUser())
				.updateUser(create.getUpdateUser())
				.build();
		
		log.info("Before Stock Data save  = "+ stock);
		stock = stockRepository.save(stock);
		
		log.info("After Stock Data save = "+ stock);
		
		
		// 4. return value.
		return mappingDTO(stock);
	}
	
	
	/**
	 * 재고 리스트 전체 가져오기
	 * @return
	 */
	public List<StockDTO> getStocks() {
		log.info("getStocks");
		List<Stock> stockList = stockRepository.findAll();
		List<StockDTO> resList = new ArrayList<StockDTO>();
		
		stockList.forEach((stock) -> {
			resList.add(mappingDTO(stock));
		});
		
		return resList;
	}
	
	
	/**
	 * 재고 상세 정보가져오기
	 * @param stockId
	 * @return
	 */
	public StockDTO getStock(Long stockId) {
		log.info("getStock stockId = "+stockId);
		Stock stock = stockRepository.findById(stockId)
				.orElseThrow(NotFoundException::new);
		
		log.info("Response Data check = "+stock);
		
		return mappingDTO(null);
	}
	
	
	/**
	 * 재고 데이터 갱신
	 * @param stockId
	 * @param update
	 * @return
	 */
	@Transactional
	public StockDTO updateStock(Long stockId, StockDTO update) {
		// 1. 전송된 파라미터에 해당하는 데이터가 존재하는지 확인
		log.info("updateStock Requested Param stockId = "+stockId+" , StockDTO = "+update);
		
		Stock stock = stockRepository.findById(stockId)
				.orElseThrow(NotFoundException::new);
		
		if (itemRepository.existsById(update.getItemId()) && stock == null) {
			throw new NotFoundException();
		}
		
		
		// 2. 데이터 갱신
		stock.update(update.getStockQuantity(), update.getUpdateUser());
		
		// 3. 갱신결과 리턴
		return mappingDTO(stockRepository.save(stock));
	}
	
	
	/**
	 * 재고 데이터 갱신
	 * @param stockId
	 * @return
	 */
	@Transactional
	public String deleteStock(Long stockId) {
		log.info("deleteStock Requested Param stockId = "+stockId);
		
		Long res = stockRepository.deleteByStockId(stockId);
		
		return (res == 1) ? "Delete Success stockId : [" + stockId + "]" : "Delete Failure stockId : [" + stockId + "]";
	}
	
	
	/**
	 * 상품 하나에 해당하는 재고 정보가져오기
	 * @param itemId
	 * @return
	 */
	public ItemStockDTO getItemStocks(Long itemId) {
		// 1. 전송된 파라미터에 해당하는 데이터가 존재하는지 확인
		log.info("getItemStock Requested Param itemId = "+itemId);
		
		Optional<List<Stock>> stockData = stockRepository.findByItemId(itemId);
		
		if (itemRepository.existsById(itemId) && stockData.isEmpty()) {
			throw new NotFoundException();
		}
		
		
		// 2. 데이터를 가져와서 DTO에 매핑 
		ItemStockDTO dto = new ItemStockDTO();
		dto.setItemId(itemId);
		
		List<StockDTO> stockDTOList = new ArrayList<StockDTO>();
		stockData.get().forEach((stock) -> {
			stockDTOList.add(mappingDTO(stock));
		});
		
		dto.setStockList(stockDTOList);
		dto.setEntireStockQuantity(stockDTOList.size());
		
		
		// 3. 매핑한 DTO를 리턴
		return dto;
		
	}
	
	
	/**
	 * DTO에 엔티티 데이터 매핑
	 * @param stock
	 * @return
	 */
	private StockDTO mappingDTO(Stock stock) {
		StockDTO dto = new StockDTO();
		dto.setStockId(stock.getStockId());
		dto.setItemId(stock.getItemId());
		dto.setStockQuantity(stock.getStockQuantity());
		
		return dto;
	}
	
}
