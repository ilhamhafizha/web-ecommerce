package com.ecommerce.webecommerce.service;

import com.ecommerce.webecommerce.model.ProductRequest;
import com.ecommerce.webecommerce.model.ProductResponse;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductResponse> findAll();
    ProductResponse findById(Long id);
    ProductResponse create(ProductRequest productRequest);
    ProductResponse update(Long id, ProductRequest productRequest);
    void delete(Long id);
}
