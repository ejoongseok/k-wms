package com.example.kwms.outbound.domain;

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
    @Getter
    @OneToMany(mappedBy = "bulkOutbound", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Outbound> outbounds = new ArrayList<>();
    @Getter
    @Column(name = "picked_at")
    @Comment("피킹 완료 일시")
    private LocalDateTime pickedAt;
    @Column(name = "inspected_at")
    @Comment("검수 완료 일시")
    @Getter
    private LocalDateTime inspectedAt;

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
        if (!outbounds.stream()
                .allMatch(o -> null != o.getRecommendedPackagingMaterial())) {
            throw new IllegalStateException("추천 포장재가 없는 출고건이 존재합니다.");
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

    public void pickedOptimalFirstOutbound(final List<DecreaseTarget> targets) {
        final Outbound outbound = getOptimalFirstOutbound();
        validate(targets, outbound);

        outbound.bulkPicked();

        if (outbounds.stream().allMatch(Outbound::isPicked)) {
            pickedAt = LocalDateTime.now();
        }
    }

    private void validate(final List<DecreaseTarget> targets, final Outbound outbound) {
        Assert.notNull(targets, "피킹 정보는 필수입니다.");
        Assert.notNull(outbound, "출고 정보는 필수입니다.");
        if (null != pickedAt) {
            throw new IllegalStateException("이미 피킹이 완료된 출고건입니다.");
        }
        for (final OutboundProduct outboundProduct : outbound.getOutboundProducts()) {
            final Long pickedQuantity = calculatePickedQuantity(targets, outboundProduct.getProductNo());
            if (outboundProduct.getQuantity() != pickedQuantity) {
                throw new IllegalArgumentException("집품수량과 출고 수량이 일치하지 않습니다.");
            }
        }
    }

    private Long calculatePickedQuantity(final List<DecreaseTarget> targets, final Long productNo) {
        return targets.stream()
                .filter(t -> t.getTarget().getProductNo().equals(productNo))
                .mapToLong(DecreaseTarget::getQuantity)
                .sum();
    }

    private Outbound getOptimalFirstOutbound() {
        return outbounds.stream()
                .filter(o -> !o.isPicked())
                .min(Comparator.comparing(Outbound::getIsPriorityDelivery)
                        .thenComparing(Outbound::getDesiredDeliveryAt)
                        .thenComparing(Outbound::getOutboundNo))
                .orElseThrow(() -> new IllegalStateException("출고 대기 중인 출고건이 없습니다."));
    }

    public void inspected() {
        validateInspected();
        outbounds.forEach(Outbound::inspected);
        inspectedAt = LocalDateTime.now();
    }

    private void validateInspected() {
        if (null != inspectedAt) {
            throw new IllegalStateException("이미 검수가 완료된 출고건입니다.");
        }
        if (null == pickedAt) {
            throw new IllegalStateException("피킹이 완료되지 않았습니다.");
        }
        if (outbounds.stream().anyMatch(o -> !o.isPicked())) {
            throw new IllegalStateException("피킹이 완료되지 않은 출고건이 존재합니다.");
        }
    }

    public void pop(final Long quantity) {
        validatePop(quantity);
        final List<Outbound> sortedForPopOptimalOutbounds = sortForPopOptimalOutbounds();
        for (int i = 0; i < quantity; i++) {
            final Outbound outbound = sortedForPopOptimalOutbounds.get(i);
            outbound.cancelled("대량 출고 제외");
            outbound.unassignBulkOutbound();
            outbounds.remove(outbound);
        }
    }

    private void validatePop(final Long quantity) {
        Assert.notNull(quantity, "수량은 필수입니다.");
        if (1 > quantity) {
            throw new IllegalArgumentException("수량은 1개 이상이어야 합니다.");
        }
        if (outbounds.size() < quantity) {
            throw new IllegalArgumentException("출고건 수보다 많은 수량을 제외할 수 없습니다.");
        }
    }

    private List<Outbound> sortForPopOptimalOutbounds() {
        return outbounds.stream()
                .filter(o -> !o.isPicked())
                .sorted(Comparator.comparing(Outbound::getIsPriorityDelivery).reversed()
                        .thenComparing(Outbound::getDesiredDeliveryAt).reversed()
                        .thenComparing(Outbound::getOutboundNo).reversed())
                .collect(Collectors.toList());
    }
}
