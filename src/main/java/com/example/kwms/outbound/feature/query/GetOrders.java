package com.example.kwms.outbound.feature.query;

import com.example.kwms.outbound.domain.Order;
import com.example.kwms.outbound.feature.OrderClient;
import com.example.kwms.outbound.feature.ProductClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class GetOrders {
    private final OrderClient orderClient;
    private final ProductClient productClient;

    @GetMapping("/orders")
    public List<Response> findAll() {
        final List<Order> orders = orderClient.getAll();
        final List<Response> responses = new ArrayList<>();
        for (final Order order : orders) {
            final Long orderNo = order.getOrderNo();
            final String deliveryRequirements = order.getDeliveryRequirements();
            final String customerName = order.getOrderCustomer().getName();
            // 상품명/수량 : 상품명/수량 이렇게 Join
            final String productNames = order.getOrderProducts().stream()
                    .map(orderProduct -> productClient.getBy(orderProduct.getProductNo()).getProductName() + "/" + orderProduct.getOrderQuantity())
                    .reduce((a, b) -> a + ", " + b)
                    .orElse("");
            responses.add(new Response(orderNo, deliveryRequirements, customerName, productNames));
        }
        return responses;
    }

    private record Response(
            Long orderNo,
            String deliveryRequirements,
            String customerName,
            String productNames) {

    }
}
