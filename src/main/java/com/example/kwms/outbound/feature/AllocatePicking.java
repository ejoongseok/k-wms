package com.example.kwms.outbound.feature;

import com.example.kwms.location.domain.Inventory;
import com.example.kwms.location.domain.InventoryRepository;
import com.example.kwms.outbound.domain.Outbound;
import com.example.kwms.outbound.domain.OutboundRepository;
import com.example.kwms.outbound.domain.PickingAllocator;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
class AllocatePicking {
    private final OutboundRepository outboundRepository;
    private final InventoryRepository inventoryRepository;
    private final PickingAllocator pickingAllocator = new PickingAllocator();

    @PostMapping("/outbounds/{outboundNo}/pickings")
    @Transactional
    public void request(@PathVariable final Long outboundNo) {
        final Outbound outbound = outboundRepository.getBy(outboundNo);
        final List<Inventory> inventories = listBy(outbound.getProductNos(), outbound);

        pickingAllocator.allocatePicking(outbound, inventories);
    }

    private List<Inventory> listBy(final Set<Long> productNos, final Outbound outbound) {
        return productNos.stream()
                .flatMap(productNo -> inventoryRepository.listBy(outbound.getWarehouseNo(), productNo).stream())
                .collect(Collectors.toList());
    }
}
