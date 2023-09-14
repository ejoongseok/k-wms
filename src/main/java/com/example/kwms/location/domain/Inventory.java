package com.example.kwms.location.domain;

import com.example.kwms.inbound.domain.LPN;
import com.google.common.annotations.VisibleForTesting;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventory")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "inventoryNo", callSuper = false)
public class Inventory {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_no")
    @Comment("재고 번호")
    private Long inventoryNo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_no", nullable = false)
    @Comment("로케이션 번호")
    private Location location;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lpn_no", nullable = false)
    @Comment("LPN 번호")
    @Getter
    private LPN lpn;
    @Getter
    @Column(name = "quantity", nullable = false)
    @Comment("재고 수량")
    private Long quantity;
    @Getter
    @Column(name = "product_no", nullable = false)
    @Comment("상품 번호")
    private Long productNo;
    @Column(name = "warehouse_no", nullable = false)
    @Comment("창고 번호")
    private Long warehouseNo;

    Inventory(final Location location, final LPN lpn) {
        Assert.notNull(location, "로케이션은 필수입니다.");
        Assert.notNull(lpn, "LPN은 필수입니다.");
        this.location = location;
        this.lpn = lpn;
        quantity = 1L;
        productNo = lpn.getProductNo();
        warehouseNo = location.getWarehouseNo();
    }

    Inventory(final Location location, final LPN lpn, final Long quantity) {
        Assert.notNull(location, "로케이션은 필수입니다.");
        Assert.notNull(lpn, "LPN은 필수입니다.");
        Assert.notNull(quantity, "수량은 필수입니다.");
        if (0 >= quantity) {
            throw new IllegalArgumentException("수량은 0보다 커야 합니다.");
        }

        this.location = location;
        this.lpn = lpn;
        this.quantity = quantity;
        productNo = lpn.getProductNo();
        warehouseNo = location.getWarehouseNo();
    }

    @VisibleForTesting
    Inventory(
            final Long inventoryNo,
            final Long quantity,
            final Long productNo,
            final Long warehouseNo,
            final LPN lpn,
            final Location location) {
        this.inventoryNo = inventoryNo;
        this.quantity = quantity;
        this.productNo = productNo;
        this.warehouseNo = warehouseNo;
        this.lpn = lpn;
        this.location = location;
    }

    public boolean equalsLPN(final LPN lpn) {
        return this.lpn.equals(lpn);
    }

    void increaseQuantity() {
        quantity++;
    }

    void increaseQuantity(final Long quantity) {
        if (0 > quantity) {
            throw new IllegalArgumentException("수량은 0보다 커야 합니다.");
        }
        this.quantity += quantity;
    }

    void adjustInventory(final Long quantity) {
        Assert.notNull(quantity, "수량은 필수입니다.");
        if (0 > quantity) {
            throw new IllegalArgumentException("수량은 0보다 커야 합니다.");
        }
        this.quantity = quantity;
    }

    void decreaseQuantity(final Long quantity) {
        if (0 > quantity) {
            throw new IllegalArgumentException("수량은 0보다 커야 합니다.");
        }
        if (quantity > this.quantity) {
            throw new IllegalArgumentException("감소시킬 수량은 재고 수량보다 작아야 합니다. " +
                    "현재 재고 수량: %d, 감소시킬 수량: %d".formatted(this.quantity, quantity));
        }
        this.quantity -= quantity;
    }

    public boolean hasAvailableQuantity() {
        return 0L < quantity;
    }

    public boolean hasInventory() {
        return 0L < quantity;
    }

    public boolean isFresh() {
        return lpn.isFresh();
    }

    public LocalDateTime getExpiringAt() {
        return lpn.getExpiringAt();
    }

    public String getLocationBarcode() {
        return location.getLocationBarcode();
    }

    public void decreaseInventory(final Long quantityRequiredForPick) {
        if (quantity < quantityRequiredForPick) {
            throw new IllegalArgumentException(
                    "차감하려는 재고 수량이 충분하지 않습니다. 재고 수량:%d, 차감 수량:%d"
                            .formatted(quantity, quantityRequiredForPick));
        }
        quantity -= quantityRequiredForPick;
    }

}
