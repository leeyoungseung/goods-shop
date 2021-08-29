package com.goods.shop.repository.predicate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.goods.shop.model.QUser;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

public class UserPredicate {
	
	private static final Logger log = LogManager.getLogger(UserPredicate.class);
	
	public static Predicate search(Map searchParams) {
		log.info("UserPredicate search start "+searchParams);
		HashMap params = (HashMap) searchParams;
		QUser user = QUser.user;
		BooleanBuilder builder = new BooleanBuilder();
		
		String emailId = (String) params.get("emailId");
		if (emailId != null && !emailId.equals("")) {
			log.info("UserPredicate search emailId");
			builder.and(user.emailId.eq(emailId));
		}
		
		String startJoinDateStr = (String) params.get("startJoinDate");
		String endJoinDateStr   = (String) params.get("endJoinDate");
		if ((startJoinDateStr != null && !startJoinDateStr.equals("")) 
				&& (endJoinDateStr != null && !endJoinDateStr.equals(""))) {
			log.info("UserPredicate search between startJoinDate and endJoinDate");
			builder.and(user.joinDate.between(convertStrtoLocalDateTime(startJoinDateStr),
					convertStrtoLocalDateTime(endJoinDateStr)));
			
		}
		
		log.info("UserPredicate search end "+searchParams);
		return builder;
	}
	
	
	public static LocalDateTime convertStrtoLocalDateTime(String str) {
		
	    DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    LocalDate ld = LocalDate.parse(str, DATEFORMATTER);
	    LocalDateTime ldt = LocalDateTime.of(ld, LocalDateTime.now().toLocalTime());
	    
	    return ldt;
	}

}
