package com.example.kwms.location.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.util.Assert;

import java.util.List;

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
    private Long fromWarehouseNo;
    @Column(name = "to_warehouse_no", nullable = false)
    @Comment("도착 창고 번호")
    private Long toWarehouseNo;
    @Column(name = "barcode", nullable = false, unique = true)
    @Comment("바코드")
    private String barcode;
    @OneToMany(mappedBy = "warehouseTransfer", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<WarehouseTransferProduct> products;

    public WarehouseTransfer(
            final Long fromWarehouseNo,
            final Long toWarehouseNo,
            final String barcode,
            final List<WarehouseTransferProduct> products) {
        Assert.notNull(fromWarehouseNo, "출발 창고 번호는 필수입니다.");
        Assert.notNull(toWarehouseNo, "도착 창고 번호는 필수입니다.");
        Assert.notNull(barcode, "바코드는 필수입니다.");
        Assert.notEmpty(products, "상품은 필수입니다.");
        this.fromWarehouseNo = fromWarehouseNo;
        this.toWarehouseNo = toWarehouseNo;
        this.barcode = barcode;
        this.products = products;
        products.forEach(product -> product.assignWarehouseTransfer(this));
    }
}
