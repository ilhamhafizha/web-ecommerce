package com.ecommerce.webecommerce.service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Random;

import com.ecommerce.webecommerce.common.errors.ResourceNotFoundException;
import com.ecommerce.webecommerce.entity.Order;
import com.ecommerce.webecommerce.entity.OrderItem;
import com.ecommerce.webecommerce.entity.Product;
import com.ecommerce.webecommerce.model.ShippingOrderRequest;
import com.ecommerce.webecommerce.model.ShippingOrderResponse;
import com.ecommerce.webecommerce.model.ShippingRateRequest;
import com.ecommerce.webecommerce.model.ShippingRateResponse;
import com.ecommerce.webecommerce.repository.OrderItemRepository;
import com.ecommerce.webecommerce.repository.OrderRepository;
import com.ecommerce.webecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MockShippingServiceImpl implements
        ShippingService {

    private static final BigDecimal BASE_RATE = BigDecimal.valueOf(10000);
    private static final BigDecimal RATE_PER_KG = BigDecimal.valueOf(2500);
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;

    @Override
    public ShippingRateResponse calculateShippingRate(ShippingRateRequest request) {
        BigDecimal shippingFee = BASE_RATE.add(
                        request.getTotalWeightInGrams().divide(BigDecimal.valueOf(1000)).multiply(RATE_PER_KG))
                .setScale(2, RoundingMode.HALF_UP);

        String estimatedDeliveryTime = "3 - 5 hari kerja";
        return ShippingRateResponse.builder()
                .shippingFee(shippingFee)
                .estimatedDeliveryTime(estimatedDeliveryTime)
                .build();
    }

    @Override
    public ShippingOrderResponse createShippingOrder(ShippingOrderRequest request) {
        String awbNumber = generateAwbNumber(request.getOrderId());

        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Order with id " + request.getOrderId() + " not found"));

        order.setStatus("SHIPPING");
        order.setAwbNumber(awbNumber);
        orderRepository.save(order);

        BigDecimal shippingFee = BASE_RATE.add(
                        request.getTotalWeightInGrams().divide(BigDecimal.valueOf(1000)).multiply(RATE_PER_KG))
                .setScale(2, RoundingMode.HALF_UP);

        String estimatedDeliveryTime = "3 - 5 hari kerja";

        return ShippingOrderResponse.builder()
                .awbNumber(awbNumber)
                .estimatedDeliveryTime(estimatedDeliveryTime)
                .shippingFee(shippingFee)
                .build();
    }

    @Override
    public String generateAwbNumber(Long orderId) {
        Random random = new Random();
        String prefix = "AWB";
        return String.format("%s%011d", prefix, random.nextInt(100000000));
    }


    @Override
    public BigDecimal calculateTotalWeight(Long orderId) {
        List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);
        return orderItems.stream()
                .map(orderItem -> {
                    Product product = productRepository.findById(orderItem.getProductId())
                            .orElseThrow(() -> new ResourceNotFoundException(
                                    "Product not found with id " + orderItem.getProductId()));

                    BigDecimal totalWeight = product.getWeight()
                            .multiply(BigDecimal.valueOf(orderItem.getQuantity()));
                    return totalWeight;
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}