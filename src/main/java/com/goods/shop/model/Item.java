package com.goods.shop.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "item")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long itemId;
	
	private String itemName;
	
	private String itemDescription;
	
	private String makerCode;
	
	private Integer price;
	
	private boolean saleStatus;
	
	private String images;
	
	private Integer sold;
	
	private String createUser;
	
	private Date createDate;
	
	private String updateUser;
	
	private Date updateDate;
	
	@Builder
	private Item(
			String itemName, String itemDescription, String makerCode,
			Integer price, boolean saleStatus, String images,
			Integer sold) {
		this.itemName = itemName;
		this.itemDescription = itemDescription;
		this.makerCode = makerCode;
		this.price = price;
		this.saleStatus = saleStatus;
		this.images = images;
		this.sold = sold;
	}
	
	
	public Item update(
			String itemName, String itemDescription, String makerCode,
			Integer price, boolean saleStatus, String images,
			Integer sold) {
		
		this.itemName = itemName;
		this.itemDescription = itemDescription;
		this.makerCode = makerCode;
		this.price = price;
		this.saleStatus = saleStatus;
		this.images = images;
		this.sold = sold;
		return this;
	}
}
