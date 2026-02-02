package com.ecommerce.webecommerce.service;

import com.ecommerce.webecommerce.model.ShippingOrderRequest;
import com.ecommerce.webecommerce.model.ShippingOrderResponse;
import com.ecommerce.webecommerce.model.ShippingRateRequest;
import com.ecommerce.webecommerce.model.ShippingRateResponse;

import java.math.BigDecimal;

public interface ShippingService {

    ShippingRateResponse calculateShippingRate(ShippingRateRequest request);

    ShippingOrderResponse createShippingOrder(ShippingOrderRequest request);

    String generateAwbNumber(Long orderId);

    BigDecimal calculateTotalWeight(Long orderId);
}
