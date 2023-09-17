package com.example.kwms.inbound.domain;

import com.example.kwms.common.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
    default PurchaseOrder getBy(final Long inboundNo) {
        return findById(inboundNo)
                .orElseThrow(() -> new NotFoundException(
                        "존재하지 않는 발주 번호입니다. 발주번호: %s".formatted(inboundNo)));
    }
}
