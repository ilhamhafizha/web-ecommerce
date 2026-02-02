package com.ecommerce.webecommerce.service;

import com.ecommerce.webecommerce.model.UserAddressRequest;
import com.ecommerce.webecommerce.model.UserAddressResponse;

import java.util.List;

public interface UserAddressService {

    UserAddressResponse create(Long userId, UserAddressRequest request);

    List<UserAddressResponse> findByUserId(Long userId);

    UserAddressResponse findById(Long id);

    UserAddressResponse update(Long addressId, UserAddressRequest request);

    void delete(Long addressId);

    UserAddressResponse setDefaultAddress(Long userId, Long addressId);

}
