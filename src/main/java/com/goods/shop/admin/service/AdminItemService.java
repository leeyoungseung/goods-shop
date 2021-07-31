package com.goods.shop.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goods.shop.admin.dto.ItemDTO;
import com.goods.shop.exception.NotFoundException;
import com.goods.shop.model.Item;
import com.goods.shop.repository.ItemRepository;
import com.goods.shop.utils.TextUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminItemService {

	private static final Logger log = LogManager.getLogger(AdminItemService.class);
	
	private final ItemRepository itemRepository;
	
	TextUtil textUtil = TextUtil.getInstance();
	
	@Value("${property.app.record-separator}")
	private String recodeSeparator;
	
	
	/**
	 * 상품 데이터 생성
	 * @param create
	 * @return
	 */
	public ItemDTO createItem(ItemDTO create) {	
		// 1. DTO에서 데이터 가져와 가공하기 (이미지 배열) 
		
		for (String str : create.getImages()) {
			log.info("Requested Data create : "+str);
		}
		
		log.info("Requested Data create 2 : "+textUtil.convertFormatRecode(recodeSeparator, create.getImages()));
		
		
		// 2. entity에 매핑
		Item item = Item.builder()
				.itemName(create.getItemName())
				.itemDescription(create.getItemDescription())
				.categoryCode(create.getCategoryCode())
				.price(create.getPrice())
				.makerCode(create.getMakerCode())
				.saleStatus(create.getSaleStatus())
				.images(textUtil.convertFormatRecode(recodeSeparator, create.getImages()))
				.sold(0)
				.createUser(create.getCreateUser())
				.build();
	    
		// 3. DB에 등록 
		log.info("Requested Data check : "+item);
		item = itemRepository.save(item);
		
		log.info("Response Data check : "+item);
		
		// 4. 결과 (등록된 Item정보) 리턴
		return mappingDTO(item);
	}

	
	/**
	 * 상품 데이터 리스트 가져오기 
	 * @return
	 */
	public List<ItemDTO> getItems() {	
		List<Item> itemList = itemRepository.findAll();
		List<ItemDTO> resList = new ArrayList<ItemDTO>();
		log.info("Response Data check : "+resList);
		
		itemList.forEach((item) -> {
			resList.add(mappingDTO(item));
		});
		
		return resList;
	}
	
	
	/**
	 * 상품 데이터 한개 가져오기
	 * @param itemId
	 * @return
	 */
	public ItemDTO getItem(Long itemId) {	
		log.info("Requested Param itemId : "+itemId);
		Item item = itemRepository.findById(itemId)
				.orElseThrow(NotFoundException::new);
		
		log.info("Response Data check : "+item);
		
		return mappingDTO(item);
	}

	/**
	 * 상품 데이터 갱신
	 * @param itemId
	 * @param update
	 * @return
	 */
	@Transactional
	public ItemDTO updateItem(Long itemId, ItemDTO update) {
		// 1. 전송된 파라미터가 DB에 존재하는지 확인 
		log.info("updateItem Requested Param itemId : "+itemId);
		Item entity = itemRepository.findById(itemId)
				.orElseThrow(NotFoundException::new);
		
		log.info("updateItem Response Data check : "+entity);
		
		// 2. 전송된 파라미터로 레코드 갱신
		entity.update(update.getItemName(),
				update.getItemDescription(),
				update.getCategoryCode(),
				update.getMakerCode(),
				update.getPrice(),
				update.getSaleStatus(),
				textUtil.convertFormatRecode(recodeSeparator, update.getImages()),
				update.getUpdateUser()
				);
		
		
		// 3. 갱신 결과를 리턴 
		Item item = itemRepository.save(entity);
		
		return mappingDTO(item);
	}

	
	/**
	 * 상품 데이터 삭제
	 * @param itemId
	 * @return
	 */
	@Transactional
	public String deleteItem(Long itemId) {
		
		Long res = itemRepository.deleteByItemId(itemId);
		
		return (res == 1) ? "Delete Success itemId : [" + itemId + "]" : "Delete Failure itemId : [" + itemId + "]";
	}
	
	
	/**
	 * DTO에 엔티티 데이터 매핑
	 * @param item
	 * @return
	 */
	private ItemDTO mappingDTO(Item item) {
		ItemDTO dto = new ItemDTO();
		dto.setItemId(item.getItemId());
		dto.setItemName(item.getItemName());
		dto.setItemDescription(item.getItemDescription());
		dto.setCategoryCode(item.getCategoryCode());
		dto.setMakerCode(item.getMakerCode());
		dto.setPrice(item.getPrice());
		dto.setSaleStatus(item.getSaleStatus());
		dto.setImages(textUtil.makeArrayFromStr(recodeSeparator, item.getImages()));
		dto.setSold(item.getSold() == null ? 0 : item.getSold());
		
		return dto;
	}
}
