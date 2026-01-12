package com.ecommerce.webecommerce.service;

import com.ecommerce.webecommerce.model.PaginatedProductResponse;
import com.ecommerce.webecommerce.model.ProductRequest;
import com.ecommerce.webecommerce.model.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductResponse> findAll();


    Page<ProductResponse> findByPage(Pageable pageable);

    Page<ProductResponse> findByNameAndPageable(String name, Pageable pageable);

    ProductResponse findById(Long id);

    ProductResponse create(ProductRequest productRequest);

    ProductResponse update(Long id, ProductRequest productRequest);

    void delete(Long id);

    PaginatedProductResponse convertProductPage(Page<ProductResponse> responses);
}
