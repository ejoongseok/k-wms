package com.example.kwms.location.feature.query;

import com.example.kwms.location.domain.Location;
import com.example.kwms.location.domain.Warehouse;
import com.example.kwms.location.domain.WarehouseRepository;
import com.example.kwms.location.domain.WarehouseTransfer;
import com.example.kwms.location.domain.WarehouseTransferLocation;
import com.example.kwms.location.domain.WarehouseTransferProduct;
import com.example.kwms.location.domain.WarehouseTransferRepository;
import com.example.kwms.outbound.domain.Product;
import com.example.kwms.outbound.feature.ProductClient;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class GetWarehouseTransfer {
    private final WarehouseTransferRepository warehouseTransferRepository;
    private final WarehouseRepository warehouseRepository;
    private final ProductClient productClient;

    @GetMapping("/warehouse-transfers/{warehouseTransferNo}")
    @Transactional(readOnly = true)
    public Response findBy(@PathVariable final Long warehouseTransferNo) {
        final WarehouseTransfer warehouseTransfer = warehouseTransferRepository.getBy(warehouseTransferNo);
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
        final List<Response.Product> products = new ArrayList<>();
        for (final WarehouseTransferProduct warehouseTransferProduct : warehouseTransfer.getProducts()) {
            final Product product = productClient.getBy(warehouseTransferProduct.getProductNo());
            final Long productNo = product.getProductNo();
            final String productName = product.getProductName();
            final Long quantity = warehouseTransferProduct.getQuantity();
            products.add(new Response.Product(productNo, productName, quantity));
        }

        final List<Response.Location> locations = new ArrayList<>();
        for (final WarehouseTransferLocation warehouseTransferLocation : warehouseTransfer.getWarehouseTransferLocations()) {
            final Location location = warehouseTransferLocation.getLocation();
            final Long locationNo = location.getLocationNo();
            final String locationBarcode = location.getLocationBarcode();
            final String description = location.getStorageType().getDescription();
            locations.add(new Response.Location(locationNo, locationBarcode, description));
        }

        final Response response = new Response(
                warehouseTransfer.getWarehouseTransferNo(),
                fromWarehouse.getName(),
                toWarehouse.getName(),
                warehouseTransfer.getFromWarehouseNo(),
                warehouseTransfer.getToWarehouseNo(),
                barcode,
                status,
                shippedAt,
                receivedAt,
                products,
                locations);
        return response;
    }

    private record Response(
            Long warehouseTransferNo,
            String fromWarehouseName,
            String toWarehouseName,
            Long fromWarehouseNo,
            Long toWarehouseNo,
            String code,
            String status,
            LocalDateTime shippedAt,
            LocalDateTime receivedAt,
            List<Product> products,
            List<Location> locations
    ) {
        record Product(
                Long productNo,
                String productName,
                Long quantity
        ) {

        }

        record Location(
                Long locationNo,
                String locationBarcode,
                String storageTypeDescription
        ) {

        }
    }
}
