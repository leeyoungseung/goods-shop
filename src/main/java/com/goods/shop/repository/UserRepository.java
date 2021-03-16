package com.goods.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.goods.shop.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	public User findByUsername(String username);
}
