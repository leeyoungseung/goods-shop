package com.goods.shop.admin.controller;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.goods.shop.admin.service.AdminUserService;
import com.goods.shop.dto.UserDTO;
import com.goods.shop.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/adm/users")
public class AdminUserController {

	private static final Logger log = LogManager.getLogger(AdminUserController.class);
	
	private final AdminUserService userService;
	
	/**
	 * 유저 정보 검색
	 * 
	 * @param searchParams
	 * @return
	 */
	@GetMapping("/search")
	public ResponseEntity<List<UserDTO>> searchUsers(
			@RequestParam(required = false) Map searchParams
			) {
		log.info("searchUsers searchParams="+searchParams);
		
		if (searchParams.isEmpty())
			return ResponseEntity.ok(userService.getUsers());
		
		return ResponseEntity.ok(userService.searchUsers(searchParams));
		
	}
	
	
	/**
	 * 유저 정보 상세보기 
	 * 
	 */
	@GetMapping("/{userNo}")
	public ResponseEntity<UserDTO> getUser(
			@PathVariable("userNo") Long userNo
			) {
		log.info("getUser userNo="+userNo);
		UserDTO res = userService.getUser(userNo);
		
		return ResponseEntity.ok(res);
	}
	
	
	/**
	 * 유저정보 수정
	 * 
	 */
	@PutMapping("/{userNo}")
	public ResponseEntity<UserDTO> updateUser(
			@PathVariable("userNo") Long userNo,
			@RequestBody UserDTO update
			){
		log.info("updateUser userNo="+userNo+" UserDTO="+update);
		
		UserDTO res = userService.updateUser(userNo, update);
		
		return ResponseEntity.ok(res);
	}
	
	
	
}
