package com.example.kwms.outbound.domain;

import lombok.Getter;

import java.util.List;
import java.util.Objects;

@Getter
public class SameOutbound {
    private final Long outboundNo;
    private final List<SameOutboundProduct> sameOutboundProducts;

    public SameOutbound(
            final Long outboundNo,
            final List<SameOutboundProduct> sameOutboundProducts) {
        this.outboundNo = outboundNo;
        this.sameOutboundProducts = sameOutboundProducts;
    }

    public static SameOutbound from(final Outbound outbound) {
        final List<SameOutboundProduct> sameOutboundProducts = outbound.getOutboundProducts().stream()
                .map(outboundProduct -> new SameOutboundProduct(outboundProduct.getProductNo(), outboundProduct.getQuantity()))
                .toList();
        return new SameOutbound(outbound.getOutboundNo(), sameOutboundProducts);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (null == o || getClass() != o.getClass()) return false;
        final SameOutbound that = (SameOutbound) o;

        // Check if both lists have the same size.
        if (sameOutboundProducts.size() != that.sameOutboundProducts.size()) return false;

        // Check if all elements in this list are present in the other list.
        for (final SameOutboundProduct product : sameOutboundProducts) {
            if (!that.sameOutboundProducts.contains(product)) return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = 1;
        for (final SameOutboundProduct product : sameOutboundProducts) {
            result = 31 * result + product.hashCode();
        }
        return result;
    }


    public static class SameOutboundProduct {
        private final Long productNo;
        private final Long quantity;

        public SameOutboundProduct(final Long productNo, final Long quantity) {
            this.productNo = productNo;
            this.quantity = quantity;
        }

        public Long getProductNo() {
            return productNo;
        }

        public Long getQuantity() {
            return quantity;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (null == o || getClass() != o.getClass()) return false;
            final SameOutboundProduct that = (SameOutboundProduct) o;
            return Objects.equals(productNo, that.productNo) &&
                    Objects.equals(quantity, that.quantity);
        }

        @Override
        public int hashCode() {
            return Objects.hash(productNo, quantity);
        }
    }
}
