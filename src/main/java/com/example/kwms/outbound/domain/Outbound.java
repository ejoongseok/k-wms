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
import java.util.Optional;
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
    @Comment("추천 포장재")
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
    @Column(name = "canceled_at")
    @Comment("출고 취소 일시")
    private LocalDateTime canceledAt;
    @Column(name = "picked_at")
    @Comment("피킹 완료 일시")
    private LocalDateTime pickedAt;
    @Column(name = "inspected_at")
    @Comment("출고 검수 일시")
    private LocalDateTime inspectedAt;
    @Column(name = "packed_at")
    @Comment("포장 완료 일시")
    private LocalDateTime packedAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "real_packaging_material_no")
    @Comment("실제 포장재")
    private PackagingMaterial realPackagingMaterial;
    @Column(name = "packaged_weight_in_grams")
    @Comment("포장중량(g)")
    private Long packagedWeightInGrams;
    @Column(name = "box_width_in_millimeters")
    @Comment("포장 가로(mm)")
    private Long boxWidthInMillimeters;
    @Column(name = "box_length_in_millimeters")
    @Comment("포장 세로(mm)")
    private Long boxLengthInMillimeters;
    @Column(name = "box_height_in_millimeters")
    @Comment("포장 높이(mm)")
    private Long boxHeightInMillimeters;
    @Column(name = "tracking_number")
    @Comment("운송장 번호")
    private String trackingNumber;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bulk_outbound_no")
    @Comment("대량 출고 번호")
    private BulkOutbound bulkOutbound;

    @Column(name = "picker_no")
    @Comment("집품 작업자 USER_NO")
    private Long pickerNo;
    @Column(name = "is_manual_outbound")
    @Comment("수동 출고 여부")
    private boolean isManualOutbound;

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
            final Long outboundNo,
            final Long warehouseNo,
            final Long orderNo,
            final List<OutboundProduct> outboundProducts,
            final Boolean isPriorityDelivery,
            final LocalDate desiredDeliveryAt,
            final PackagingMaterial packagingMaterial,
            final Location pickingTote,
            final Long pickerNo) {
        this.outboundNo = outboundNo;
        this.warehouseNo = warehouseNo;
        recommendedPackagingMaterial = packagingMaterial;
        this.orderNo = orderNo;
        this.isPriorityDelivery = isPriorityDelivery;
        this.desiredDeliveryAt = desiredDeliveryAt;
        this.outboundProducts = outboundProducts;
        outboundProducts.forEach(outboundProduct -> outboundProduct.assignOutbound(this));
        this.pickingTote = pickingTote;
        this.pickerNo = pickerNo;
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

    public boolean isReady() {
        return !hasPickings() && null == pickingTote && !isCanceled();
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
        if (null == pickerNo) {
            throw new IllegalStateException("집품 작업자가 지정되지 않았습니다.");
        }
    }

    public void canceled(final String cancelReason) {
        canceledAt = LocalDateTime.now();
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
        return null != canceledAt && null != cancelReason;
    }

    public void scanToPick(final Inventory inventory) {
        validateScanToPick(inventory);
        final OutboundProduct outboundProduct = getOutboundProduct(inventory.getProductNo());
        outboundProduct.scanToPick(inventory);
        pickingTote.addInventory(inventory.getLpn());
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


    public void scanToPickManual(final Inventory inventory, final Long quantity) {
        validateScanToPickManual(inventory, quantity);
        final OutboundProduct outboundProduct = getOutboundProduct(inventory.getProductNo());
        outboundProduct.scanToPickManual(inventory, quantity);
        pickingTote.addManualInventory(inventory.getLpn(), quantity);
        final boolean allPicked = outboundProducts.stream()
                .allMatch(OutboundProduct::isPicked);
        if (allPicked) {
            pickedAt = LocalDateTime.now();
        }
    }

    public void appScanToPickManual(final Inventory inventory, final Long quantity) {
        validateScanToPickManual(inventory, quantity);
        final OutboundProduct outboundProduct = getOutboundProduct(inventory.getProductNo());
        outboundProduct.scanToPickManual(inventory, quantity);
        pickingTote.adjustPickManualInventory(inventory.getLpn(), quantity);
        final boolean allPicked = outboundProducts.stream()
                .allMatch(OutboundProduct::isPicked);
        if (allPicked) {
            pickedAt = LocalDateTime.now();
        }
    }


    private void validateScanToPickManual(final Inventory inventory, final Long quantity) {
        Assert.notNull(inventory, "스캔할 재고 정보가 없습니다.");
        Assert.notNull(quantity, "집품할 수량 정보가 없습니다.");
        if (1 > quantity) throw new IllegalArgumentException("집품할 수량은 1개 이상이어야 합니다.");
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

    public void inspected() {
        validateInspected();
        inspectedAt = LocalDateTime.now();
    }

    private void validateInspected() {
        if (isCanceled()) {
            throw new IllegalStateException("취소된 출고는 출고 검수를 할 수 없습니다.");
        }
        if (!isPicked()) {
            throw new IllegalStateException("출고 검수는 집품이 완료된 출고만 할 수 있습니다.");
        }
        if (isInspected()) {
            throw new IllegalStateException("이미 출고 검수가 완료된 출고입니다.");
        }
    }

    public boolean isInspected() {
        return null != inspectedAt;
    }

    public void packed(
            final PackagingMaterial packagingMaterial,
            final Long packagedWeightInGrams,
            final Long boxWidthInMillimeters,
            final Long boxLengthInMillimeters,
            final Long boxHeightInMillimeters) {
        validatePacked(
                packagingMaterial,
                packagedWeightInGrams,
                boxWidthInMillimeters,
                boxLengthInMillimeters,
                boxHeightInMillimeters);
        packedAt = LocalDateTime.now();
        realPackagingMaterial = packagingMaterial;
        this.packagedWeightInGrams = packagedWeightInGrams;
        this.boxWidthInMillimeters = boxWidthInMillimeters;
        this.boxLengthInMillimeters = boxLengthInMillimeters;
        this.boxHeightInMillimeters = boxHeightInMillimeters;
    }

    private void validatePacked(
            final PackagingMaterial packagingMaterial,
            final Long packagedWeightInGrams,
            final Long boxWidthInMillimeters,
            final Long boxLengthInMillimeters,
            final Long boxHeightInMillimeters) {
        Assert.notNull(packagingMaterial, "포장자재는 필수입니다.");
        Assert.notNull(packagedWeightInGrams, "포장중량은 필수입니다.");
        if (1 > packagedWeightInGrams) throw new IllegalArgumentException("포장중량은 1g 이상이어야 합니다.");
        Assert.notNull(boxWidthInMillimeters, "포장 가로는 필수입니다.");
        if (1 > boxWidthInMillimeters) throw new IllegalArgumentException("포장 가로는 1mm 이상이어야 합니다.");
        Assert.notNull(boxLengthInMillimeters, "포장 세로는 필수입니다.");
        if (1 > boxLengthInMillimeters) throw new IllegalArgumentException("포장 세로는 1mm 이상이어야 합니다.");
        Assert.notNull(boxHeightInMillimeters, "포장 높이는 필수입니다.");
        if (1 > boxHeightInMillimeters) throw new IllegalArgumentException("포장 높이는 1mm 이상이어야 합니다.");
        if (isCanceled()) {
            throw new IllegalStateException("취소된 출고는 포장완료를 할 수 없습니다.");
        }
        if (!isInspected()) {
            throw new IllegalStateException("출고 검수가 완료되지 않은 출고는 포장완료를 할 수 없습니다.");
        }
        if (isPacked()) {
            throw new IllegalStateException("이미 포장완료된 출고입니다.");
        }

    }

    public boolean isPacked() {
        return null != packedAt;
    }

    public void assignTrackingNumber(final String trackingNumber) {
        Assert.hasText(trackingNumber, "운송장번호는 필수입니다.");
        this.trackingNumber = trackingNumber;
    }

    public void reset() {
        validateReset();
        outboundProducts.forEach(OutboundProduct::reset);
        pickedAt = null;
        inspectedAt = null;
        packedAt = null;
        realPackagingMaterial = null;
        packagedWeightInGrams = null;
        boxWidthInMillimeters = null;
        boxLengthInMillimeters = null;
        boxHeightInMillimeters = null;
        trackingNumber = null;
        cancelReason = null;
        canceledAt = null;
        pickingTote = null;
        bulkOutbound = null;
        pickerNo = null;
    }

    private void validateReset() {
        if (!isCanceled()) {
            throw new IllegalStateException("취소된 출고만 초기화할 수 있습니다.");
        }
    }

    void assignBulkOutbound(final BulkOutbound bulkOutbound) {
        this.bulkOutbound = bulkOutbound;
    }

    void bulkPicked() {
        if (null != pickedAt) {
            throw new IllegalStateException("이미 피킹이 완료된 출고입니다.");
        }
        if (isCanceled()) {
            throw new IllegalStateException("취소된 출고는 피킹할 수 없습니다.");
        }

        pickedAt = LocalDateTime.now();
        outboundProducts.forEach(OutboundProduct::bulkPicked);
    }

    public void unassignBulkOutbound() {
        bulkOutbound = null;
    }

    public void allocatePicker(final Long userNo) {
        Assert.notNull(userNo, "작업자 USER_NO는 필수입니다.");
        if (null != pickerNo) {
            throw new IllegalStateException("이미 집품 작업자에게 할당된 출고가 있습니다.");
        }
        if (!isReady()) {
            throw new IllegalStateException("출고 대기 상태에서만 집품 작업자를 할당할 수 있습니다.");
        }
        pickerNo = userNo;
    }

    public void manualOutbound() {
        validateManualOutbound();
        isManualOutbound = true;
    }

    private void validateManualOutbound() {
        if (null != bulkOutbound) {
            throw new IllegalStateException("대량 출고된 출고는 수동 출고할 수 없습니다.");
        }
        if (isManualOutbound) {
            throw new IllegalStateException("이미 수동 출고된 출고입니다.");
        }
    }

    public void manualCompletePicking(final Long outboundProductNo) {
        validateCompletePicking(outboundProductNo);
        final OutboundProduct outboundProduct = getOutboundProductByOutboundProductNo(outboundProductNo);

        for (final Picking picking : outboundProduct.getPickings()) {
            final Inventory inventory = picking.getInventory();
            final Optional<Inventory> pickingToteInventory = pickingTote.getInventories().stream()
                    .filter(inventory1 -> inventory1.getLpn().equals(inventory.getLpn()))
                    .findFirst();
            if (pickingToteInventory.isPresent()) {
                final Inventory pickingToteInventory_ = pickingToteInventory.get();
                final Long totePickedQuantity = pickingToteInventory_.getQuantity();
                final Long quantityRequiredForPick = picking.getQuantityRequiredForPick();
                if (totePickedQuantity == quantityRequiredForPick) {
                    continue;
                }
                final long remainingQuantity = quantityRequiredForPick - totePickedQuantity;
                pickingTote.addManualInventory(pickingToteInventory_.getLpn(), remainingQuantity);
            } else {
                pickingTote.addManualInventory(inventory.getLpn(), picking.getQuantityRequiredForPick());
            }
        }

        outboundProduct.completePicking();

        final boolean allPicked = outboundProducts.stream()
                .allMatch(OutboundProduct::isPicked);
        if (allPicked) {
            pickedAt = LocalDateTime.now();
        }
    }

    private OutboundProduct getOutboundProductByOutboundProductNo(final Long outboundProductNo) {
        return outboundProducts.stream()
                .filter(op -> op.getOutboundProductNo().equals(outboundProductNo))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(
                        "출고상품이 존재하지 않습니다. 출고 상품 번호: %d".formatted(outboundProductNo)));
    }

    private void validateCompletePicking(final Long outboundProductNo) {
        Assert.notNull(outboundProductNo, "출고상품번호는 필수입니다.");
        final OutboundProduct outboundProduct = getOutboundProductByOutboundProductNo(outboundProductNo);
        if (outboundProduct.isPicked()) {
            throw new IllegalStateException("이미 집품이 완료된 출고상품입니다.");
        }
        if (null != pickedAt) {
            throw new IllegalStateException("이미 피킹이 완료된 출고입니다.");
        }
        if (isCanceled()) {
            throw new IllegalStateException("취소된 출고는 피킹할 수 없습니다.");
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
}
