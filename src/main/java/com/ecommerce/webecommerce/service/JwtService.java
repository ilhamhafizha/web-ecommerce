package com.ecommerce.webecommerce.service;

import com.ecommerce.webecommerce.model.UserInfo;

public interface JwtService {

    String generateToken(UserInfo userInfo);

    boolean validateToken(String token);

    String getUsernameFromToken(String token);
}