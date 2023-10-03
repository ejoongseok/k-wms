package com.example.kwms.inbound.domain;

import com.example.kwms.common.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LPNRepository extends JpaRepository<LPN, Long> {
    @Query("select l from LPN l where l.lpnBarcode = :lpnBarcode")
    Optional<LPN> findBy(String lpnBarcode);

    default LPN getBy(final String lpnBarcode) {
        return findBy(lpnBarcode)
                .orElseThrow(() -> new NotFoundException(
                        "존재하지 않는 LPN입니다. LPN 바코드: %s".formatted(lpnBarcode)));
    }
}
