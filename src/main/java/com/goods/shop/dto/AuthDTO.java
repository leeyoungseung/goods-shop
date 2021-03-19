package com.goods.shop.dto;

import java.util.List;

import com.goods.shop.model.User;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthDTO {

	@Setter
	@Getter
	public static class Create {
		private String username;
		
		private String password;
		
		private String givenName;
		
		private String familyName;
		
		public User toEntity() {
			return User.builder()
					.username(username)
					.password(password)
					.givenName(givenName)
					.familyName(familyName)
					.build();
		}
	}
	
	@Setter
	@Getter
	public static class Update {
		private String password;
		private String givenName;
		private String familyName;
		
		public User apply(User user) {
			return user.update(password, givenName, familyName);
		}
	}
	
	@Getter
	public static class Response {
		private Long id;
		private String email;
		//private String password;
		private String givenName;
		private String familyName;
		
		@Builder
		private Response(Long id, String username, String givenName, String familyName) {
			this.id = id;
			this.email = username;
			this.givenName = givenName;
			this.familyName = familyName;
		}
		
		public static Response of(User user) {
			return Response.builder()
					.id(user.getId())
					.username(user.getUsername())
					.givenName(user.getGivenName())
					.familyName(user.getFamilyName())
					.build();
		}
	}
	
	@Getter
	public static class ResponseToken {
		private String authorization;
		
		public ResponseToken(String authorization) {
			this.authorization = authorization;
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
