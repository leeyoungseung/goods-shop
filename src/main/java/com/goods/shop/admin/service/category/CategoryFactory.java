package com.goods.shop.admin.service.category;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.goods.shop.admin.dto.CategoryDTO;

public class CategoryFactory {

	private static final Logger log = LogManager.getLogger(CategoryFactory.class);
	
	public static CategoryLogic getInstance(CategoryDTO dto) {
		
		CategoryLogic logic = null;
		if ((dto.getCategoryCode() != null && !dto.getCategoryCode().equals("")) 
				&& (dto.getCategoryCodeLayer1() != null && !dto.getCategoryCodeLayer1().equals("")) 
				&& (dto.getCategoryCodeLayer2() != null && !dto.getCategoryCodeLayer2().equals("")) 
				&& (dto.getCategoryCodeLayer3() != null && !dto.getCategoryCodeLayer3().equals("")) 
				) {
			log.debug("CategoryFactory => CategoryLogicLayer3");
			logic = new CategoryLogicLayer3();
			
		} else if ((dto.getCategoryCode() != null && !dto.getCategoryCode().equals("")) 
				&& (dto.getCategoryCodeLayer1() != null && !dto.getCategoryCodeLayer1().equals("")) 
				&& (dto.getCategoryCodeLayer2() != null && !dto.getCategoryCodeLayer2().equals("")) 
				) {
			log.debug("CategoryFactory => CategoryLogicLayer2");
			logic = new CategoryLogicLayer2();
			
		} else if ((dto.getCategoryCode() != null && !dto.getCategoryCode().equals("")) 
				&& (dto.getCategoryCodeLayer1() != null && !dto.getCategoryCodeLayer1().equals("")) 
				) {
			log.debug("CategoryFactory => CategoryLogicLayer1");
			logic = new CategoryLogicLayer1();
			
		} 
		
		logic.setDTO(dto);

		return logic;
	}
}
