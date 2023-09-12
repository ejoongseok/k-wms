package com.example.kwms.outbound.domain;

import com.example.kwms.common.NotFoundException;

import java.util.Comparator;
import java.util.List;

public class OutboundSplitter {

    public Outbound split(
            final Outbound outbound,
            final List<OutboundProduct> targets,
            final List<PackagingMaterial> packagingMaterials,
            final List<Product> products) {
        final Outbound split = outbound.copyFrom(targets);
        for (final OutboundProduct target : targets) {
            outbound.decreaseQuantityForSplit(target.getProductNo(), target.getQuantity());
        }
        outbound.removeEmptyQuantityProducts();
        outbound.assignRecommendedPackaging(findOptimalPackaging(packagingMaterials, outbound.getOutboundProducts(), products));
        split.assignRecommendedPackaging(findOptimalPackaging(packagingMaterials, split.getOutboundProducts(), products));
        return split;
    }

    private PackagingMaterial findOptimalPackaging(
            final List<PackagingMaterial> packagingMaterials,
            final List<OutboundProduct> outboundProducts,
            final List<Product> products) {
        final Long totalWeight = calculateTotalWeight(outboundProducts, products);
        final Long totalVolume = calculateTotalVolume(outboundProducts, products);
        return findOptimalPackaging(packagingMaterials, totalWeight, totalVolume);
    }

    private Long calculateTotalWeight(
            final List<OutboundProduct> outboundProducts,
            final List<Product> products) {
        Long totalWeight = 0L;
        for (final OutboundProduct outboundProduct : outboundProducts) {
            final Product product = getProduct(products, outboundProduct.getProductNo());
            totalWeight += product.getWeightInGrams() * outboundProduct.getQuantity();
        }
        return totalWeight;
    }

    private Long calculateTotalVolume(
            final List<OutboundProduct> outboundProducts,
            final List<Product> products) {
        Long totalVolume = 0L;
        for (final OutboundProduct outboundProduct : outboundProducts) {
            final Product product = getProduct(products, outboundProduct.getProductNo());
            totalVolume += product.volume() * outboundProduct.getQuantity();
        }
        return totalVolume;
    }

    private Product getProduct(final List<Product> products, final Long productNo) {
        return products.stream()
                .filter(product1 -> product1.getProductNo().equals(productNo))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(
                        "상품번호에 해당하는 상품이 존재하지 않습니다. 상품번호: %d".formatted(productNo)));
    }

    private PackagingMaterial findOptimalPackaging(final List<PackagingMaterial> packagingMaterials, final Long totalWeight, final Long totalVolume) {
        return packagingMaterials.stream()
                .filter(pm -> pm.isAvailable(totalWeight, totalVolume))
                .min(Comparator.comparingLong(PackagingMaterial::outerVolume))
                .orElse(null);
    }
}