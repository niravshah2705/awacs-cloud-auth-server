package com.aiocdwacs.awacscloudauthserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aiocdwacs.awacscloudauthserver.model.User;

@Repository
public interface UserDetailsRepository extends JpaRepository<User,Integer> {

	User findByUsername(String name);

}