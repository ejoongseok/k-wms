package com.example.kwms.outbound.feature;

import com.example.kwms.location.domain.Inventory;
import com.example.kwms.location.domain.InventoryRepository;
import com.example.kwms.outbound.domain.Outbound;
import com.example.kwms.outbound.domain.OutboundRepository;
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
public class ScanToPickManual {
    private final OutboundRepository outboundRepository;
    private final InventoryRepository inventoryRepository;

    @PostMapping("/outbounds/scan-to-pick-manual")
    @Transactional
    public void request(@RequestBody @Valid final Request request) {
        final Outbound outbound = outboundRepository.getBy(request.pickingToteBarcode);
        final Inventory inventory = inventoryRepository.getBy(request.barcodeForPickingLocation, request.lpnBarcode);

        outbound.scanToPickManual(inventory, request.quantity);
    }

    public record Request(
            @NotBlank(message = "피킹 토트 바코드는 필수입니다.")
            String pickingToteBarcode,
            @NotBlank(message = "LPN 바코드는 필수입니다.")
            String lpnBarcode,
            @NotBlank(message = "집품할 로케이션 바코드는 필수입니다.")
            String barcodeForPickingLocation,
            @NotNull(message = "집품할 수량은 필수입니다.")
            @Min(value = 1, message = "집품할 수량은 1개 이상이어야 합니다.")
            Long quantity) {
    }
}
