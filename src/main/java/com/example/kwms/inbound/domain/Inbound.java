package com.example.kwms.inbound.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "inbound")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Inbound {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inbound_no")
    @Comment("입고 번호")
    private Long inboundNo;
    @Column(name = "title", nullable = false)
    @Comment("입고명")
    private String title;
    @Column(name = "estimated_arrival_at", nullable = false)
    @Comment("입고 예정일")
    private LocalDateTime estimatedArrivalAt;
    @Column(name = "order_requested_at", nullable = false)
    @Comment("주문 요청일")
    private LocalDateTime orderRequestedAt;
    @Column(name = "description")
    @Comment("입고 설명")
    private String description;
    @Getter
    @OneToMany(mappedBy = "inbound", orphanRemoval = true, cascade = CascadeType.ALL)
    private final List<InboundProduct> inboundProducts = new ArrayList<>();

    public Inbound(
            final String title,
            final LocalDateTime estimatedArrivalAt,
            final LocalDateTime orderRequestedAt,
            final String description) {
        Assert.hasText(title, "입고 제목은 필수입니다.");
        Assert.notNull(estimatedArrivalAt, "입고 예정일은 필수입니다.");
        Assert.notNull(orderRequestedAt, "주문 요청일은 필수입니다.");
        Assert.notNull(inboundProducts, "입고 상품은 필수입니다.");
        this.title = title;
        this.estimatedArrivalAt = estimatedArrivalAt;
        this.orderRequestedAt = orderRequestedAt;
        this.description = description;
    }

    public void assignProducts(final List<InboundProduct> products) {
        Assert.notEmpty(products, "입고 상품은 필수입니다.");
        for (final InboundProduct product : products) {
            product.assignInbound(this);
            inboundProducts.add(product);
        }
    }

    public void addProduct(final InboundProduct inboundProduct) {
        Assert.notNull(inboundProduct, "입고 상품은 필수입니다.");

        inboundProduct.added();
        inboundProduct.assignInbound(this);
        inboundProducts.add(inboundProduct);
    }

}
