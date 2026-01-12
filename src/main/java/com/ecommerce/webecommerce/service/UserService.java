package com.ecommerce.webecommerce.service;


import com.ecommerce.webecommerce.model.UserRegisterRequest;
import com.ecommerce.webecommerce.model.UserResponse;
import com.ecommerce.webecommerce.model.UserUpdateRequest;

public interface UserService {
    UserResponse register(UserRegisterRequest registerRequest);
    UserResponse findById(Long id);
    UserResponse findByKeyword(String keyword);
    UserResponse updateUser(Long id, UserUpdateRequest request);
    void deleteUser(Long id);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
