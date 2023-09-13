package com.example.kwms.outbound.domain;

import com.example.kwms.location.domain.Inventory;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

public class ConstructOutbound {
    public Outbound create(
            final Long warehouseNo,
            final List<Inventory> inventories,
            final List<PackagingMaterial> packagingMaterials,
            final Order order,
            final Boolean isPriorityDelivery,
            final LocalDate desiredDeliveryAt,
            final Long orderTotalWeight,
            final Long orderTotalVolume) {
//        validateInventory(inventories, order.getOrderProducts());
        return newOutbound(
                warehouseNo,
                order,
                findOptimalPackaging(packagingMaterials, orderTotalWeight, orderTotalVolume),
                isPriorityDelivery,
                desiredDeliveryAt);
    }

    private PackagingMaterial findOptimalPackaging(final List<PackagingMaterial> packagingMaterials, final Long totalWeight, final Long totalVolume) {
        return packagingMaterials.stream()
                .filter(pm -> pm.isAvailable(totalWeight, totalVolume))
                .min(Comparator.comparingLong(PackagingMaterial::outerVolume))
                .orElse(null);
    }

    private void validateInventory(final List<Inventory> inventories, final List<OrderProduct> orderProducts) {
        for (final OrderProduct orderProduct : orderProducts) {
            final long totalProductQuantity = inventories.stream()
                    .filter(i -> i.getProductNo().equals(orderProduct.getProductNo()))
                    .mapToLong(Inventory::getQuantity)
                    .sum();
            if (totalProductQuantity < orderProduct.getOrderQuantity())
                throw new IllegalArgumentException(
                        "재고가 부족합니다. 재고 수량:%d, 주문 수량:%d"
                                .formatted(totalProductQuantity, orderProduct.getOrderQuantity()));
        }
    }

    private Outbound newOutbound(
            final Long warehouseNo,
            final Order order,
            final PackagingMaterial packagingMaterial,
            final Boolean isPriorityDelivery,
            final LocalDate desiredDeliveryAt) {
        return new Outbound(
                warehouseNo,
                order.getOrderNo(),
                mapToOutboundProducts(order.getOrderProducts()),
                isPriorityDelivery,
                desiredDeliveryAt,
                packagingMaterial
        );
    }

    private List<OutboundProduct> mapToOutboundProducts(
            final List<OrderProduct> orderProducts) {
        return orderProducts.stream()
                .map(this::newOutboundProduct)
                .toList();
    }

    private OutboundProduct newOutboundProduct(final OrderProduct orderProduct) {
        return new OutboundProduct(
                orderProduct.getProductNo(),
                orderProduct.getOrderQuantity(),
                orderProduct.getUnitPrice());
    }
}
