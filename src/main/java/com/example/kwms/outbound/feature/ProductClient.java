package com.example.kwms.outbound.feature;

import com.example.kwms.outbound.domain.Product;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Component
public class ProductClient {
    private final Map<Long, Product> products = new HashMap<>();
    private final Faker faker = new Faker(Locale.KOREA);


    public ProductClient() {
        products.put(1L, newProduct(1L, 100L, 10L, 10L, 10L));
        products.put(2L, newProduct(2L, 1000L, 15L, 8L, 10L));
        products.put(3L, newProduct(3L, 2500L, 20L, 5L, 10L));
        products.put(4L, newProduct(4L, 300L, 30L, 6L, 10L));
        products.put(5L, newProduct(5L, 500L, 50L, 7L, 10L));
        products.put(6L, newProduct(6L, 1500L, 15L, 15L, 10L));
        products.put(7L, newProduct(7L, 600L, 20L, 20L, 10L));
    }
    public Product getBy(final Long productNo) {
        return products.get(productNo);
    }

    private Product newProduct(final Long productNo, final Long weightInGrams, final Long widthInMillimeters, final Long heightInMillimeters, final Long lengthInMillimeters) {
        return new Product(
                productNo,
                faker.commerce().productName(),
                faker.commerce().promotionCode(),
                faker.commerce().material(),
                weightInGrams,
                widthInMillimeters,
                heightInMillimeters,
                lengthInMillimeters
        );
    }
}
