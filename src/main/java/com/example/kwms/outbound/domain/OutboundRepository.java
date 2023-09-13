package com.example.kwms.outbound.domain;

import com.example.kwms.common.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutboundRepository extends JpaRepository<Outbound, Long> {
    default Outbound getBy(final Long outboundNo) {
        return findById(outboundNo)
                .orElseThrow(() -> new NotFoundException("출고가 존재하지 않습니다. 출고번호: %d".formatted(outboundNo)));
    }
}
