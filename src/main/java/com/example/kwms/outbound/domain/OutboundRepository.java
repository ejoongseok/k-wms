package com.example.kwms.outbound.domain;

import com.example.kwms.common.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OutboundRepository extends JpaRepository<Outbound, Long> {
    default Outbound getBy(final Long outboundNo) {
        return findById(outboundNo)
                .orElseThrow(() -> new NotFoundException("출고가 존재하지 않습니다. 출고번호: %d".formatted(outboundNo)));
    }

    @Query("select o from Outbound o where o.pickingTote.locationBarcode = :pickingTote")
    Optional<Outbound> findBy(String pickingTote);

    default Outbound getBy(final String pickingToteBarcode) {
        return findBy(pickingToteBarcode)
                .orElseThrow(() -> new NotFoundException(
                        "피킹 토트 바코드로 출고를 찾을 수 없습니다. 토트 바코드: %s"
                                .formatted(pickingToteBarcode)));
    }

    @Query("select o from Outbound o where o.orderNo = :orderNo")
    Optional<Outbound> getByOrderNo(Long orderNo);
}
