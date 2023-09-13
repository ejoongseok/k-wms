package com.example.kwms.outbound.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface PickingRepository extends JpaRepository<Picking, Long> {
    @Query("select p from Picking p where p.inventory.inventoryNo in :inventoryNos")
    List<Picking> listByInventoryNos(Set<Long> inventoryNos);
}
