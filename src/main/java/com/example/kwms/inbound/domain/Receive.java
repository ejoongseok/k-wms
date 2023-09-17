package com.example.kwms.inbound.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
import java.util.List;

@Entity
@Getter
@Table(name = "receive")
@NoArgsConstructor
public class Receive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "receive_no")
    @Comment("입고 번호")
    private Long receiveNo;
    @Column(name = "name", nullable = false)
    @Comment("입고명")
    private String name;
    @OneToMany(mappedBy = "receive", cascade = CascadeType.ALL)
    private List<ReceiveProduct> receiveProducts;
    @ManyToOne
    @JoinColumn(name = "purchase_order_no", nullable = false)
    @Comment("발주 번호")
    private PurchaseOrder purchaseOrder;
    @Column(name = "created_at", nullable = false)
    @Comment("생성 일시")
    private final LocalDateTime createdAt = LocalDateTime.now();

    public Receive(
            final String name,
            final List<ReceiveProduct> receiveProducts) {
        Assert.hasText(name, "입고명은 필수입니다.");
        Assert.notEmpty(receiveProducts, "입고 상품은 필수입니다.");
        this.name = name;
        this.receiveProducts = receiveProducts;
        receiveProducts.forEach(receiveProduct -> receiveProduct.assignReceive(this));
    }

    public void assignPurchaseOrder(final PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }
}
