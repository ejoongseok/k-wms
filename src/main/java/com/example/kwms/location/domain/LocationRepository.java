package com.example.kwms.location.domain;

import com.example.kwms.common.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {
    @Query("select l from Location l where l.locationBarcode = :locationBarcode")
    Optional<Location> findByBarcode(String locationBarcode);

    default Location getBy(final String locationBarcode) {
        return findByBarcode(locationBarcode)
                .orElseThrow(() -> new NotFoundException(
                        "존재하지 않는 로케이션입니다. 로케이션 바코드: %s".formatted(
                                locationBarcode)));
    }
}
