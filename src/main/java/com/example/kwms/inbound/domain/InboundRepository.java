package com.example.kwms.inbound.domain;

import com.example.kwms.common.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InboundRepository extends JpaRepository<Inbound, Long> {
    default Inbound getBy(final Long inboundNo) {
        return findById(inboundNo)
                .orElseThrow(() -> new NotFoundException(
                        "존재하지 않는 입고 번호입니다. 입고번호: %s".formatted(inboundNo)));
    }
}
