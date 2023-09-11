package com.example.kwms.location.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.util.Assert;

@Entity
@Table(name = "warehouse_transfer_product")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class WarehouseTransferProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "warehouse_transfer_product_no")
    @Comment("창고간 재고이동 상품 번호")
    private Long warehouseTransferProductNo;
    @Column(name = "product_no", nullable = false)
    @Comment("상품 번호")
    private Long productNo;
    @Column(name = "quantity", nullable = false)
    @Comment("수량")
    private Long quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_transfer_no", nullable = false)
    private WarehouseTransfer warehouseTransfer;

    public WarehouseTransferProduct(final Long productNo, final Long quantity) {
        Assert.notNull(productNo, "상품 번호는 필수입니다.");
        Assert.notNull(quantity, "수량은 필수입니다.");
        if (0 >= quantity) {
            throw new IllegalArgumentException("수량은 0보다 커야합니다.");
        }
        this.productNo = productNo;
        this.quantity = quantity;
    }

    void assignWarehouseTransfer(final WarehouseTransfer warehouseTransfer) {
        this.warehouseTransfer = warehouseTransfer;
    }
}
