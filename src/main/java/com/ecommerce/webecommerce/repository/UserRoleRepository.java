package com.ecommerce.webecommerce.repository;

import com.ecommerce.webecommerce.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRole.UserRoleId> {

    void deleteByIdUserId(Long userId);
}