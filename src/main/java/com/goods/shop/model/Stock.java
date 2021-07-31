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
import lombok.NonNull;

@Data
@Entity(name = "stock")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class Stock extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long stockId;
	
	@NonNull
	private Long itemId;
	
	private Integer stockQuantity;
	
	private String createUser;
	
	private String updateUser;
	
	
	@Builder
	private Stock (
			Long itemId, Integer stockQuantity,
			String createUser, String updateUser
			) {
		this.itemId = itemId;
		this.stockQuantity = stockQuantity;
		this.createUser = createUser;
		this.updateUser = updateUser;
	}
	
	
	public Stock update(Integer stockQuantity, String updateUser) {
		this.stockQuantity = stockQuantity;
		this.updateUser = updateUser;
		return this;
	}
}
