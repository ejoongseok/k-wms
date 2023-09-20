package com.example.kwms.location.feature;

import com.example.kwms.location.domain.Inventory;
import com.example.kwms.location.domain.Location;
import com.example.kwms.location.domain.LocationRepository;
import com.example.kwms.location.domain.WarehouseTransfer;
import com.example.kwms.location.domain.WarehouseTransferRepository;
import com.example.kwms.outbound.domain.Picking;
import com.example.kwms.outbound.domain.PickingRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class AddWarehouseTransferLocation {
    private final LocationRepository locationRepository;
    private final WarehouseTransferRepository warehouseTransferRepository;
    private final PickingRepository pickingRepository;

    @Transactional
    @PostMapping("/warehouse-transfers/{warehouseTransferNo}/locations")
    public void request(
            @PathVariable final Long warehouseTransferNo,
            @RequestBody @Valid final Request request) {
        final Location location = locationRepository.getBy(request.locationBarcode);
        final WarehouseTransfer warehouseTransfer = warehouseTransferRepository.getBy(warehouseTransferNo);
        validate(location.getAllInventories());

        addLocation(warehouseTransfer, location);
    }

    private void validate(final List<Inventory> allInventories) {
        final Set<Long> inventoryNos = allInventories.stream()
                .map(Inventory::getInventoryNo)
                .collect(Collectors.toUnmodifiableSet());
        final List<Picking> pickings = pickingRepository.listByInventoryNos(inventoryNos);
        pickings.stream()
                .filter(p -> !p.isPicked())
                .findFirst()
                .ifPresent(p -> {
                    throw new IllegalStateException("집품중인 상품이 있습니다. 집품 번호: %d".formatted(p.getPickingNo()));
                });
    }

    void addLocation(final WarehouseTransfer warehouseTransfer, final Location location) {
        warehouseTransfer.addLocation(location);
    }

    public record Request(
            @NotBlank(message = "로케이션 바코드는 필수입니다.")
            String locationBarcode) {
    }
}
