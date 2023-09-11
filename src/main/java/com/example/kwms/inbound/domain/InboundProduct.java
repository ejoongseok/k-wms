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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    @Getter
    @OneToMany(mappedBy = "inboundProduct", orphanRemoval = true, cascade = CascadeType.ALL)
    private final List<LPN> lpns = new ArrayList<>();
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inbound_no", nullable = false)
    @Comment("입고 번호")
    private Inbound inbound;
    /**
     * 발주요청 상태에서 생성되었는지 입고 이후에 추가로 입고된 상품인지를 구분하기 위한 필드
     */
    @Column(name = "is_added", nullable = false)
    @Comment("추가 여부")
    private boolean isAdded;
    @Column(name = "inspected_at")
    @Comment("검수 일시")
    @Getter
    private LocalDateTime inspectedAt;
    @Column(name = "arrived_at")
    @Comment("입고 일시")
    private LocalDateTime arrivedAt;
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

    @VisibleForTesting
    public InboundProduct(
            final Long inboundProductNo,
            final Long productNo,
            final Long requestQuantity,
            final Long unitPrice,
            final String description,
            final boolean isAdded,
            final LocalDateTime inspectedAt,
            final LocalDateTime arrivedAt,
            final String inspectionComment,
            final Long acceptableQuantity,
            final Long rejectedQuantity) {
        this.inboundProductNo = inboundProductNo;
        this.productNo = productNo;
        this.requestQuantity = requestQuantity;
        this.unitPrice = unitPrice;
        this.description = description;
        this.isAdded = isAdded;
        this.inspectedAt = inspectedAt;
        this.arrivedAt = arrivedAt;
        this.inspectionComment = inspectionComment;
        this.acceptableQuantity = acceptableQuantity;
        this.rejectedQuantity = rejectedQuantity;
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
        if (null != inspectedAt)
            throw new IllegalStateException("이미 검수가 완료된 상품은 변경할 수 없습니다.");
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

    void registerInspectionResult(
            final LocalDateTime inspectedAt,
            final LocalDateTime arrivedAt,
            final String inspectionComment,
            final Long acceptableQuantity,
            final Long rejectedQuantity) {
        validateRegisterInspectionResult(
                inspectedAt,
                arrivedAt,
                inspectionComment,
                acceptableQuantity,
                rejectedQuantity);
        this.inspectedAt = inspectedAt;
        this.arrivedAt = arrivedAt;
        this.inspectionComment = inspectionComment;
        this.acceptableQuantity = acceptableQuantity;
        this.rejectedQuantity = rejectedQuantity;
    }

    private void validateRegisterInspectionResult(
            final LocalDateTime inspectedAt,
            final LocalDateTime arrivedAt,
            final String inspectionComment,
            final Long acceptableQuantity,
            final Long rejectedQuantity) {
        if (null != this.inspectedAt)
            throw new IllegalStateException("이미 검수가 완료된 상품입니다.");
        Assert.notNull(inspectedAt, "검수 일시는 필수입니다.");
        Assert.notNull(arrivedAt, "입고 일시는 필수입니다.");
        Assert.notNull(inspectionComment, "검수 코멘트는 필수입니다.");
        Assert.notNull(acceptableQuantity, "정상 수량은 필수입니다.");
        if (0 > acceptableQuantity)
            throw new IllegalArgumentException("정상 수량은 0개 이상이어야 합니다.");
        Assert.notNull(rejectedQuantity, "불량 수량은 필수입니다.");
        if (0 > rejectedQuantity)
            throw new IllegalArgumentException("불량 수량은 0개 이상이어야 합니다.");
    }

    void assignLPN(
            final String lpnBarcode,
            final LocalDateTime expiringAt) {
        validateAssignLPN(lpnBarcode, expiringAt);
        final LPN lpn = new LPN(lpnBarcode, expiringAt);
        lpns.add(lpn);
        lpn.assignInboundProduct(this);
    }

    private void validateAssignLPN(
            final String lpnBarcode,
            final LocalDateTime expiringAt) {
        if (null == inspectedAt) {
            throw new IllegalStateException("검수가 완료되지 않은 상품은 LPN을 등록할 수 없습니다.");
        }
        Assert.hasText(lpnBarcode, "LPN 바코드는 필수입니다.");
        Assert.notNull(expiringAt, "유통기한은 필수입니다.");
        lpns.stream()
                .filter(lpn -> lpn.equalsBarcode(lpnBarcode))
                .findAny()
                .ifPresent(lpn -> {
                    throw new IllegalArgumentException("이미 등록된 LPN 바코드입니다.");
                });
    }

}
