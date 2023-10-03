package com.example.kwms.inbound.domain;

import com.example.kwms.common.NotFoundException;
import com.google.common.annotations.VisibleForTesting;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "purchase_order")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Getter
public class PurchaseOrder {
    @OneToMany(mappedBy = "purchaseOrder", orphanRemoval = true, cascade = CascadeType.ALL)
    private final List<PurchaseOrderProduct> purchaseOrderProducts = new ArrayList<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_order_no")
    @Comment("발주 번호")
    private Long purchaseOrderNo;
    @Column(name = "title", nullable = false)
    @Comment("발주 명")
    private String title;
    @Column(name = "description")
    @Comment("발주 설명")
    private String description;
    @Column(name = "warehouse_no", nullable = false)
    @Comment("창고 번호")
    private Long warehouseNo;
    @OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.ALL)
    private final List<Receive> receives = new ArrayList<>();

    public PurchaseOrder(
            final Long warehouseNo,
            final String title,
            final String description) {
        Assert.notNull(warehouseNo, "창고 번호는 필수입니다.");
        Assert.hasText(title, "입고 제목은 필수입니다.");
        this.warehouseNo = warehouseNo;
        this.title = title;
        this.description = description;
    }

    @VisibleForTesting
    PurchaseOrder(
            final Long purchaseOrderNo,
            final String title,
            final String description,
            final List<PurchaseOrderProduct> purchaseOrderProducts) {
        this.purchaseOrderNo = purchaseOrderNo;
        this.title = title;
        this.description = description;
        this.purchaseOrderProducts.addAll(purchaseOrderProducts);
    }

    public void assignProducts(final List<PurchaseOrderProduct> products) {
        validateAssignProducts(products);
        for (final PurchaseOrderProduct product : products) {
            product.assignInbound(this);
            purchaseOrderProducts.add(product);
        }
    }

    private void validateAssignProducts(final List<PurchaseOrderProduct> products) {
        Assert.notEmpty(products, "입고 상품은 필수입니다.");
        final Set<Long> productsSize = products.stream()
                .map(PurchaseOrderProduct::getProductNo)
                .collect(Collectors.toUnmodifiableSet());
        if (products.size() != productsSize.size()) {
            throw new IllegalArgumentException("상품 번호는 중복될 수 없습니다.");
        }
    }

    public void updatePurchaseOrder(
            final Long warehouseNo,
            final String title,
            final String description,
            final List<PurchaseOrderProduct> products) {
        validateUpdate(warehouseNo, title, description, products);
        this.warehouseNo = warehouseNo;
        this.title = title;
        this.description = description;
        purchaseOrderProducts.clear();
        purchaseOrderProducts.addAll(products);
        purchaseOrderProducts.forEach(ip -> ip.assignInbound(this));
    }

    private void validateUpdate(
            final Long warehouseNo,
            final String title,
            final String description,
            final List<PurchaseOrderProduct> products) {
        Assert.notNull(warehouseNo, "창고 번호는 필수입니다.");
        Assert.hasText(title, "입고 제목은 필수입니다.");
        Assert.notNull(description, "입고 설명은 필수입니다.");
        Assert.notEmpty(products, "입고 상품은 필수입니다.");
        if (isReceived())
            throw new IllegalArgumentException("이미 입고된 상품이 있으면 수정이 불가능합니다.");
        final Set<Long> productsSize = products.stream()
                .map(PurchaseOrderProduct::getProductNo)
                .collect(Collectors.toUnmodifiableSet());
        if (products.size() != productsSize.size()) {
            throw new IllegalArgumentException("상품 번호는 중복될 수 없습니다.");
        }
    }

    public void addReceive(final Receive receive) {
        validateAddReceive(receive);
        receives.add(receive);
        receive.assignPurchaseOrder(this);
    }

    private void validateAddReceive(final Receive receive) {
        Assert.notNull(receive, "입고는 필수입니다.");
        if (isReceiveCompleted()) {
            throw new IllegalArgumentException("이미 발주한 상품 모두 입고되었습니다.");
        }
        for (final ReceiveProduct receiveProduct : receive.getReceiveProducts()) {
            final Long productNo = receiveProduct.getProductNo();
            final List<ReceiveProduct> allReceiveProducts = receives.stream()
                    .flatMap(r -> r.getReceiveProducts().stream())
                    .toList();
            final long totalQuantityForProductNo = allReceiveProducts.stream()
                    .filter(rp -> rp.getProductNo().equals(productNo))
                    .mapToLong(ReceiveProduct::totalQuantity)
                    .sum();
            final Long requestQuantity = purchaseOrderProducts.stream()
                    .filter(product -> product.getProductNo().equals(productNo))
                    .findFirst()
                    .orElseThrow(() -> new NotFoundException(
                            "상품 번호에 해당하는 발주 상품이 존재하지 않습니다. 상품 번호: %s".formatted(productNo))).getRequestQuantity();
            if (requestQuantity < totalQuantityForProductNo + receiveProduct.getAcceptableQuantity() + receiveProduct.getRejectedQuantity()) {
                throw new IllegalArgumentException(
                        "발주 수량을 초과하여 입고할 수 없습니다. 상품 번호: %s, 발주 수량: %s, 입고 수량: %s".formatted(
                                productNo, requestQuantity, totalQuantityForProductNo + receiveProduct.getAcceptableQuantity() + receiveProduct.getRejectedQuantity()));
            }
        }
    }

    public PurchaseOrderProduct getPurchaseOrderProductBy(final Long purchaseOrderProductNo) {
        return purchaseOrderProducts.stream()
                .filter(product -> product.getPurchaseOrderProductNo().equals(purchaseOrderProductNo))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(
                        "발주 상품 번호에 해당하는 발주 상품이 존재하지 않습니다. 상품 번호: %s".formatted(purchaseOrderProductNo)));
    }

    public boolean isReceived() {
        return !receives.isEmpty();
    }

    public boolean isReceiveCompleted() {
        final long totalQuantity = purchaseOrderProducts.stream()
                .mapToLong(PurchaseOrderProduct::getRequestQuantity)
                .sum();
        final long totalReceivedQuantity = receives.stream()
                .flatMap(r -> r.getReceiveProducts().stream())
                .mapToLong(ReceiveProduct::totalQuantity)
                .sum();
        return totalQuantity == totalReceivedQuantity;
    }

    public void addLPN(
            final Long purchaseOrderProductNo,
            final LPN lpn) {
        validateAssignLPN(purchaseOrderProductNo, lpn);
        final var purchaseOrderProduct = getPurchaseOrderProductBy(purchaseOrderProductNo);
        purchaseOrderProduct.addLPN(lpn);
    }

    private void validateAssignLPN(
            final Long inboundProductNo,
            final LPN lpn) {
        Assert.notNull(inboundProductNo, "입고 상품 번호는 필수입니다.");
        Assert.notNull(lpn, "LPN은 필수입니다.");
    }

    public Receive getReceive(final Long receiveNo) {
        return receives.stream()
                .filter(r -> r.getReceiveNo().equals(receiveNo))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(
                        "입고 번호에 해당하는 입고가 존재하지 않습니다. 입고 번호: %s".formatted(receiveNo)));
    }
}
