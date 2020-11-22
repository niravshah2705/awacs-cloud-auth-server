package com.aiocdwacs.awacscloudauthserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aiocdwacs.awacscloudauthserver.model.Permission;

@Repository
public interface PermissionsRepository extends JpaRepository<Permission,Integer> {

}