package com.example.kwms.inbound.domain;

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
@Table(name = "lpn")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "lpnBarcode")
public class LPN {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lpn_no")
    @Comment("LPN 번호")
    @Getter
    private Long lpnNo;

    @Getter
    @Column(name = "lpn_barcode", nullable = false, unique = true)
    @Comment("LPN 바코드")
    private String lpnBarcode;
    @Getter
    @Column(name = "expiring_at", nullable = false)
    @Comment("유통기한")
    private LocalDateTime expiringAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_order_product_no", nullable = false)
    @Comment("입고 상품 번호")
    private PurchaseOrderProduct purchaseOrderProduct;

    LPN(final String lpnBarcode, final LocalDateTime expiringAt) {
        Assert.hasText(lpnBarcode, "LPN 바코드는 필수입니다.");
        Assert.notNull(expiringAt, "유통기한은 필수입니다.");
        this.lpnBarcode = lpnBarcode;
        this.expiringAt = expiringAt;
    }

    @VisibleForTesting
    LPN(final String lpnBarcode, final LocalDateTime expiringAt, final PurchaseOrderProduct purchaseOrderProduct) {
        this.lpnBarcode = lpnBarcode;
        this.expiringAt = expiringAt;
        this.purchaseOrderProduct = purchaseOrderProduct;
    }

    void assignInboundProduct(final PurchaseOrderProduct purchaseOrderProduct) {
        this.purchaseOrderProduct = purchaseOrderProduct;
    }

    boolean equalsBarcode(final String lpnBarcode) {
        return this.lpnBarcode.equals(lpnBarcode);
    }

    public Long getProductNo() {
        return purchaseOrderProduct.getProductNo();
    }

    public boolean isFresh() {
        return expiringAt.isAfter(LocalDateTime.now());
    }

}
