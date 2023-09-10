package com.example.kwms.location.feature;

import com.example.kwms.inbound.domain.LPN;
import com.example.kwms.inbound.domain.LPNRepository;
import com.example.kwms.location.domain.InventoryTransfer;
import com.example.kwms.location.domain.Location;
import com.example.kwms.location.domain.LocationRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TransferInventory {
    private final LocationRepository locationRepository;
    private final LPNRepository lpnRepository;
    private final InventoryTransfer inventoryTransfer = new InventoryTransfer();

    @PostMapping("/locations/transfer-inventory")
    @Transactional
    public void request(@RequestBody @Valid final Request request) {
        final Location currentLocation = locationRepository.getBy(request.currentLocationBarcode);
        final Location targetLocation = locationRepository.getBy(request.targetLocationBarcode);
        final LPN lpn = lpnRepository.getBy(request.lpnBarcode);

        inventoryTransfer.execute(currentLocation, targetLocation, lpn, request.quantity);
    }

    public record Request(
            @NotBlank(message = "현재 로케이션 바코드는 필수입니다.")
            String currentLocationBarcode,
            @NotBlank(message = "재고 이동할 로케이션 바코드는 필수입니다.")
            String targetLocationBarcode,
            @NotBlank(message = "LPN 바코드는 필수입니다.")
            String lpnBarcode,
            @NotNull(message = "이동할 수량은 필수입니다.")
            @Min(value = 1, message = "이동할 수량은 1개 이상이어야 합니다.")
            Long quantity) {
    }
}
