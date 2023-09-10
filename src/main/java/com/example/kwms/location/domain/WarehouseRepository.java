package com.example.kwms.location.domain;

import com.example.kwms.common.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    default Warehouse getBy(final Long warehouseNo) {
        return findById(warehouseNo)
                .orElseThrow(() -> new NotFoundException(
                        "창고를 찾을 수 없습니다. 창고번호: %d".formatted(warehouseNo)));
    }
}
