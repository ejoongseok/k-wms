package com.example.kwms.outbound.domain;

import com.example.kwms.common.NotFoundException;
import com.example.kwms.location.domain.Inventory;
import com.example.kwms.location.domain.Location;
import com.google.common.annotations.VisibleForTesting;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Column(name = "warehouse_no", nullable = false)
    @Comment("출고할 창고")
    private Long warehouseNo;
    @Column(name = "cancel_reason")
    @Comment("출고 취소 사유")
    private String cancelReason;
    @Column(name = "cancelled_at")
    @Comment("출고 취소 일시")
    private LocalDateTime cancelledAt;
    @Column(name = "picked_at")
    @Comment("피킹 완료 일시")
    private LocalDateTime pickedAt;

    public Outbound(
            final Long warehouseNo,
            final Long orderNo,
            final List<OutboundProduct> outboundProducts,
            final Boolean isPriorityDelivery,
            final LocalDate desiredDeliveryAt,
            final PackagingMaterial packagingMaterial) {
        validateConstructor(
                warehouseNo,
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
        this.warehouseNo = warehouseNo;
    }

    @VisibleForTesting
    Outbound(
            final Long warehouseNo,
            final Long orderNo,
            final List<OutboundProduct> outboundProducts,
            final Boolean isPriorityDelivery,
            final LocalDate desiredDeliveryAt,
            final PackagingMaterial packagingMaterial,
            final Location pickingTote) {
        this.warehouseNo = warehouseNo;
        recommendedPackagingMaterial = packagingMaterial;
        this.orderNo = orderNo;
        this.isPriorityDelivery = isPriorityDelivery;
        this.desiredDeliveryAt = desiredDeliveryAt;
        this.outboundProducts = outboundProducts;
        outboundProducts.forEach(outboundProduct -> outboundProduct.assignOutbound(this));
        this.pickingTote = pickingTote;
    }

    private void validateConstructor(
            final Long warehouseNo,
            final Long orderNo,
            final List<OutboundProduct> outboundProducts,
            final Boolean isPriorityDelivery,
            final LocalDate desiredDeliveryAt) {
        Assert.notNull(warehouseNo, "창고번호는 필수입니다.");
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
        if (!isReady()) {
            throw new IllegalStateException("출고 분할은 출고 대기 상태에서만 가능합니다.");
        }
        Assert.notNull(productNo, "상품번호는 필수입니다.");
        Assert.notNull(quantity, "수량은 필수입니다.");
        if (1 > quantity) throw new IllegalArgumentException("수량은 1개 이상이어야 합니다.");
        final OutboundProduct outboundProduct = getOutboundProduct(productNo);
        if (outboundProduct.hasPickings()) {
            throw new IllegalArgumentException("이미 집품된 상품은 분할할 수 없습니다.");
        }
    }

    private boolean isReady() {
        return !hasPickings() || null == pickingTote;
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
                warehouseNo,
                orderNo,
                targets,
                isPriorityDelivery,
                desiredDeliveryAt,
                null);
    }

    private void validateSplit(final List<OutboundProduct> targets) {
        if (!isReady()) {
            throw new IllegalStateException("출고 분할은 출고 대기 상태에서만 가능합니다.");
        }
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
        if (!isReady()) {
            throw new IllegalStateException("출고 분할은 출고 대기 상태에서만 가능합니다.");
        }
        Assert.notNull(productNo, "상품번호는 필수입니다.");
        Assert.notNull(quantity, "수량은 필수입니다.");
        if (1 > quantity) throw new IllegalArgumentException("수량은 1개 이상이어야 합니다.");
    }

    public void removeEmptyQuantityProducts() {
        if (isCanceled()) {
            throw new IllegalStateException("출고 취소된 출고는 변경할 수 없습니다.");
        }
        outboundProducts.removeIf(OutboundProduct::isZeroQuantity);
    }

    public void assignRecommendedPackaging(final PackagingMaterial optimalPackaging) {
        Assert.notNull(optimalPackaging, "추천 포장재는 필수입니다.");
        if (isCanceled()) {
            throw new IllegalStateException("출고 취소된 출고는 변경할 수 없습니다.");
        }
        recommendedPackagingMaterial = optimalPackaging;
    }

    public void allocatePickingTote(final Location tote) {
        validateToteAllocation(tote);
        pickingTote = tote;
    }

    private void validateToteAllocation(final Location tote) {
        Assert.notNull(tote, "출고에 할당할 토트는 필수 입니다.");
        if (isCanceled()) {
            throw new IllegalStateException("출고 취소된 출고는 변경할 수 없습니다.");
        }
        if (!tote.isTote()) {
            throw new IllegalArgumentException("할당하려는 로케이션이 토트가 아닙니다.");
        }
        if (tote.hasAvailableInventory()) {
            throw new IllegalArgumentException("할당하려는 토트에 상품이 존재합니다.");
        }
        if (null != pickingTote) {
            throw new IllegalStateException("이미 출고에 토트가 할당되어 있습니다.");
        }
        if (null == recommendedPackagingMaterial) {
            throw new IllegalStateException("포장재가 할당되어 있지 않습니다.");
        }
    }

    public void cancelled(final String cancelReason) {
        cancelledAt = LocalDateTime.now();
        this.cancelReason = cancelReason;
    }

    public void transferWarehouse(final Long toWarehouseNo) {
        Assert.notNull(toWarehouseNo, "출고할 창고는 필수입니다.");
        if (isCanceled()) {
            throw new IllegalStateException("출고 취소된 출고는 변경할 수 없습니다.");
        }
        if (!isReady()) {
            throw new IllegalStateException("출고 대기 상태에서만 출고할 창고를 변경할 수 있습니다.");
        }
        warehouseNo = toWarehouseNo;
    }

    public Set<Long> getProductNos() {
        return outboundProducts.stream()
                .map(OutboundProduct::getProductNo)
                .collect(Collectors.toUnmodifiableSet());
    }

    public void allocatePicking(final List<Inventory> inventories) {
        Assert.notEmpty(inventories, "집품을 할당하려는 재고 정보가 없습니다.");
        for (final OutboundProduct outboundProduct : outboundProducts) {
            outboundProduct.allocatePicking(inventories);
        }
    }

    public List<Picking> getPickings() {
        return outboundProducts.stream()
                .flatMap(outboundProduct -> outboundProduct.getPickings().stream())
                .collect(Collectors.toUnmodifiableList());
    }

    public boolean hasPickings() {
        return outboundProducts.stream()
                .anyMatch(OutboundProduct::hasPickings);
    }

    public boolean isCanceled() {
        return null != cancelledAt && null != cancelReason;
    }

    public void scanToPick(final Inventory inventory) {
        validateScanToPick(inventory);
        final OutboundProduct outboundProduct = getOutboundProduct(inventory.getProductNo());
        outboundProduct.scanToPick(inventory);
        final boolean allPicked = outboundProducts.stream()
                .allMatch(OutboundProduct::isPicked);
        if (allPicked) {
            pickedAt = LocalDateTime.now();
        }
    }

    private void validateScanToPick(final Inventory inventory) {
        Assert.notNull(inventory, "스캔할 재고 정보가 없습니다.");
        if (isCanceled()) {
            throw new IllegalStateException("취소된 출고는 집품을 할 수 없습니다.");
        }
        if (!hasPickings()) {
            throw new IllegalStateException("집품 목록이 할당된 상태에서만 집품할 수 있습니다.");
        }
        if (null == pickingTote) {
            throw new IllegalStateException("토트가 할당되지 않은 출고는 집품할 수 없습니다.");
        }
        if (!pickingTote.isTote()) {
            throw new IllegalStateException("토트가 아닌 로케이션은 집품할 수 없습니다.");
        }

    }

    public boolean isPicked() {
        return null != pickedAt;
    }
}
