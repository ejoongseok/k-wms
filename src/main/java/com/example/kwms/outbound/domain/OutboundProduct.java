package com.example.kwms.outbound.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.util.Assert;

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
    private Long productNo;
    @Column(name = "quantity", nullable = false)
    @Comment("출고 수량")
    @Getter
    private Long quantity;
    @Column(name = "unit_price", nullable = false)
    @Comment("단가")
    private Long unitPrice;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "outbound_no", nullable = false)
    @Comment("출고 번호")
    private Outbound outbound;

    public OutboundProduct(
            final Long productNo,
            final Long quantity,
            final Long unitPrice) {
        validateConstructor(productNo, quantity, unitPrice);
        this.productNo = productNo;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
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
}
