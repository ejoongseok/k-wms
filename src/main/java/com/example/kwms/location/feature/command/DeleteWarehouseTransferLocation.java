package com.example.kwms.location.feature.command;

import com.example.kwms.location.domain.Location;
import com.example.kwms.location.domain.LocationRepository;
import com.example.kwms.location.domain.WarehouseTransfer;
import com.example.kwms.location.domain.WarehouseTransferRepository;
import com.example.kwms.outbound.domain.PickingRepository;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DeleteWarehouseTransferLocation {
    private final LocationRepository locationRepository;
    private final WarehouseTransferRepository warehouseTransferRepository;
    private final PickingRepository pickingRepository;

    @Transactional
    @DeleteMapping("/warehouse-transfers/{warehouseTransferNo}/locations/{locationNo}")
    public void request(
            @PathVariable final Long warehouseTransferNo,
            @PathVariable final Long locationNo) {
        final Location location = locationRepository.getBy(locationNo);
        final WarehouseTransfer warehouseTransfer = warehouseTransferRepository.getBy(warehouseTransferNo);
        warehouseTransfer.deleteLocation(location);
    }

    void addLocation(final WarehouseTransfer warehouseTransfer, final Location location) {
        warehouseTransfer.addLocation(location);
        location.warehouseTransferred();
    }

    public record Request(
            @NotBlank(message = "로케이션 바코드는 필수입니다.")
            String locationBarcode) {
    }
}
