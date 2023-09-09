package com.example.kwms.inbound.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.util.Assert;

@Entity
@Table(name = "inbound_product")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class InboundProduct {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inbound_product_no")
    @Comment("입고 상품 번호")
    private Long inboundProductNo;
    @Column(name = "product_no", nullable = false)
    @Comment("상품 번호")
    private Long productNo;
    @Column(name = "request_quantity", nullable = false)
    @Comment("상품 입고 요청 수량")
    private Long requestQuantity;
    @Column(name = "unit_price", nullable = false)
    @Comment("상품 입고 요청 단가")
    private Long unitPrice;
    @Column(name = "description")
    @Comment("상품 입고 설명")
    @Getter
    private String description;
    @ManyToOne
    @JoinColumn(name = "inbound_no", nullable = false)
    @Comment("입고 번호")
    private Inbound inbound;
    /**
     * 발주요청 상태에서 생성되었는지 입고 이후에 추가로 입고된 상품인지를 구분하기 위한 필드
     */
    @Column(name = "is_added", nullable = false)
    @Comment("추가 여부")
    private boolean isAdded;

    public InboundProduct(
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

    void assignInbound(final Inbound inbound) {
        Assert.notNull(inbound, "입고는 필수입니다.");
        this.inbound = inbound;
    }

    void added() {
        isAdded = true;
    }

    void update(
            final Long productNo,
            final Long requestQuantity,
            final Long unitPrice,
            final String description) {
        validateUpdate(productNo, requestQuantity, unitPrice);
        this.productNo = productNo;
        this.requestQuantity = requestQuantity;
        this.unitPrice = unitPrice;
        this.description = description;
    }

    private void validateUpdate(
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

    boolean equalsId(final Long inboundProductNo) {
        return this.inboundProductNo.equals(inboundProductNo);
    }
}
