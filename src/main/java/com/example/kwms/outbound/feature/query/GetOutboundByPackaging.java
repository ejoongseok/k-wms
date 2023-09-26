package com.example.kwms.outbound.feature.query;

import com.example.kwms.outbound.domain.Outbound;
import com.example.kwms.outbound.domain.OutboundProduct;
import com.example.kwms.outbound.domain.OutboundRepository;
import com.example.kwms.outbound.domain.PackagingMaterial;
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
public class GetOutboundByPackaging {
    private final OutboundRepository outboundRepository;
    private final ProductClient productClient;

    @GetMapping("/outbounds/tote-for-packaging/{toteBarcode}")
    @Transactional(readOnly = true)
    public Response findBy(
            @PathVariable final String toteBarcode) {
        final Outbound outbound = outboundRepository.getBy(toteBarcode);
        if (!outbound.isManualOutbound()) {
            if (!outbound.isPicked()) {
                throw new RuntimeException("아직 집품이 완료되지 않았습니다.");
            }
        }
        if (outbound.isCanceled()) {
            throw new RuntimeException("중지된 출고입니다.");
        }
        if (!outbound.isInspected()) {
            throw new RuntimeException("검수가 완료되지 않은 출고입니다.");
        }
        if (outbound.isPacked()) {
            throw new RuntimeException("이미 포장된 출고입니다.");
        }

        final List<Response.Product> products = new ArrayList<>();
        for (final OutboundProduct outboundProduct : outbound.getOutboundProducts()) {
            final Product product = productClient.getBy(outboundProduct.getProductNo());
            final String name = product.getProductName();
            final Long quantity = outboundProduct.getQuantity();
            final Response.Product product1 = new Response.Product(name, quantity);
            products.add(product1);
        }
        final PackagingMaterial recommendedPackagingMaterial = outbound.getRecommendedPackagingMaterial();
        return new Response(
                outbound.getOutboundNo(),
                outbound.getOrderNo(),
                products,
                recommendedPackagingMaterial.getName(),
                recommendedPackagingMaterial.getCode(),
                recommendedPackagingMaterial.getMaterialType().getDescription());
    }

    private record Response(
            Long outboundNo,
            Long orderNo,
            List<Product> products,
            String recommendedPackagingMaterialName,
            String recommendedPackagingMaterialCode,
            String recommendedPackagingMaterialType) {
        record Product(
                String name,
                Long quantity) {
        }
    }

}
