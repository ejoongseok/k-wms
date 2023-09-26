package com.example.kwms.outbound.feature.query;


import com.example.kwms.outbound.domain.OutboundProduct;
import com.example.kwms.outbound.domain.OutboundRepository;
import com.example.kwms.outbound.domain.Product;
import com.example.kwms.outbound.feature.ProductClient;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class GetInquiryOutbound {
    private final OutboundRepository outboundRepository;
    private final ProductClient productClient;

    @GetMapping("/inquiry-outbounds/{toteBarcode}")
    @Transactional(readOnly = true)
    public Response inquiryOutbound(@PathVariable final String toteBarcode) {
        final var outbound = outboundRepository.getBy(toteBarcode);
        if (!outbound.isManualOutbound()) {
            if (!outbound.isPicked()) {
                throw new RuntimeException("아직 집품이 완료되지 않았습니다.");
            }
        }
        if (outbound.isCanceled()) {
            throw new RuntimeException("중지된 출고입니다.");
        }
        if (outbound.isInspected()) {
            throw new RuntimeException("검수가 완료된 출고입니다.");
        }

        final Long outboundNo = outbound.getOutboundNo();
        final Long orderNo = outbound.getOrderNo();

        final List<OutboundProduct> outboundProducts = outbound.getOutboundProducts();
        final List<Response.Product> products = new ArrayList<>();
        for (final OutboundProduct outboundProduct : outboundProducts) {
            final Long productNo = outboundProduct.getProductNo();
            final Product product = productClient.getBy(productNo);
            final String productName = product.getProductName();
            final Long quantity = outboundProduct.getQuantity();
            final Response.Product product1 = new Response.Product(productNo, productName, quantity);
            products.add(product1);
        }
        return new Response(
                outboundNo,
                orderNo,
                products
        );
    }

    private record Response(Long outboundNo, Long orderNo, List<Product> products) {

        public record Product(Long productNo, String name, Long quantity) {
        }
    }
}
