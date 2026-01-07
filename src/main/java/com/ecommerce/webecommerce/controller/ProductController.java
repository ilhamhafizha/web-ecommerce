package com.ecommerce.webecommerce.controller;

import com.ecommerce.webecommerce.model.ErrorResponse;
import com.ecommerce.webecommerce.model.ProductRequest;
import com.ecommerce.webecommerce.model.ProductResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<ProductResponse> createProduct(@RequestBody @Valid ProductRequest request) {
        return ResponseEntity.ok(
                ProductResponse.builder()
                        .name(request.getName())
                        .price(request.getPrice())
                        .description(request.getDescription())
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@RequestBody @Valid ProductRequest request,
                                                         @PathVariable(value = "id") Long productId) {
        return ResponseEntity.ok(
                ProductResponse.builder()
                        .name(request.getName() + productId)
                        .price(request.getPrice())
                        .description(request.getDescription())
                        .build()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorResponse handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(objectError -> {
            String fieldName = ((FieldError) objectError).getField();
            String errorMessage = objectError.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(errors.toString())
                .timestamp(LocalDateTime.now())
                .build();
    }
}
