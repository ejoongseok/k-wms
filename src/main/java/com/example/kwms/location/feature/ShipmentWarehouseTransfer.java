package com.example.kwms.location.feature;

import com.example.kwms.location.domain.WarehouseTransfer;
import com.example.kwms.location.domain.WarehouseTransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class ShipmentWarehouseTransfer {
    private final WarehouseTransferRepository warehouseTransferRepository;

    @Transactional
    @PostMapping("/warehouse-transfers/{warehouseTransferBarcode}/shipment")
    public void request(
            @PathVariable final String warehouseTransferBarcode) {
        final WarehouseTransfer warehouseTransfer = warehouseTransferRepository.getBy(warehouseTransferBarcode);

        warehouseTransfer.shipment();
    }

}