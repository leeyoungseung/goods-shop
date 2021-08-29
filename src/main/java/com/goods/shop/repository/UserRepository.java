package com.goods.shop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.goods.shop.model.User;

public interface UserRepository extends JpaRepository<User, Long> , QuerydslPredicateExecutor<User>{

	Optional<User> findByEmailId(String username);
	Optional<User> findByRefreshToken(String refreshToken);
}
