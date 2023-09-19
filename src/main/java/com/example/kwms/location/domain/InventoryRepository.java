package com.example.kwms.location.domain;

import com.example.kwms.common.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    @Query("select i from Inventory i where i.warehouseNo = :warehouseNo and i.productNo = :productNo")
    List<Inventory> listBy(final Long warehouseNo, final Long productNo);

    @Query("select i from Inventory i where i.location.locationBarcode = :locationBarcode and i.lpn.lpnBarcode = :lpnBarcode")
    Optional<Inventory> findByLocationBarcodeAndLpnBarcode(String locationBarcode, String lpnBarcode);

    default Inventory getBy(final String locationBarcode, final String lpnBarcode) {
        return findByLocationBarcodeAndLpnBarcode(locationBarcode, lpnBarcode)
                .orElseThrow(() -> new NotFoundException("재고 정보가 없습니다. 로케이션 바코드:%s , lpnBarcode: %s"
                        .formatted(locationBarcode, lpnBarcode)));
    }

    default Inventory getBy(final Long inventoryNo) {
        return findById(inventoryNo)
                .orElseThrow(() -> new NotFoundException(
                        "재고가 존재하지 않습니다. 재고번호: %d".formatted(inventoryNo)));
    }
}
