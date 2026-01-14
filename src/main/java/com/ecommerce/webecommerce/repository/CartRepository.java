package com.ecommerce.webecommerce.repository;

import java.util.Optional;

import com.ecommerce.webecommerce.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    boolean existsByUserId(Long userId);

    Optional<Cart> findByUserId(Long userId);
}
