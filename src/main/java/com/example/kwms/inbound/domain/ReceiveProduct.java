package com.example.kwms.inbound.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

import java.time.LocalDateTime;

@Entity
@Table(name = "receive_product")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Getter
public class ReceiveProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "receive_product_no")
    @Comment("입고 상품 번호")
    private Long receiveProductNo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receive_no", nullable = false)
    @Comment("발주 번호")
    private Receive receive;
    @Column(name = "inspected_at")
    @Comment("검수 일시")
    private LocalDateTime inspectedAt;
    @Column(name = "inspection_comment")
    @Comment("검수 코멘트")
    private String inspectionComment;
    @Column(name = "acceptable_quantity")
    @Comment("정상 수량")
    private Long acceptableQuantity;
    @Column(name = "rejected_quantity")
    @Comment("불량 수량")
    private Long rejectedQuantity;
    @Getter
    @Column(name = "product_no", nullable = false)
    @Comment("상품 번호")
    private Long productNo;

    public ReceiveProduct(
            final Long productNo,
            final Long acceptableQuantity,
            final Long rejectedQuantity,
            final String inspectionComment,
            final LocalDateTime inspectedAt) {
        validateConstructor(
                productNo,
                acceptableQuantity,
                rejectedQuantity,
                inspectionComment,
                inspectedAt);
        this.inspectedAt = inspectedAt;
        this.inspectionComment = inspectionComment;
        this.acceptableQuantity = acceptableQuantity;
        this.rejectedQuantity = rejectedQuantity;
        this.productNo = productNo;
    }

    private void validateConstructor(
            final Long productNo,
            final Long acceptableQuantity,
            final Long rejectedQuantity,
            final String inspectionComment,
            final LocalDateTime inspectedAt) {
        Assert.notNull(productNo, "상품 번호는 필수입니다.");
        Assert.notNull(acceptableQuantity, "정상 수량은 필수입니다.");
        if (0 > acceptableQuantity)
            throw new IllegalArgumentException("정상 수량은 0개 이상이어야 합니다.");
        Assert.notNull(rejectedQuantity, "불량 수량은 필수입니다.");
        if (0 > rejectedQuantity)
            throw new IllegalArgumentException("불량 수량은 0개 이상이어야 합니다.");
        Assert.notNull(inspectionComment, "검수 코멘트는 필수입니다.");
        Assert.notNull(inspectedAt, "검수 일시는 필수입니다.");
    }

    void assignReceive(final Receive receive) {
        Assert.notNull(receive, "입고는 필수입니다.");
        this.receive = receive;
    }

    public Long totalQuantity() {
        return acceptableQuantity + rejectedQuantity;
    }
}
