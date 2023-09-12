package com.example.kwms.outbound.feature;

import com.example.kwms.outbound.domain.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductClient {
    public Product getBy(final Long productNo) {
        final Long weightInGrams = 100L;
        final Long widthInMillimeters = 10L;
        final Long heightInMillimeters = 10L;
        final Long lengthInMillimeters = 10L;
        return new Product(
                productNo,
                "상품명",
                "상품바코드",
                "상품옵션",
                weightInGrams,
                widthInMillimeters,
                heightInMillimeters,
                lengthInMillimeters
        );
    }
}
