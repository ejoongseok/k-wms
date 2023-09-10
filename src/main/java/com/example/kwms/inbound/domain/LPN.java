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

import java.time.LocalDateTime;

@Entity
@Table(name = "lpn")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class LPN {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lpn_no")
    @Comment("LPN 번호")
    private Long lpnNo;

    @Getter
    @Column(name = "lpn_barcode", nullable = false, unique = true)
    @Comment("LPN 바코드")
    private String lpnBarcode;
    @Column(name = "expiring_at", nullable = false)
    @Comment("유통기한")
    private LocalDateTime expiringAt;
    @ManyToOne
    @JoinColumn(name = "inbound_product_no", nullable = false)
    @Comment("입고 상품 번호")
    private InboundProduct inboundProduct;

    LPN(final String lpnBarcode, final LocalDateTime expiringAt) {
        Assert.hasText(lpnBarcode, "LPN 바코드는 필수입니다.");
        Assert.notNull(expiringAt, "유통기한은 필수입니다.");
        this.lpnBarcode = lpnBarcode;
        this.expiringAt = expiringAt;
    }

    void assignInboundProduct(final InboundProduct inboundProduct) {
        this.inboundProduct = inboundProduct;
    }

    boolean equalsBarcode(final String lpnBarcode) {
        return this.lpnBarcode.equals(lpnBarcode);
    }
}