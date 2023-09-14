package com.example.kwms.outbound.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "bulk_outbound")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class BulkOutbound {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bulk_outbound_no")
    @Comment("일괄 출고 번호")
    private Long bulkOutboundNo;
    @OneToMany(mappedBy = "bulkOutbound")
    private List<Outbound> outbounds = new ArrayList<>();
    @Column(name = "picking_completed_quantity", nullable = false)
    @Comment("집품 완료 수량")
    private final Long pickingCompletedQuantity = 0L;

    public BulkOutbound(final List<Outbound> outbounds) {
        Assert.notEmpty(outbounds, "출고가 존재하지 않습니다.");
        this.outbounds = outbounds;
    }

    public void validateAndAssign() {
        validateBulkedOutbounds();
        outbounds.forEach(outbound -> outbound.assignBulkOutbound(this));
    }

    private void validateBulkedOutbounds() {
        Assert.notEmpty(outbounds, "출고가 존재하지 않습니다.");
        final boolean hasAssignedBulkOutbound = outbounds.stream().anyMatch(outbound -> null != outbound.getBulkOutbound());
        if (hasAssignedBulkOutbound) {
            throw new IllegalStateException("이미 대량출고에 할당된 출고건이 존재합니다.");
        }
        final Set<String> uniqueOutboundProducts = outbounds.stream()
                .map(this::formatOutboundProducts)
                .collect(Collectors.toSet());

        if (1 < uniqueOutboundProducts.size()) {
            throw new IllegalStateException("동일한 출고건이 아닙니다.");
        }

        // 출고 목록이 전부 출고 대기 중인 목록인지 확인
        final Set<Outbound> unreadyOutbounds = outbounds.stream()
                .filter(outbound -> !outbound.isReady())
                .collect(Collectors.toUnmodifiableSet());

        if (!unreadyOutbounds.isEmpty()) {
            throw new IllegalStateException("출고 대기 중인 출고건이 아닙니다.");
        }
    }

    private String formatOutboundProducts(final Outbound outbound) {
        final List<OutboundProduct> sortedOutboundProducts = outbound.getOutboundProducts()
                .stream()
                .sorted(Comparator.comparing(OutboundProduct::getProductNo))
                .toList();
        return sortedOutboundProducts.stream()
                .map(outboundProduct -> "%d:%d".formatted(outboundProduct.getProductNo(), outboundProduct.getQuantity()))
                .collect(Collectors.joining("/"));
    }
}
