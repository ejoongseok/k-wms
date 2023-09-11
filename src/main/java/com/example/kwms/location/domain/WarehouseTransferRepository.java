package com.example.kwms.location.domain;

import com.example.kwms.common.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface WarehouseTransferRepository extends JpaRepository<WarehouseTransfer, Long> {
    @Query("select wt from WarehouseTransfer wt where wt.barcode = :barcode")
    Optional<WarehouseTransfer> findBy(String barcode);

    default WarehouseTransfer getBy(final Long warehouseTransferNo) {
        return findById(warehouseTransferNo)
                .orElseThrow(() -> new NotFoundException(
                        "존재하지 않는 창고간 재고 이동 번호입니다. 창고간 재고 이동 번호: %d".formatted(warehouseTransferNo)));
    }
}
