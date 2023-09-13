package com.example.kwms.outbound.domain;

import com.example.kwms.common.NotFoundException;
import com.example.kwms.location.domain.Location;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "outbound")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Outbound {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "outbound_no")
    @Comment("출고 번호")
    private Long outboundNo;

    @Comment("주문 번호")
    @Column(name = "order_no")
    private Long orderNo;
    @OneToMany(mappedBy = "outbound", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<OutboundProduct> outboundProducts = new ArrayList<>();
    @Column(name = "is_priority_delivery", nullable = false)
    @Comment("우선 출고 여부")
    private Boolean isPriorityDelivery;
    @Column(name = "desired_delivery_at", nullable = false)
    @Comment("희망 출고일")
    private LocalDate desiredDeliveryAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "packaging_material_no")
    private PackagingMaterial recommendedPackagingMaterial;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "picking_tote_no")
    @Comment("집품할 토트 바구니")
    private Location pickingTote;

    public Outbound(
            final Long orderNo,
            final List<OutboundProduct> outboundProducts,
            final Boolean isPriorityDelivery,
            final LocalDate desiredDeliveryAt,
            final PackagingMaterial packagingMaterial) {
        validateConstructor(
                orderNo,
                outboundProducts,
                isPriorityDelivery,
                desiredDeliveryAt);
        recommendedPackagingMaterial = packagingMaterial;
        this.orderNo = orderNo;
        this.isPriorityDelivery = isPriorityDelivery;
        this.desiredDeliveryAt = desiredDeliveryAt;
        this.outboundProducts = outboundProducts;
        outboundProducts.forEach(outboundProduct -> outboundProduct.assignOutbound(this));
    }

    private void validateConstructor(
            final Long orderNo,
            final List<OutboundProduct> outboundProducts,
            final Boolean isPriorityDelivery,
            final LocalDate desiredDeliveryAt) {
        Assert.notNull(orderNo, "주문번호는 필수입니다.");
        Assert.notEmpty(outboundProducts, "출고상품은 필수입니다.");
        Assert.notNull(isPriorityDelivery, "우선출고여부는 필수입니다.");
        Assert.notNull(desiredDeliveryAt, "희망출고일은 필수입니다.");
    }

    public OutboundProduct createOutboundProductForSplit(final Long productNo, final Long quantity) {
        validateCreateOutboundProductForSplit(productNo, quantity);
        final OutboundProduct outboundProduct = getOutboundProduct(productNo);
        return new OutboundProduct(
                outboundProduct.getProductNo(),
                quantity,
                outboundProduct.getUnitPrice());
    }

    private void validateCreateOutboundProductForSplit(final Long productNo, final Long quantity) {
        Assert.notNull(productNo, "상품번호는 필수입니다.");
        Assert.notNull(quantity, "수량은 필수입니다.");
        if (1 > quantity) throw new IllegalArgumentException("수량은 1개 이상이어야 합니다.");
//        final OutboundProduct outboundProduct = getOutboundProduct(productNo);
//        if(outboundProduct.getPickings() != null) {
//            throw new IllegalArgumentException("이미 집품된 상품은 분할할 수 없습니다.");
//        }
    }

    private OutboundProduct getOutboundProduct(final Long productNo) {
        return outboundProducts.stream()
                .filter(outboundProduct -> outboundProduct.getProductNo().equals(productNo))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(
                        "상품번호에 해당하는 출고상품이 존재하지 않습니다. 상푸번호: %d".formatted(productNo)));
    }

    public Outbound copyFrom(final List<OutboundProduct> targets) {
        validateSplit(targets);
        return new Outbound(
                orderNo,
                targets,
                isPriorityDelivery,
                desiredDeliveryAt,
                null);
    }

    private void validateSplit(final List<OutboundProduct> targets) {
        Assert.notEmpty(targets, "분할할 출고상품은 필수입니다.");
        final long totalQuantity = outboundProducts.stream()
                .mapToLong(OutboundProduct::getQuantity)
                .sum();
        final long sum = targets.stream()
                .mapToLong(OutboundProduct::getQuantity)
                .sum();
        if (totalQuantity <= sum) throw new IllegalArgumentException("분할할 수량이 출고 수량보다 같거나 많습니다.");
    }

    public void decreaseQuantityForSplit(final Long productNo, final Long quantity) {
        validateDecreaseQuantityForSplit(productNo, quantity);
        final OutboundProduct outboundProduct = getOutboundProduct(productNo);
        outboundProduct.decreaseQuantity(quantity);
    }

    private void validateDecreaseQuantityForSplit(final Long productNo, final Long quantity) {
        Assert.notNull(productNo, "상품번호는 필수입니다.");
        Assert.notNull(quantity, "수량은 필수입니다.");
        if (1 > quantity) throw new IllegalArgumentException("수량은 1개 이상이어야 합니다.");
    }

    public void removeEmptyQuantityProducts() {
        outboundProducts.removeIf(OutboundProduct::isZeroQuantity);
    }

    public void assignRecommendedPackaging(final PackagingMaterial optimalPackaging) {
        Assert.notNull(optimalPackaging, "추천 포장재는 필수입니다.");
        recommendedPackagingMaterial = optimalPackaging;
    }
}
