package com.example.kwms.outbound.domain;

import com.example.kwms.location.domain.Location;
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
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "outbound")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Outbound {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "outbound_no")
    @Comment("출고 번호")
    private Long outboundNo;

    @Comment("주문 번호")
    @Column(name = "order_no")
    private Long orderNo;
    @OneToMany(mappedBy = "outbound", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<OutboundProduct> outboundProducts = new ArrayList<>();
    @Column(name = "is_priority_delivery", nullable = false)
    @Comment("우선 출고 여부")
    private Boolean isPriorityDelivery;
    @Column(name = "desired_delivery_at", nullable = false)
    @Comment("희망 출고일")
    private LocalDate desiredDeliveryAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "packaging_material_no")
    private PackagingMaterial recommendedPackagingMaterial;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "picking_tote_no")
    @Comment("집품할 토트 바구니")
    private Location pickingTote;

    public Outbound(
            final Long orderNo,
            final List<OutboundProduct> outboundProducts,
            final Boolean isPriorityDelivery,
            final LocalDate desiredDeliveryAt,
            final PackagingMaterial packagingMaterial) {
        validateConstructor(
                orderNo,
                outboundProducts,
                isPriorityDelivery,
                desiredDeliveryAt);
        recommendedPackagingMaterial = packagingMaterial;
        this.orderNo = orderNo;
        this.isPriorityDelivery = isPriorityDelivery;
        this.desiredDeliveryAt = desiredDeliveryAt;
        this.outboundProducts = outboundProducts;
        outboundProducts.forEach(outboundProduct -> outboundProduct.assignOutbound(this));
    }

    private void validateConstructor(
            final Long orderNo,
            final List<OutboundProduct> outboundProducts,
            final Boolean isPriorityDelivery,
            final LocalDate desiredDeliveryAt) {
        Assert.notNull(orderNo, "주문번호는 필수입니다.");
        Assert.notEmpty(outboundProducts, "출고상품은 필수입니다.");
        Assert.notNull(isPriorityDelivery, "우선출고여부는 필수입니다.");
        Assert.notNull(desiredDeliveryAt, "희망출고일은 필수입니다.");
    }
}