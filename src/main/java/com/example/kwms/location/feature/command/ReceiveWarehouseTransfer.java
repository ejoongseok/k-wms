package com.example.kwms.location.feature.command;

import com.example.kwms.location.domain.WarehouseTransfer;
import com.example.kwms.location.domain.WarehouseTransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class ReceiveWarehouseTransfer {
    private final WarehouseTransferRepository warehouseTransferRepository;

    @Transactional
    @PostMapping("/warehouse-transfers/{warehouseTransferNo}/receive")
    public void request(
            @PathVariable final Long warehouseTransferNo) {
        final WarehouseTransfer warehouseTransfer = warehouseTransferRepository.getBy(warehouseTransferNo);

        warehouseTransfer.receive();
    }

}
