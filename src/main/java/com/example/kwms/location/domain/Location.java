package com.example.kwms.location.domain;

import com.example.kwms.common.NotFoundException;
import com.example.kwms.inbound.domain.LPN;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "location")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "locationBarcode")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_no")
    @Comment("로케이션 번호")
    private Long locationNo;
    @Getter
    @Column(name = "location_barcode", nullable = false, unique = true)
    @Comment("로케이션 바코드")
    private String locationBarcode;
    @Enumerated(EnumType.STRING)
    @Column(name = "storage_type", nullable = false)
    @Comment("로케이션 유형")
    private StorageType storageType;
    @Enumerated(EnumType.STRING)
    @Column(name = "usage_purpose", nullable = false)
    @Comment("로케이션 용도")
    @Getter
    private UsagePurpose usagePurpose;
    @Getter
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Location> children = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_location_no", nullable = true)
    @Comment("부모 로케이션 ID")
    private Location parent;
    @Getter
    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Inventory> inventories = new ArrayList<>();
    @Column(name = "warehouse_no", nullable = false)
    @Comment("창고 번호")
    private Long warehouseNo;

    public Location(
            final Long warehouseNo,
            final String locationBarcode,
            final StorageType storageType,
            final UsagePurpose usagePurpose) {
        Assert.notNull(warehouseNo, "창고 번호는 필수입니다.");
        Assert.hasText(locationBarcode, "로케이션 바코드는 필수입니다.");
        Assert.notNull(storageType, "로케이션 유형은 필수입니다.");
        Assert.notNull(usagePurpose, "로케이션 용도는 필수입니다.");
        this.warehouseNo = warehouseNo;
        this.locationBarcode = locationBarcode;
        this.storageType = storageType;
        this.usagePurpose = usagePurpose;

    }

    public void appendLocation(final Location current) {
        validateAppendLocation(current);
        children.add(current);
        current.parent = this;
    }

    private void validateAppendLocation(final Location location) {
        Assert.notNull(location, "추가할 로케이션은 필수입니다.");
        if (equals(location)) {
            throw new IllegalArgumentException("자기 자신을 추가할 수 없습니다.");
        }

        final int currentSize = location.getSize();
        final int targetSize = getSize();
        if (currentSize >= targetSize) {
            throw new IllegalArgumentException(
                    ("크기가 같거나 더 큰 로케이션이 하위 로케이션으로 추가될 수 없습니다. " +
                            "현재 로케이션 바코드: %s 유형/사이즈: %s/%d, " +
                            "추가하려는 로케이션 바코드: %s 유형/사이즈: %s/%d")
                            .formatted(
                                    locationBarcode,
                                    storageType,
                                    getSize(),
                                    location.locationBarcode,
                                    location.storageType,
                                    location.getSize()
                            ));
        }
        if (contains(location, this)) {
            throw new IllegalArgumentException(
                    ("이미 상위 로케이션으로 추가된 로케이션입니다. " +
                            "로케이션 바코드: %s, 상위 로케이션 바코드: %s")
                            .formatted(location.locationBarcode, locationBarcode));
        }

        if (contains(this, location)) {
            throw new IllegalArgumentException(
                    ("이미 하위 로케이션으로 추가된 로케이션입니다. " +
                            "로케이션 바코드: %s, 하위 로케이션 바코드: %s")
                            .formatted(locationBarcode, location.locationBarcode));
        }
        if (!warehouseNo.equals(location.warehouseNo)) {
            throw new IllegalArgumentException("다른 창고의 로케이션은 추가할 수 없습니다.");
        }
    }

    private Long getWarehouseNo() {
        return warehouseNo;
    }

    private int getSize() {
        return storageType.getSize();
    }


    private boolean contains(final Location root, final Location target) {
        if (root.equals(target)) {
            return true;
        }

        for (final Location child : root.children) {
            if (contains(child, target)) {
                return true;
            }
        }

        return false;
    }

    public void addInventory(final LPN lpn) {
        Assert.notNull(lpn, "LPN은 필수입니다.");
        findInventory(lpn)
                .ifPresentOrElse(
                        Inventory::increaseQuantity,
                        () -> inventories.add(new Inventory(this, lpn)));
    }

    private Optional<Inventory> findInventory(final LPN lpn) {
        return inventories.stream()
                .filter(inventory -> inventory.equalsLPN(lpn))
                .findFirst();
    }

    public void addManualInventory(final LPN lpn, final Long quantity) {
        validateAddManualInventory(lpn, quantity);
        findInventory(lpn)
                .ifPresentOrElse(
                        inventory -> inventory.increaseQuantity(quantity),
                        () -> inventories.add(new Inventory(this, lpn, quantity)));
    }

    private void validateAddManualInventory(final LPN lpn, final Long quantity) {
        Assert.notNull(lpn, "LPN은 필수입니다.");
        Assert.notNull(quantity, "수량은 필수입니다.");
        if (0 >= quantity)
            throw new IllegalArgumentException("수량은 0개 이상이어야 합니다.");
    }

    public void adjustInventory(final LPN lpn, final Long quantity) {
        validateAdjustInventory(lpn, quantity);
        final Inventory inventory = getInventory(lpn);
        inventory.adjustInventory(quantity);
    }

    private Inventory getInventory(final LPN lpn) {
        return findInventory(lpn)
                .orElseThrow(() -> new NotFoundException(
                        "해당 LPN이 존재하지 않습니다. LPN 바코드: %s"
                                .formatted(lpn.getLpnBarcode())));
    }

    private void validateAdjustInventory(final LPN lpn, final Long quantity) {
        Assert.notNull(lpn, "LPN은 필수입니다.");
        Assert.notNull(quantity, "수량은 필수입니다.");
        if (0 > quantity)
            throw new IllegalArgumentException("수량은 0개 이상이어야 합니다.");
    }

    public void decreaseInventory(final LPN lpn, final Long quantity) {
        validateDecreaseInventory(lpn, quantity);
        final Inventory inventory = getInventory(lpn);
        inventory.decreaseQuantity(quantity);
    }

    private void validateDecreaseInventory(final LPN lpn, final Long quantity) {
        Assert.notNull(lpn, "LPN은 필수입니다.");
        Assert.notNull(quantity, "수량은 필수입니다.");
        if (0 > quantity)
            throw new IllegalArgumentException("수량은 0개 이상이어야 합니다.");
    }

    public void update(
            final String locationBarcode,
            final UsagePurpose usagePurpose) {
        validateUpdate(locationBarcode, usagePurpose);
        this.locationBarcode = locationBarcode;
        this.usagePurpose = usagePurpose;
    }

    private void validateUpdate(
            final String locationBarcode,
            final UsagePurpose usagePurpose) {
        Assert.hasText(locationBarcode, "로케이션 바코드는 필수입니다.");
        Assert.notNull(usagePurpose, "로케이션 용도는 필수입니다.");
    }
}
