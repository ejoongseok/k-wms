package com.example.kwms.location.feature.query;

import com.example.kwms.location.domain.Warehouse;
import com.example.kwms.location.domain.WarehouseRepository;
import com.example.kwms.location.domain.WarehouseTransfer;
import com.example.kwms.location.domain.WarehouseTransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class GetWarehouseTransfers {
    private final WarehouseTransferRepository warehouseTransferRepository;
    private final WarehouseRepository warehouseRepository;

    @GetMapping("/warehouse-transfers")
    public List<Response> findAll() {
        final List<WarehouseTransfer> warehouseTransfers = warehouseTransferRepository.findAll();
        final List<Response> responses = new ArrayList<>();
        for (final WarehouseTransfer warehouseTransfer : warehouseTransfers) {
            final Warehouse fromWarehouse = warehouseRepository.getBy(warehouseTransfer.getFromWarehouseNo());
            final Warehouse toWarehouse = warehouseRepository.getBy(warehouseTransfer.getToWarehouseNo());
            final String barcode = warehouseTransfer.getBarcode();
            final String status;
            if (warehouseTransfer.isShipped() && !warehouseTransfer.isReceived()) {
                status = "출고";
            } else if (warehouseTransfer.isReceived()) {
                status = "입고";
            } else {
                status = "대기";
            }
            final LocalDateTime shippedAt = warehouseTransfer.getShippedAt();
            final LocalDateTime receivedAt = warehouseTransfer.getReceivedAt();
            final Response response = new Response(
                    warehouseTransfer.getWarehouseTransferNo(),
                    fromWarehouse.getName(),
                    toWarehouse.getName(),
                    barcode,
                    status,
                    shippedAt,
                    receivedAt);
            responses.add(response);
        }
        return responses;
    }

    private record Response(
            Long warehouseTransferNo,
            String fromWarehouseName,
            String toWarehouseName,
            String code,
            String status,
            LocalDateTime shippedAt,
            LocalDateTime receivedAt
    ) {

    }
}
