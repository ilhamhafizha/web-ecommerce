package com.ecommerce.webecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder // no usages
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    String name;
    BigDecimal price;
    String description;
}
