package com.example.kwms.outbound.feature;

import com.example.kwms.outbound.domain.Order;
import com.example.kwms.outbound.domain.OrderCustomer;
import com.example.kwms.outbound.domain.OrderProduct;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderClient {
    public Order getBy(final Long orderNo) {
        return new Order(
                orderNo,
                new OrderCustomer(
                        "name",
                        "email",
                        "phone",
                        "zipNo",
                        "address"
                ),
                "배송 요구사항",
                List.of(
                        new OrderProduct(
                                1L,
                                1L,
                                1500L)
                ));
    }
}
