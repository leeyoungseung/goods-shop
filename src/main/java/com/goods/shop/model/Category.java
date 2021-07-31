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
@Entity(name = "category")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class Category extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long categoryId;
	
	private String categoryCode;
	
	private Integer categoryLayer;
	
	private String categoryCodeLayer1;
	
	private String categoryCodeLayer2;
	
	private String categoryCodeLayer3;
	
	private String categoryName;
	
	private String categoryDescription;

	// 0: 비활성, 1: 활성
	private Integer categoryStatus;
	
	private String createUser;
	
	private String updateUser;
	
	
	@Builder
	private Category(
			String categoryCode, Integer categoryLayer, String categoryCodeLayer1, String categoryCodeLayer2,
			String categoryCodeLayer3, String categoryName, String categoryDescription,
			Integer categoryStatus, String createUser, String updateUser
			) {
		this.categoryCode = categoryCode;
		this.categoryLayer = categoryLayer;
		this.categoryCodeLayer1 = categoryCodeLayer1;
		this.categoryCodeLayer2 = categoryCodeLayer2;
		this.categoryCodeLayer3 = categoryCodeLayer3;
		this.categoryName = categoryName;
		this.categoryDescription = categoryDescription;
		this.categoryStatus = categoryStatus;
		this.createUser = createUser;
		this.updateUser = updateUser;
	}
	
	public Category update(	
			String categoryName, String categoryDescription,
			Integer categoryStatus, String updateUser
			) {
		this.categoryName = categoryName;
		this.categoryDescription = categoryDescription;
		this.categoryStatus = categoryStatus;
		this.updateUser = updateUser;
		return this;
	}
	
	
}
