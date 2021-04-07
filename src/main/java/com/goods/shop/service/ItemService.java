package com.goods.shop.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.goods.shop.dto.ItemDTO;
import com.goods.shop.dto.ItemDTO.Response;
import com.goods.shop.exception.NotFoundException;
import com.goods.shop.model.Item;
import com.goods.shop.repository.ItemRepository;
import com.goods.shop.utils.TextUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {

	private static final Logger log = LogManager.getLogger(ItemService.class);
	
	private final ItemRepository itemRepository;
	
	@Value("${property.app.record-separator}")
	private String recodeSeparator;
	
	
	public ItemDTO.Response createItem(ItemDTO.Create create) {
		
		// 1. DTO에서 데이터 가져와 가공하기 (이미지 배열) 
		TextUtil textUtil = TextUtil.getInstance();
		for (String str : create.getImages()) {
			log.info("Requested Data create : "+str);
		}
		
		log.info("Requested Data create 2 : "+textUtil.convertFormatRecode(recodeSeparator, create.getImages()));
		
		
		// 2. entity에 매핑
		Item item = Item.builder()
				.itemName(create.getItemName())
				.itemDescription(create.getItemDescription())
				.price(create.getPrice())
				.makerCode(create.getMakerCode())
				.saleStatus(create.getSaleStatus())
				.images(textUtil.convertFormatRecode(recodeSeparator, create.getImages()))
				.sold(0)
				.createUser(create.getCreateUser())
				.build();
	    
		// 3. DB에 등록 
		log.info("Requested Data check : "+item);
		Item res = itemRepository.save(item);
		
		log.info("Response Data check : "+res);
		
		// 4. 결과 (등록된 Item정보) 리턴
		return ItemDTO.Response.of(
					res.getItemId(),
					res.getItemName(),
					res.getItemDescription(),
					res.getMakerCode(),
					res.getPrice(),
					res.getSaleStatus(),
					textUtil.makeArrayFromStr(recodeSeparator, res.getImages()),
					res.getSold() == null ? 0 : res.getSold()
				);
	}


	public ItemDTO.Response getItem(Long itemId) {
		TextUtil textUtil = TextUtil.getInstance();
		
		log.info("Requested Param itemId : "+itemId);
		Item res = itemRepository.findById(itemId)
				.orElseThrow(NotFoundException::new);
		
		log.info("Response Data check : "+res);

		return ItemDTO.Response.of(
				res.getItemId(),
				res.getItemName(),
				res.getItemDescription(),
				res.getMakerCode(),
				res.getPrice(),
				res.getSaleStatus(),
				textUtil.makeArrayFromStr(recodeSeparator, res.getImages()),
				res.getSold() == null ? 0 : res.getSold()
			);
	}
	
	
}