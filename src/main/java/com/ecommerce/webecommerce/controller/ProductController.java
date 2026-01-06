package com.ecommerce.webecommerce.controller;

import com.ecommerce.webecommerce.model.ProductRequest;
import com.ecommerce.webecommerce.model.ProductResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController // no usages
@RequestMapping("products")
public class ProductController {

    // localhost:3000/products/2
    @GetMapping("/{id}") // no usages
    public ResponseEntity<ProductResponse> findProductById(
            @PathVariable(value = "id") Long productId) {
        return ResponseEntity.ok(
                ProductResponse.builder()
                        .name("product" + productId)
                        .price(BigDecimal.ONE)
                        .description("deskripsi produk")
                        .build()
        );
    }

    // localhost:3000/products
    @GetMapping("") // no usages
    public ResponseEntity<List<ProductResponse>> getAllProduct() {
        return ResponseEntity.ok(
                List.of(
                        ProductResponse.builder()
                                .name("product 1")
                                .price(BigDecimal.ONE)
                                .description("deskripsi produk")
                                .build(),
                        ProductResponse.builder()
                                .name("product 1")
                                .price(BigDecimal.ONE)
                                .description("deskripsi produk")
                                .build()
                )
        );
    }

    @PostMapping("")
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest request) {
        return ResponseEntity.ok(
                ProductResponse.builder()
                        .name(request.getName())
                        .price(request.getPrice())
                        .description(request.getDescription())
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest request,
                                                         @PathVariable(value = "id") Long productId) {
        return ResponseEntity.ok(
                ProductResponse.builder()
                        .name(request.getName() + productId)
                        .price(request.getPrice())
                        .description(request.getDescription())
                        .build()
        );
    }
}
