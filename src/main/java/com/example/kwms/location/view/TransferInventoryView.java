package com.example.kwms.location.view;

import com.example.kwms.location.domain.Inventory;
import com.example.kwms.location.domain.InventoryRepository;
import com.example.kwms.location.domain.Location;
import com.example.kwms.location.domain.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class TransferInventoryView {
    private final LocationRepository locationRepository;
    private final InventoryRepository inventoryRepository;

    @GetMapping("/web/locations/{locationNo}/transfer-inventories/{inventoryNo}")
    @Transactional(readOnly = true)
    public String transferInventoryView(
            @PathVariable final Long locationNo,
            @PathVariable final Long inventoryNo,
            final Model model) {
        final Inventory inventory = inventoryRepository.getBy(inventoryNo);
        final Location location = locationRepository.getBy(locationNo);
        model.addAttribute("inventoryNo", inventory.getInventoryNo());
        model.addAttribute("locationNo", location.getLocationNo());
        model.addAttribute("locationBarcode", location.getLocationBarcode());
        model.addAttribute("lpnBarcode", inventory.getLpn().getLpnBarcode());
        return "location/transfer-inventory";
    }

}
