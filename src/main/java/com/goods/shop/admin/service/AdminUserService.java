package com.goods.shop.admin.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goods.shop.dto.UserDTO;
import com.goods.shop.exception.NotFoundException;
import com.goods.shop.model.User;
import com.goods.shop.repository.UserRepository;
import com.goods.shop.repository.predicate.UserPredicate;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminUserService {

	private static final Logger log = LogManager.getLogger(AdminUserService.class);
	
	private final UserRepository userRepository;
	
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	/**
	 * 유저 데이터 검색
	 * 
	 * @param searchParams
	 * @return
	 */
	public List<UserDTO> searchUsers(Map searchParams) {
		List<User> userList = (List<User>) userRepository.findAll(UserPredicate.search(searchParams));
		List<UserDTO> resList = new ArrayList<UserDTO>();
		
		log.info("searchUsers Response Data check : "+resList);
		
		userList.forEach((user) -> {
			resList.add(mappingDTO(user));
		});
		
		return resList;
	}
	
	
	/**
	 * 전체 유저 리스트 가져오기  
	 * 
	 * @return
	 */
	public List<UserDTO> getUsers() {
		log.info("getUsers");
		List<User> userList = userRepository.findAll();
		List<UserDTO> resList = new ArrayList<UserDTO>();
		
		log.info("getUsers Response Data check : "+resList);
		
		userList.forEach((user) -> {
			resList.add(mappingDTO(user));
		});
		
		return resList;
	}
	
	
	/**
	 * 유저 상세 정보가져오기 
	 * 
	 * @return
	 */
	public UserDTO getUser(Long userNo) {
		log.info("getUser userNo : "+userNo);
		
		User user = userRepository.findById(userNo)
				.orElseThrow(NotFoundException::new);
		
		return mappingDTO(user);
	}
	
	
	/**
	 * 유저 데이터 갱신
	 * 
	 * @param userNo
	 * @param update
	 * @return
	 */
	@Transactional
	public UserDTO updateUser(Long userNo, UserDTO update) {
		// 1. 전송된 파라미터가 DB에 존재하는지 확인
		log.info("updateUser Requested Param userNo : "+userNo);
		User entity = userRepository.findById(userNo)
				.orElseThrow(NotFoundException::new);
		
		log.info("updateUser Response Data check : "+entity);
		
		// 2. 전송된 파라미터로 레코드 갱신
		entity.update(
				bCryptPasswordEncoder.encode(update.getPassword()),
		        update.getGivenName(),
		        update.getFamilyName()
				);
		
		// 3. 갱신 결과를 리턴 
		User user = userRepository.save(entity);
		
		
		return mappingDTO(user);
	}
	
	
	/**
	 * DTO에 엔티티 데이터 매핑
	 * @param user
	 * @return
	 */
	private UserDTO mappingDTO(User user) {
		UserDTO dto = new UserDTO();
		dto.setUserNo(user.getId());
		dto.setEmailId(user.getEmailId());
		dto.setJoinDate(user.getJoinDate());
		
		return dto;
	}
}
