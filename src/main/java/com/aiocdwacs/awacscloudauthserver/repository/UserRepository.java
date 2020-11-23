package com.aiocdwacs.awacscloudauthserver.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.aiocdwacs.awacscloudauthserver.model.User;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User,Long> {

	User findByUsername(String name);

	List<User> findAllByEmail(String email);
	
	User findByEmail(String email);

}