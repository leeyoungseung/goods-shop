package com.goods.shop.dto;

import java.util.List;

import com.goods.shop.dto.AuthDTO.Response;
import com.goods.shop.model.Item;
import com.goods.shop.model.User;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemDTO {

	@Setter
	@Getter
	public static class Create {
		private String itemName;
		
		private String itemDescription;
		
		private String makerCode;
		
		private Integer price;
		
		private Integer saleStatus;
		
		private String [] images;
		
		private String createUser;
		
		public Create toEntity() {
			return this;
		}
		
	}
	
	@Setter
	@Getter
	public static class Update {
		private Long itemId;
		
		private String itemName;
		
		private String itemDescription;
		
		private String makerCode;
		
		private Integer price;
		
		private Integer saleStatus;
		
		private String [] images;
		
		private String updateUser;
		
		public Update toEntity() {
			return this;
		}
		
	}
	
	
	@Getter
	public static class Response {
		private Long itemId;
		private String itemName;
		private String itemDescription;
		private String makerCode;
		private Integer price;
		private Integer saleStatus;
		private String [] images;
		private Integer sold;
		
		@Builder
		private Response(Long itemId, String itemName, String itemDescription, String makerCode, Integer price,
				Integer saleStatus, String[] images, Integer sold) {
			this.itemId = itemId;
			this.itemName = itemName;
			this.itemDescription = itemDescription;
			this.makerCode = makerCode;
			this.price = price;
			this.saleStatus = saleStatus;
			this.images = images;
			this.sold = sold;
		}
		
		public static Response of (Long itemId, String itemName, String itemDescription, String makerCode, Integer price,
				Integer saleStatus, String[] images, Integer sold) {
			return Response.builder()
					.itemId(itemId)
					.itemName(itemName)
					.itemDescription(itemDescription)
					.makerCode(makerCode)
					.price(price)
					.saleStatus(saleStatus)
					.images(images)
					.sold(sold)
					.build();
		}

	}
	
    @Getter
    public static class ResponseOne {

        private Response response;

        public ResponseOne(Response response) {
            this.response = response;
        }
    }
    
    @Getter
    public static class ResponseList {

        private List<Response> responseList;

        public ResponseList(List<Response> responseList) {
            this.responseList = responseList;
        }
    }
}
