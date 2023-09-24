package com.example.kwms.outbound.feature;

import com.example.kwms.outbound.domain.Order;
import com.example.kwms.outbound.domain.OrderCustomer;
import com.example.kwms.outbound.domain.OrderProduct;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Component
public class OrderClient {
    private final Map<Long, Order> orders = new HashMap<>();
    private final Faker faker = new Faker(Locale.KOREA);

    public OrderClient() {
        orders.put(1L, new Order(1L, createOrderCustomer(),
                faker.lorem().sentence(),
                List.of(newOrderProduct(1L, 1L))));
        orders.put(2L, new Order(2L, createOrderCustomer(),
                faker.lorem().sentence(),
                List.of(newOrderProduct(2L, 1L))));
        orders.put(3L, new Order(3L, createOrderCustomer(),
                faker.lorem().sentence(),
                List.of(newOrderProduct(3L, 1L))));
        orders.put(4L, new Order(4L, createOrderCustomer(),
                faker.lorem().sentence(),
                List.of(newOrderProduct(4L, 1L))));
        orders.put(5L, new Order(5L, createOrderCustomer(),
                faker.lorem().sentence(),
                List.of(newOrderProduct(5L, 1L))));
        orders.put(6L, new Order(6L, createOrderCustomer(),
                faker.lorem().sentence(),
                List.of(newOrderProduct(6L, 1L))));
        orders.put(7L, new Order(7L, createOrderCustomer(),
                faker.lorem().sentence(),
                List.of(newOrderProduct(7L, 1L))));
        orders.put(7L, new Order(8L, createOrderCustomer(),
                faker.lorem().sentence(),
                List.of(newOrderProduct(6L, 2L), newOrderProduct(7L, 2L))));
        orders.put(99L, new Order(99L, createOrderCustomer(),
                faker.lorem().sentence(),
                List.of(newOrderProduct(1L, 1L))));
    }

    private OrderProduct newOrderProduct(final long productNo, final long orderQuantity) {
        return new OrderProduct(
                productNo,
                orderQuantity,
                1500L);
    }

    private OrderCustomer createOrderCustomer() {
        return new OrderCustomer(faker.name().fullName(), faker.internet().emailAddress(), faker.phoneNumber().cellPhone(), faker.address().zipCode(), faker.address().fullAddress());
    }

    public Order getBy(final Long orderNo) {
        return orders.get(orderNo);
    }

    public List<Order> getAll() {
        return List.copyOf(orders.values());
    }
}
