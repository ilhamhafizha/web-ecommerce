package com.ecommerce.webecommerce.controller;

import com.ecommerce.webecommerce.model.ErrorResponse;
import com.ecommerce.webecommerce.model.ProductRequest;
import com.ecommerce.webecommerce.model.ProductResponse;
import com.ecommerce.webecommerce.service.ProductService;
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

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    // localhost:3000/products/2
    @GetMapping("/{id}") // no usages
    public ResponseEntity<ProductResponse> findProductById(
            @PathVariable(value = "id") Long productId) {
        ProductResponse productResponse = productService.findById(productId);
        return ResponseEntity.ok(productResponse);
    }

    // localhost:3000/products
    @GetMapping("") // no usages
    public ResponseEntity<List<ProductResponse>> getAllProduct() {
        List<ProductResponse> productResponse = productService.findAll();
        return ResponseEntity.ok(productResponse);
    }

    @PostMapping("")
    public ResponseEntity<ProductResponse> createProduct(@RequestBody @Valid ProductRequest request) {
        ProductResponse response = productService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@RequestBody @Valid ProductRequest request,
                                                         @PathVariable(value = "id") Long productId) {
      ProductResponse response = productService.update(productId,request);
      return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@RequestBody @Valid ProductRequest request,
                                                         @PathVariable(value = "id") Long productId) {
       productService.delete(productId);
       return ResponseEntity.noContent().build();
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
