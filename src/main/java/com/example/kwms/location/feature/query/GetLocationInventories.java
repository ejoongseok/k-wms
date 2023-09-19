package com.example.kwms.location.feature.query;

import com.example.kwms.location.domain.Inventory;
import com.example.kwms.location.domain.Location;
import com.example.kwms.location.domain.LocationRepository;
import com.example.kwms.outbound.domain.Product;
import com.example.kwms.outbound.feature.ProductClient;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class GetLocationInventories {
    private final LocationRepository locationRepository;
    private final ProductClient productClient;

    @GetMapping("/locations/{locationBarcode}/inventories")
    @Transactional(readOnly = true)
    public List<InventoryResponse> getLocationInventories(@PathVariable final String locationBarcode) {
        final Location location = locationRepository.getBy(locationBarcode);

        final List<Inventory> inventories = location.getInventories();
        final List<InventoryResponse> inventoryResponses = new ArrayList<>();
        for (final Inventory inventory : inventories) {
            final Long productNo = inventory.getProductNo();
            final Product product = productClient.getBy(productNo);
            final String productName = product.getProductName();
            final String lpnBarcode = inventory.getLpn().getLpnBarcode();
            final Long quantity = inventory.getQuantity();
            final InventoryResponse inventoryResponse = new InventoryResponse(productNo, productName, lpnBarcode, quantity);
            inventoryResponses.add(inventoryResponse);
        }

        return inventoryResponses;
    }

    public record InventoryResponse(
            Long productNo,
            String productName,
            String lpnBarcode,
            Long quantity
    ) {
    }
}
