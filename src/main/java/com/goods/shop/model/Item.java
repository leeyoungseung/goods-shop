package com.goods.shop.model;


import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "item")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class Item extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long itemId;
	
	private String itemName;
	
	private String itemDescription;
	
	private String categoryCode;
	
	private String makerCode;
	
	private Integer price;
	
	private Integer saleStatus;
	
	private String images;
	
	private Integer sold;
	
	private String createUser;
	
	private String updateUser;
	
	@Builder
	private Item(
			String itemName, String itemDescription,
			String categoryCode, String makerCode,
			Integer price, Integer saleStatus, String images,
			Integer sold , String createUser, String updateUser) {
		this.itemName = itemName;
		this.itemDescription = itemDescription;
		this.categoryCode = categoryCode;
		this.makerCode = makerCode;
		this.price = price;
		this.saleStatus = saleStatus;
		this.images = images;
		this.sold = sold;
		this.createUser = createUser;
		this.updateUser = updateUser;
	}
	
	
	public Item update(
			String itemName, String itemDescription, 
			String categoryCode, String makerCode,
			Integer price, Integer saleStatus, String images , String updateUser
			) {
		
		this.itemName = itemName;
		this.itemDescription = itemDescription;
		this.categoryCode = categoryCode;
		this.makerCode = makerCode;
		this.price = price;
		this.saleStatus = saleStatus;
		this.images = images;
		this.updateUser = updateUser;
		return this;
	}
}
