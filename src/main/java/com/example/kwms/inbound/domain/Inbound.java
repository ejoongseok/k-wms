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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "inbound")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Inbound {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inbound_no")
    @Comment("입고 번호")
    private Long inboundNo;
    @Column(name = "title", nullable = false)
    @Comment("입고명")
    @Getter
    private String title;
    @Column(name = "estimated_arrival_at", nullable = false)
    @Comment("입고 예정일")
    private LocalDateTime estimatedArrivalAt;
    @Column(name = "order_requested_at", nullable = false)
    @Comment("주문 요청일")
    private LocalDateTime orderRequestedAt;
    @Column(name = "description")
    @Comment("입고 설명")
    private String description;
    @Getter
    @OneToMany(mappedBy = "inbound", orphanRemoval = true, cascade = CascadeType.ALL)
    private final List<InboundProduct> inboundProducts = new ArrayList<>();
    @Column(name = "warehouse_no", nullable = false)
    @Comment("창고 번호")
    private Long warehouseNo;

    public Inbound(
            final Long warehouseNo,
            final String title,
            final LocalDateTime estimatedArrivalAt,
            final LocalDateTime orderRequestedAt,
            final String description) {
        Assert.notNull(warehouseNo, "창고 번호는 필수입니다.");
        Assert.hasText(title, "입고 제목은 필수입니다.");
        Assert.notNull(estimatedArrivalAt, "입고 예정일은 필수입니다.");
        Assert.notNull(orderRequestedAt, "주문 요청일은 필수입니다.");
        Assert.notNull(inboundProducts, "입고 상품은 필수입니다.");
        this.warehouseNo = warehouseNo;
        this.title = title;
        this.estimatedArrivalAt = estimatedArrivalAt;
        this.orderRequestedAt = orderRequestedAt;
        this.description = description;
    }

    @VisibleForTesting
    Inbound(
            final Long inboundNo,
            final String title,
            final LocalDateTime estimatedArrivalAt,
            final LocalDateTime orderRequestedAt,
            final String description,
            final List<InboundProduct> inboundProducts) {
        this.inboundNo = inboundNo;
        this.title = title;
        this.estimatedArrivalAt = estimatedArrivalAt;
        this.orderRequestedAt = orderRequestedAt;
        this.description = description;
        this.inboundProducts.addAll(inboundProducts);
    }

    public void assignProducts(final List<InboundProduct> products) {
        Assert.notEmpty(products, "입고 상품은 필수입니다.");
        for (final InboundProduct product : products) {
            product.assignInbound(this);
            inboundProducts.add(product);
        }
    }

    public void addProduct(final InboundProduct inboundProduct) {
        Assert.notNull(inboundProduct, "입고 상품은 필수입니다.");

        inboundProduct.added();
        inboundProduct.assignInbound(this);
        inboundProducts.add(inboundProduct);
    }

    public void update(
            final Long warehouseNo,
            final String title,
            final LocalDateTime estimatedArrivalAt,
            final LocalDateTime orderRequestedAt,
            final String description) {
        validateUpdate(warehouseNo, title, estimatedArrivalAt, orderRequestedAt, description);
        this.warehouseNo = warehouseNo;
        this.title = title;
        this.estimatedArrivalAt = estimatedArrivalAt;
        this.orderRequestedAt = orderRequestedAt;
        this.description = description;
    }

    private void validateUpdate(
            final Long warehouseNo,
            final String title,
            final LocalDateTime estimatedArrivalAt,
            final LocalDateTime orderRequestedAt,
            final String description) {
        Assert.notNull(warehouseNo, "창고 번호는 필수입니다.");
        Assert.hasText(title, "입고 제목은 필수입니다.");
        Assert.notNull(estimatedArrivalAt, "입고 예정일은 필수입니다.");
        Assert.notNull(orderRequestedAt, "주문 요청일은 필수입니다.");
        Assert.notNull(description, "입고 설명은 필수입니다.");
    }

    public void updateProduct(
            final Long inboundProductNo,
            final Long productNo,
            final Long requestQuantity,
            final Long unitPrice,
            final String description) {
        validateUpdateProduct(inboundProductNo, productNo, requestQuantity, unitPrice);
        final InboundProduct inboundProduct = getInboundProduct(inboundProductNo);

        inboundProduct.update(productNo, requestQuantity, unitPrice, description);
    }

    private void validateUpdateProduct(
            final Long inboundProductNo,
            final Long productNo,
            final Long requestQuantity,
            final Long unitPrice) {
        Assert.notNull(inboundProductNo, "입고 상품 번호는 필수입니다.");
        Assert.notNull(productNo, "상품 번호는 필수입니다.");
        Assert.notNull(requestQuantity, "상품 입고 요청 수량은 필수입니다.");
        if (1 > requestQuantity)
            throw new IllegalArgumentException("상품 입고 요청 수량은 1개 이상이어야 합니다.");
        Assert.notNull(unitPrice, "상품 입고 요청 단가는 필수입니다.");
        if (0 > unitPrice)
            throw new IllegalArgumentException("상품 입고 요청 단가는 0원 이상이어야 합니다.");
    }

    private InboundProduct getInboundProduct(final Long inboundProductNo) {
        return inboundProducts.stream()
                .filter(product -> product.equalsId(inboundProductNo))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(
                        "입고 상품이 존재하지 않습니다. 입고 상품 번호: %s".formatted(inboundProductNo)));
    }

    public void registerInspectionResult(
            final Long inboundProductNo,
            final LocalDateTime inspectedAt,
            final LocalDateTime arrivedAt,
            final String inspectionComment,
            final Long acceptableQuantity,
            final Long rejectedQuantity) {
        final InboundProduct inboundProduct = getInboundProduct(inboundProductNo);
        inboundProduct.registerInspectionResult(
                inspectedAt,
                arrivedAt,
                inspectionComment,
                acceptableQuantity,
                rejectedQuantity);
    }

    public void assignLPN(
            final Long inboundProductNo,
            final String lpnBarcode,
            final LocalDateTime expiringAt) {
        validateAssignLPN(inboundProductNo, lpnBarcode, expiringAt);
        final InboundProduct inboundProduct = getInboundProduct(inboundProductNo);
        inboundProduct.assignLPN(lpnBarcode, expiringAt);
    }

    private void validateAssignLPN(
            final Long inboundProductNo,
            final String lpnBarcode,
            final LocalDateTime expiringAt) {
        Assert.notNull(inboundProductNo, "입고 상품 번호는 필수입니다.");
        Assert.hasText(lpnBarcode, "LPN 바코드는 필수입니다.");
        Assert.notNull(expiringAt, "유통기한은 필수입니다.");
    }
}
