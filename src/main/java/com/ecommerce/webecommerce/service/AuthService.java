package com.ecommerce.webecommerce.service;

import com.ecommerce.webecommerce.model.AuthRequest;
import com.ecommerce.webecommerce.model.UserInfo;

public interface AuthService {

    UserInfo authenticate(AuthRequest authRequest);
}
