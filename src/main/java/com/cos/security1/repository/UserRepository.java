package com.cos.security1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.security1.model.User;


// CRUD 함수를 JPA가 들고 있음
// @Repository라는 어노테이션이 없어도 Ioc가 된다. JpaRepository를 상속받기 때문
public interface UserRepository extends JpaRepository<User, Integer>{

	public User findByUsername(String username);
	
}
