package com.example.kwms.location.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    @Query("select i from Inventory i where i.warehouseNo = :warehouseNo and i.productNo = :productNo")
    List<Inventory> listBy(final Long warehouseNo, final Long productNo);
}
