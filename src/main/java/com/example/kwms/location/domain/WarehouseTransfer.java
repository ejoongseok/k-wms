package com.example.kwms.location.domain;

import com.example.kwms.common.NotFoundException;
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
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.kwms.location.domain.StorageType.PALLET;
import static com.example.kwms.location.domain.StorageType.TOTE;

/**
 * 창고간 재고이동
 */
@Entity
@Table(name = "warehouse_transfer")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class WarehouseTransfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "warehouse_transfer_no")
    @Comment("창고간 재고이동 번호")
    private Long warehouseTransferNo;
    @Column(name = "from_warehouse_no", nullable = false)
    @Comment("출발 창고 번호")
    @Getter
    private Long fromWarehouseNo;
    @Column(name = "to_warehouse_no", nullable = false)
    @Comment("도착 창고 번호")
    private Long toWarehouseNo;
    @Column(name = "barcode", nullable = false, unique = true)
    @Comment("바코드")
    @Getter
    private String barcode;
    @OneToMany(mappedBy = "warehouseTransfer", orphanRemoval = true, cascade = CascadeType.ALL)
    @Getter
    private List<WarehouseTransferProduct> products;
    @OneToMany(mappedBy = "warehouseTransfer", orphanRemoval = true, cascade = CascadeType.ALL)
    @Getter
    private final List<WarehouseTransferLocation> warehouseTransferLocations = new ArrayList<>();
    @Getter
    @Column(name = "shipped_at")
    @Comment("출하 일시")
    private LocalDateTime shippedAt;

    public WarehouseTransfer(
            final Long fromWarehouseNo,
            final Long toWarehouseNo,
            final String barcode,
            final List<WarehouseTransferProduct> products) {
        Assert.notNull(fromWarehouseNo, "출발 창고 번호는 필수입니다.");
        Assert.notNull(toWarehouseNo, "도착 창고 번호는 필수입니다.");
        Assert.notNull(barcode, "바코드는 필수입니다.");
        Assert.notEmpty(products, "상품은 필수입니다.");
        final Set<Long> productNos = products.stream()
                .map(WarehouseTransferProduct::getProductNo)
                .collect(Collectors.toSet());
        if (productNos.size() != products.size()) {
            throw new IllegalArgumentException("상품 번호는 중복될 수 없습니다.");
        }
        this.fromWarehouseNo = fromWarehouseNo;
        this.toWarehouseNo = toWarehouseNo;
        this.barcode = barcode;
        this.products = products;
        products.forEach(product -> product.assignWarehouseTransfer(this));
    }

    //TODO 대기 상태일때만 변경 가능
    public void update(
            final Long fromWarehouseNo,
            final Long toWarehouseNo,
            final String barcode) {
        Assert.notNull(fromWarehouseNo, "출발 창고 번호는 필수입니다.");
        Assert.notNull(toWarehouseNo, "도착 창고 번호는 필수입니다.");
        Assert.hasText(barcode, "바코드는 필수입니다.");
        this.fromWarehouseNo = fromWarehouseNo;
        this.toWarehouseNo = toWarehouseNo;
        this.barcode = barcode;
    }

    public void updateProduct(
            final Long warehouseTransferProductNo,
            final Long productNo,
            final Long quantity) {
        validateUpdateProduct(warehouseTransferProductNo, productNo, quantity);

        final WarehouseTransferProduct warehouseTransferProduct = getWarehouseTransferProduct(warehouseTransferProductNo);

        warehouseTransferProduct.update(productNo, quantity);
    }

    private void validateUpdateProduct(final Long warehouseTransferProductNo, final Long productNo, final Long quantity) {
        Assert.notNull(warehouseTransferProductNo, "창고간 재고이동 상품 번호는 필수입니다.");
        Assert.notNull(productNo, "상품 번호는 필수입니다.");
        Assert.notNull(quantity, "수량은 필수입니다.");
        if (1 > quantity) {
            throw new IllegalArgumentException("수량은 1개 이상이어야 합니다.");
        }
    }

    private WarehouseTransferProduct getWarehouseTransferProduct(final Long warehouseTransferProductNo) {
        return products.stream()
                .filter(product -> warehouseTransferProductNo.equals(product.getWarehouseTransferProductNo()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("창고간 재고이동 상품이 존재하지 않습니다. 상품 번호: %d".formatted(warehouseTransferProductNo)));
    }

    public void addLocation(final Location location) {
        validateAddLocation(location);
        warehouseTransferLocations.add(new WarehouseTransferLocation(this, location));
    }

    private void validateAddLocation(final Location location) {
        Assert.notNull(location, "로케이션은 필수입니다.");
        if (TOTE != location.getStorageType() && PALLET != location.getStorageType()) {
            throw new IllegalArgumentException(
                    ("재고이동가능한 로케이션은 로케이션의 용도는 토트 또는 팔렛이어야 합니다. " +
                            "로케이션 용도: %s").formatted(location.getStorageType()));
        }
        if (!fromWarehouseNo.equals(location.getWarehouseNo())) {
            throw new IllegalArgumentException(
                    ("출발 창고 번호와 로케이션의 창고 번호가 일치하지 않습니다. " +
                            "출발 창고 번호: %d, 로케이션의 창고 번호: %d").formatted(fromWarehouseNo, location.getWarehouseNo()));
        }

        // 로케이션의 모든 상품 번호를 가져온다.
        final Set<Long> allProductsInLocation = location.getAllProductNos();
        // 2. 재고이동할 상품 리스트에서 로케이션의 모든 상품 리스트를 제거합니다.
        for (final WarehouseTransferProduct product : products) {
            allProductsInLocation.remove(product.getProductNo());
        }

        // 3. allProductsInLocation에는 재고이동 대상이 아닌 로케이션에 남아있는 상품들이 있는지 확인.
        if (!allProductsInLocation.isEmpty()) {
            throw new IllegalArgumentException(
                    ("재고이동 대상이 아닌 상품이 로케이션에 존재합니다. " +
                            "로케이션 바코드: %s, 상품 번호: %d")
                            .formatted(location.getLocationBarcode(), allProductsInLocation.iterator().next()));
        }
    }

    public void shipment() {
        validateShipment();
        shippedAt = LocalDateTime.now();
    }

    private void validateShipment() {
        if (null != shippedAt) {
            throw new IllegalStateException("이미 출하된 재고이동입니다.");
        }
        if (warehouseTransferLocations.isEmpty()) {
            throw new IllegalStateException("출하할 로케이션이 존재하지 않습니다.");
        }

        final Set<Long> productNos = warehouseTransferLocations.stream()
                .map(WarehouseTransferLocation::getLocation)
                .flatMap(location -> location.getAllProductNos().stream())
                .collect(Collectors.toSet());

        //TODO 이거는 출하 확정할때 하면 될듯하다.
        for (final WarehouseTransferProduct product : products) {
            if (!productNos.contains(product.getProductNo())) {
                throw new IllegalArgumentException(
                        ("출하할 상품이 로케이션에 존재하지 않습니다. " +
                                "상품 번호: %d").formatted(product.getProductNo()));
            }
        }
    }

}
