package com.example.kwms.location.view;

import com.example.kwms.location.domain.Location;
import com.example.kwms.location.domain.LocationRepository;
import com.example.kwms.location.domain.StorageType;
import com.example.kwms.outbound.domain.OutboundRepository;
import com.example.kwms.outbound.domain.Picking;
import com.example.kwms.outbound.domain.PickingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class LocationDetailView {
    private final LocationRepository locationRepository;
    private final OutboundRepository outboundRepository;
    private final PickingRepository pickingRepository;

    @GetMapping("/web/locations/{locationNo}")
    @Transactional(readOnly = true)
    public String locationsList(
            @PathVariable final Long locationNo,
            final Model model) {
        final Location location = locationRepository.getBy(locationNo);
        model.addAttribute("locationNo", location.getLocationNo());
        model.addAttribute("locationBarcode", location.getLocationBarcode());
        model.addAttribute("parentLocationNo", null == location.getParent() ? null : location.getParent().getLocationNo());
        model.addAttribute("hasChildren", !location.getChildren().isEmpty());

        final Set<Long> inventoryNos = location.getAllInventories().stream()
                .map(inventory -> inventory.getInventoryNo())
                .collect(Collectors.toUnmodifiableSet());
        final List<Picking> pickings = pickingRepository.listByInventoryNos(inventoryNos);
        final boolean isUpdatable = pickings.stream().allMatch(Picking::isPicked);
        model.addAttribute("isUpdatable", isUpdatable);
        final boolean isPickingTote = isPickingTote(location);
        model.addAttribute("isPickingTote", isPickingTote);
        return "location/detail";
    }

    private boolean isPickingTote(final Location location) {
        if (StorageType.TOTE == location.getStorageType()) {
            return outboundRepository.findBy(location.getLocationBarcode()).isPresent();
        }
        return false;
    }

}
