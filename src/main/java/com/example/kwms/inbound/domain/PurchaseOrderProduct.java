package com.example.kwms.inbound.domain;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "purchase_order_product")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Getter
public class PurchaseOrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_order_product_no")
    @Comment("입고 상품 번호")
    private Long purchaseOrderProductNo;
    @Column(name = "request_quantity", nullable = false)
    @Comment("상품 입고 요청 수량")
    private Long requestQuantity;
    @Column(name = "unit_price", nullable = false)
    @Comment("상품 입고 요청 단가")
    private Long unitPrice;
    @Column(name = "description")
    @Comment("상품 입고 설명")
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_order_no", nullable = false)
    @Comment("입고 번호")
    private PurchaseOrder purchaseOrder;
    @Column(name = "product_no", nullable = false)
    @Comment("상품 번호")
    private Long productNo;
    @OneToMany(mappedBy = "purchaseOrderProduct", orphanRemoval = true, cascade = CascadeType.ALL)
    private final List<LPN> lpns = new ArrayList<>();

    public PurchaseOrderProduct(
            final Long productNo,
            final Long requestQuantity,
            final Long unitPrice,
            final String description) {
        validateConstructor(productNo, requestQuantity, unitPrice);

        this.productNo = productNo;
        this.requestQuantity = requestQuantity;
        this.unitPrice = unitPrice;
        this.description = description;
    }

    private void validateConstructor(
            final Long productNo,
            final Long requestQuantity,
            final Long unitPrice) {
        Assert.notNull(productNo, "상품 번호는 필수입니다.");
        Assert.notNull(requestQuantity, "상품 입고 요청 수량은 필수입니다.");
        if (1 > requestQuantity)
            throw new IllegalArgumentException("상품 입고 요청 수량은 1개 이상이어야 합니다.");
        Assert.notNull(unitPrice, "상품 입고 요청 단가는 필수입니다.");
        if (0 > unitPrice)
            throw new IllegalArgumentException("상품 입고 요청 단가는 0원 이상이어야 합니다.");
    }

    @VisibleForTesting
    public PurchaseOrderProduct(
            final Long purchaseOrderProductNo,
            final Long requestQuantity,
            final Long unitPrice,
            final String description,
            final Long productNo) {
        this.purchaseOrderProductNo = purchaseOrderProductNo;
        this.requestQuantity = requestQuantity;
        this.unitPrice = unitPrice;
        this.description = description;
        this.productNo = productNo;
    }

    void assignPurchaseOrder(final PurchaseOrder purchaseOrder) {
        Assert.notNull(purchaseOrder, "입고는 필수입니다.");
        this.purchaseOrder = purchaseOrder;
    }

    void addLPN(final LPN lpn) {
        validateAddLPN(lpn);
        lpns.add(lpn);
        lpn.assignInboundProduct(this);
    }

    private void validateAddLPN(final LPN newLPN) {
        Assert.notNull(newLPN, "LPN은 필수입니다.");
        lpns.stream()
                .filter(lpn -> lpn.equals(newLPN))
                .findAny()
                .ifPresent(lpn -> {
                    throw new AlreadyExistLPNException(lpn.getLpnBarcode());
                });
    }

    boolean isEqualsPurchaseOrderProductNo(final Long purchaseOrderProductNo) {
        return this.purchaseOrderProductNo.equals(purchaseOrderProductNo);
    }
}
