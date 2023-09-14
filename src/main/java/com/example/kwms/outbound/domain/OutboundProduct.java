package com.example.kwms.outbound.domain;

import com.example.kwms.location.domain.Inventory;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Entity
@Table(name = "outbound_product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OutboundProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "outbound_product_no")
    @Comment("출고 상품 번호")
    @Getter
    private Long outboundProductNo;
    @Column(name = "product_no", nullable = false)
    @Comment("상품 번호")
    @Getter
    private Long productNo;
    @Column(name = "quantity", nullable = false)
    @Comment("출고 수량")
    @Getter
    private Long quantity;
    @Column(name = "unit_price", nullable = false)
    @Comment("단가")
    @Getter
    private Long unitPrice;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "outbound_no", nullable = false)
    @Comment("출고 번호")
    private Outbound outbound;
    @Getter
    @OneToMany(mappedBy = "outboundProduct", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Picking> pickings = new ArrayList<>();
    @Column(name = "picked_at")
    @Comment("피킹 완료 일시")
    private LocalDateTime pickedAt;

    public OutboundProduct(
            final Long productNo,
            final Long quantity,
            final Long unitPrice) {
        validateConstructor(productNo, quantity, unitPrice);
        this.productNo = productNo;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    @VisibleForTesting
    OutboundProduct(
            final Long productNo,
            final Long orderQuantity,
            final Long unitPrice,
            final List<Picking> pickings) {
        this.productNo = productNo;
        quantity = orderQuantity;
        this.unitPrice = unitPrice;
        this.pickings.addAll(pickings);
    }

    private void validateConstructor(final Long productNo, final Long orderQuantity, final Long unitPrice) {
        Assert.notNull(productNo, "상품 번호는 필수입니다.");
        Assert.notNull(orderQuantity, "주문수량은 필수입니다.");
        if (1 > orderQuantity) throw new IllegalArgumentException("주문수량은 1개 이상이어야 합니다.");
        Assert.notNull(unitPrice, "단가는 필수입니다.");
        if (1 > unitPrice) throw new IllegalArgumentException("단가는 1원 이상이어야 합니다.");
    }

    public void assignOutbound(final Outbound outbound) {
        this.outbound = outbound;
    }

    public void decreaseQuantity(final Long quantity) {
        validateDecreaseQuantity(quantity);
        this.quantity -= quantity;
    }

    private void validateDecreaseQuantity(final Long quantity) {
        Assert.notNull(quantity, "감소할 수량은 필수입니다.");
        if (1 > quantity) throw new IllegalArgumentException("감소할 수량은 1개 이상이어야 합니다.");
        if (quantity > this.quantity) throw new IllegalArgumentException("감소할 수량은 출고 수량보다 클 수 없습니다.");
        if (!pickings.isEmpty()) {
            throw new IllegalArgumentException("집품이 할당된 상품의 수량을 변경할 수 없습니다.");
        }
    }

    public boolean isZeroQuantity() {
        return 0 == quantity;
    }

    public void allocatePicking(final List<Inventory> inventories) {
        Assert.notNull(inventories, "집품을 할당하려는 재고 정보가 없습니다.");
        final List<Inventory> pickingInventories = makeEfficientInventoriesForPicking(
                productNo, quantity, inventories);

        final List<Picking> pickings = createPickings(pickingInventories);

        allocatePickings(pickings);
    }

    private List<Inventory> makeEfficientInventoriesForPicking(final Long productNo, final Long quantity, final List<Inventory> inventories) {
        validate(productNo, quantity);
        final List<Inventory> filteredInventories = filterAvailableInventories(productNo, inventories);

        checkInventoryAvailability(quantity, filteredInventories);
        final List<Inventory> sortedEfficientInventories = sortEfficientInventoriesForPicking(filteredInventories);

        return sortedEfficientInventories;
    }

    private void validate(final Long productNo, final Long quantity) {
        Assert.notNull(productNo, "상품 번호가 없습니다.");
        Assert.notNull(quantity, "출고 수량이 없습니다.");
        if (0 >= quantity) throw new IllegalArgumentException("출고 수량은 0보다 커야 합니다.");
    }

    private List<Inventory> filterAvailableInventories(final Long productNo, final List<Inventory> inventories) {
        return inventories.stream()
                .filter(i -> i.getProductNo().equals(productNo))
                .filter(Inventory::hasInventory)
                .filter(Inventory::isFresh)
                .toList();
    }

    private void checkInventoryAvailability(
            final Long orderQuantity, final List<Inventory> inventories) {
        final long totalQuantity = inventories.stream()
                .mapToLong(Inventory::getQuantity)
                .sum();
        if (totalQuantity < orderQuantity) {
            throw new IllegalArgumentException(
                    "재고가 부족합니다. 재고 수량:%d, 주문 수량:%d"
                            .formatted(totalQuantity, orderQuantity));
        }
    }

    private List<Inventory> sortEfficientInventoriesForPicking(
            final List<Inventory> inventories) {
        return inventories.stream()
                .sorted(Comparator.comparing(Inventory::getExpiringAt)
                        .thenComparing(Inventory::getQuantity, Comparator.reverseOrder())
                        .thenComparing(Inventory::getLocationBarcode)
                )
                .toList();
    }

    List<Picking> createPickings(final List<Inventory> inventories) {
        final Inventory firstInventory = inventories.get(0);
        if (quantity <= firstInventory.getQuantity()) {
            return List.of(new Picking(firstInventory, quantity));
        }

        Long remainingQuantity = quantity;
        final List<Picking> pickings = new ArrayList<>();
        for (final Inventory inventory : inventories) {
            if (isAllocationComplete(remainingQuantity)) {
                return pickings;
            }
            final Long quantityToAllocate = Math.min(
                    inventory.getQuantity(),
                    remainingQuantity);
            remainingQuantity -= quantityToAllocate;
            pickings.add(new Picking(inventory, quantityToAllocate));
        }
        return pickings;
    }

    private boolean isAllocationComplete(final Long remainingQuantity) {
        return 0 == remainingQuantity;
    }

    private void allocatePickings(final List<Picking> pickings) {
        this.pickings.clear();
        this.pickings.addAll(pickings);
        pickings.forEach(picking -> picking.assignOutboundProduct(this));
    }

    boolean hasPickings() {
        return !pickings.isEmpty();
    }

    public void scanToPick(final Inventory inventory) {
        validateScanToPick(inventory);
        final Picking picking = getPicking(inventory);
        picking.scanToPick();
        if (isPicked()) {
            pickedAt = LocalDateTime.now();
        }
    }

    private void validateScanToPick(final Inventory inventory) {
        Assert.notNull(inventory, "스캔할 로케이션 정보가 없습니다.");
        if (!hasPickings()) {
            throw new IllegalArgumentException("할당된 집품이 없습니다.");
        }
        if (null != pickedAt) {
            throw new IllegalArgumentException("이미 피킹이 완료된 집품입니다.");
        }

    }

    private Picking getPicking(final Inventory inventory) {
        return pickings.stream()
                .filter(picking -> picking.getInventory().equals(inventory))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("스캔한 재고는 집품목록에 할당되어 있지 않습니다."));
    }

    public boolean isPicked() {
        return pickings.stream()
                .allMatch(Picking::isPicked);
    }
}
