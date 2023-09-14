package com.example.kwms.outbound.domain;

import com.example.kwms.common.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BulkOutboundRepository extends JpaRepository<BulkOutbound, Long> {
    default BulkOutbound getBy(final Long bulkOutboundNo) {
        return findById(bulkOutboundNo)
                .orElseThrow(() -> new NotFoundException(
                        "존재하지 않는 대량출고건입니다. 대량출고건 번호: %d".formatted(bulkOutboundNo)));
    }
}
