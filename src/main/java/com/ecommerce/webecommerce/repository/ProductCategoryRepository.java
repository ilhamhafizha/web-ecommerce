package com.ecommerce.webecommerce.repository;

import com.ecommerce.webecommerce.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, ProductCategory.ProductCategoryId> {

}
