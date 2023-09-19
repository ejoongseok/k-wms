package com.example.kwms.location.feature.query;

import com.example.kwms.location.domain.Inventory;
import com.example.kwms.location.domain.InventoryRepository;
import com.example.kwms.outbound.domain.Product;
import com.example.kwms.outbound.feature.ProductClient;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetInventoryNo {
    private final InventoryRepository inventoryRepository;
    private final ProductClient productClient;

    @GetMapping("/inventories/{inventoryNo}")
    @Transactional(readOnly = true)
    public InventoryResponse getLocation(@PathVariable final Long inventoryNo) {
        final Inventory inventory = inventoryRepository.getBy(inventoryNo);
        final Long productNo = inventory.getProductNo();
        final Product product = productClient.getBy(productNo);
        final String productName = product.getProductName();
        final String lpnBarcode = inventory.getLpn().getLpnBarcode();
        final Long quantity = inventory.getQuantity();
        return new InventoryResponse(inventoryNo, productNo, productName, lpnBarcode, quantity);
    }

    public record InventoryResponse(
            Long inventoryNo,
            Long productNo,
            String productName,
            String lpnBarcode,
            Long quantity
    ) {
    }
}
